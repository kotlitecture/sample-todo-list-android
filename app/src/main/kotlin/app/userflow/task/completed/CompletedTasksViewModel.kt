package app.userflow.task.completed

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.AppState
import app.data.TaskData
import app.repository.TaskRepository
import core.ui.BaseViewModel
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CompletedTasksViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val appState: AppState
) : BaseViewModel() {

    val flowStore = StoreObject<Flow<PagingData<TaskData>>>()

    override fun doBind() {
        if (flowStore.isNull()) {
            launchAsync("getAll") {
                val all = repository.getAll(true)
                flowStore.set(all.cachedIn(viewModelScope))
            }
        }
    }

    fun onCheck(data: TaskData?) {
        if (data == null) return
        launchAsync("onClick", appState) {
            data.task.completed = !data.task.completed
            repository.update(data)
        }
    }

}
