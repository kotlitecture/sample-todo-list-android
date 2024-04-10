package core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import core.ui.misc.extensions.findActivity
import core.ui.state.DataState
import core.ui.state.StoreState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.collections.set
import kotlin.coroutines.CoroutineContext

/**
 * Abstract class representing a ViewModel with lifecycle-aware coroutine launching capabilities.
 */
@Immutable
abstract class BaseViewModel : ViewModel() {

    private val subscribers = ConcurrentLinkedQueue<Int>()
    private val jobs = ConcurrentHashMap<String, Job>()

    /**
     * Launches a coroutine in the main thread context, managing the loading state and error handling.
     *
     * @param id The identifier for the coroutine job.
     * @param state The [StoreState] associated with the data state.
     * @param block The block of code to execute as a coroutine.
     */
    protected fun launchMain(
        id: String,
        state: StoreState? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        launch(
            id = id,
            state = state,
            block = block,
            context = viewModelScope.coroutineContext,
        )
    }

    /**
     * Launches a coroutine in the IO thread context, managing the loading state and error handling.
     *
     * @param id The identifier for the coroutine job.
     * @param state The [StoreState] associated with the data state.
     * @param block The block of code to execute as a coroutine.
     */
    protected fun launchAsync(
        id: String,
        state: StoreState? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        launch(
            id = id,
            state = state,
            block = block,
            context = Dispatchers.IO
        )
    }

    protected suspend fun <T> withAsync(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return viewModelScope.async(Dispatchers.IO) { block.invoke(this) }
    }

    private fun launch(
        id: String,
        state: StoreState?,
        context: CoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) {
        jobs.remove(id)?.cancel()
        val loadingState = state?.let { DataState.Loading(id) }
        state?.dataStateStore?.set(loadingState)
        val job = viewModelScope.launch(context = context, block = block)
        if (state != null) {
            job.invokeOnCompletion { th ->
                val currentState = state.dataStateStore.get()
                if (currentState == null || currentState.uid == loadingState?.uid) {
                    val nextState = when {
                        th == null -> DataState.Loaded(id)
                        else -> DataState.Error(id, th)
                    }
                    state.dataStateStore.set(nextState)
                }
            }
        }
        jobs[id] = job
    }

    /**
     * Lifecycle-aware method called when binding the ViewModel to a [LifecycleOwner].
     *
     * @param owner The [LifecycleOwner] to bind to.
     */
    @Composable
    protected open fun doBind(owner: LifecycleOwner) = Unit

    /**
     * Lifecycle-aware method called when binding the ViewModel.
     */
    protected open fun doBind() = Unit

    /**
     * Lifecycle-aware method called when the ViewModel is resumed.
     */
    protected open fun doResume() = Unit

    /**
     * Lifecycle-aware method called when unbinding the ViewModel.
     */
    protected open fun doUnbind() = Unit

    /**
     * Binds the ViewModel to the given [owner]'s lifecycle.
     *
     * @param owner The [LifecycleOwner] to bind to.
     * @param activityScope Determines if the binding is scoped to the activity.
     */
    @Composable
    fun bind(owner: LifecycleOwner, activityScope: Boolean) {
        val ownerId = owner.hashCode()
        val scope = rememberCoroutineScope()
        LaunchedEffect(ownerId) {
            val isNew = !subscribers.contains(ownerId)
            subscribers.add(ownerId)
            if (isNew) {
                doBind()
                var initialRequest = true
                owner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    scope.launch {
                        if (!initialRequest) {
                            doResume()
                        }
                        initialRequest = false
                    }
                }
            }
        }
        DisposableEffect(ownerId) {
            onDispose {
                subscribers.remove(ownerId)
                if (subscribers.isEmpty()) {
                    if (activityScope) {
                        val currentJobs = jobs.values.toList()
                        jobs.clear()
                        currentJobs.forEach { it.cancel() }
                    }
                    doUnbind()
                }
            }
        }
        doBind(owner)
    }

}

@Stable
@Composable
inline fun <reified VM : BaseViewModel> provideViewModel(
    key: String? = null,
    activityScope: Boolean = false
): VM {
    return createViewModel(activityScope) { viewModel(it, key) }
}

@Stable
@Composable
inline fun <reified VM : BaseViewModel> createViewModel(
    activityScope: Boolean = false,
    provider: @Composable (storeOwner: ViewModelStoreOwner) -> VM
): VM {
    val storeOwner: ViewModelStoreOwner
    val lifecycleOwner: LifecycleOwner
    when {
        activityScope -> {
            val activity = LocalContext.current.findActivity()!!
            lifecycleOwner = activity
            storeOwner = activity
        }

        else -> {
            lifecycleOwner = LocalLifecycleOwner.current
            storeOwner = LocalViewModelStoreOwner.current!!
        }
    }
    val viewModel: VM = provider(storeOwner)
    viewModel.bind(lifecycleOwner, activityScope)
    return viewModel
}