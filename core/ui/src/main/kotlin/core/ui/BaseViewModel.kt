package core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
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
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.set
import kotlin.coroutines.CoroutineContext

/**
 * Abstract class representing a ViewModel with lifecycle-aware coroutine launching capabilities.
 */
@Stable
@Immutable
abstract class BaseViewModel : ViewModel() {

    private val jobs = ConcurrentHashMap<String, Job>()
    private val initialized = AtomicBoolean(false)

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
     * Lifecycle-aware method called when initializing the ViewModel.
     */
    protected open fun doInit() = Unit

    /**
     * Lifecycle-aware method called when binding the ViewModel to a view.
     */
    protected open fun doBind() = Unit

    /**
     * Lifecycle-aware method called when the ViewModel is resumed.
     */
    protected open fun doResume() = Unit

    /**
     * Lifecycle-aware method called when disposing the ViewModel.
     */
    protected open fun doDispose() = Unit

    /**
     * Binds the ViewModel to the given [owner]'s lifecycle.
     *
     * @param owner The [LifecycleOwner] to bind to.
     */
    @Composable
    fun bind(owner: LifecycleOwner) {
        val ownerId = owner.hashCode()
        LaunchedEffect(ownerId) {
            if (initialized.compareAndSet(false, true)) {
                doInit()
            }
            doBind()
            var initialRequest = true
            owner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModelScope.launch {
                    if (!initialRequest) {
                        doResume()
                    }
                    initialRequest = false
                }
            }
        }
        doBind(owner)
    }

    override fun onCleared() {
        doDispose()
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
    viewModel.bind(lifecycleOwner)
    return viewModel
}