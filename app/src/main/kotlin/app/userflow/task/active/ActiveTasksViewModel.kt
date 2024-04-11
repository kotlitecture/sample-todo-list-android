package app.userflow.task.active

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.AppState
import app.data.TaskData
import app.datasource.clipboard.AppClipboardSource
import app.repository.TaskRepository
import core.ui.BaseViewModel
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class ActiveTasksViewModel @Inject constructor(
    private val clipboardSource: AppClipboardSource,
    private val repository: TaskRepository,
    private val appState: AppState
) : BaseViewModel() {

    val flowStore = StoreObject<Flow<PagingData<TaskData>>>()
    val clipboardStore = StoreObject<String>()
    val noteStore = StoreObject<String>()

    override fun doBind() {
        if (flowStore.isNull()) {
            launchAsync("getAll") {
                val all = repository.getAll(false)
                flowStore.set(all.cachedIn(viewModelScope))
            }
        }
        trackClipboard()
    }

    override fun doResume() {
        trackClipboard()
    }

    fun onCheck(data: TaskData?) {
        if (data == null) return
        launchAsync("onClick", appState) {
            data.task.completed = !data.task.completed
            repository.update(data)
        }
    }

    fun onCreate(text: String) {
        launchAsync("onCreate", appState) {
            repository.create(text)
            clipboardSource.copy(null)
        }
    }

    fun onCreate() {
        val text = noteStore.get() ?: return
        launchAsync("onCreate", appState) {
            repository.create(text)
            noteStore.clear()
        }
    }

    private fun trackClipboard() {
        launchAsync("trackClipboard") {
            clipboardSource.getChanges()
                .distinctUntilChanged()
                .collectLatest(clipboardStore::set)
        }
    }

}
