package app.userflow.task.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.AppState
import app.data.TaskData
import app.datasource.config.AppConfigSource
import app.repository.TaskRepository
import core.ui.BaseViewModel
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class SearchTasksViewModel @Inject constructor(
    private val configSource: AppConfigSource,
    private val repository: TaskRepository,
    private val appState: AppState
) : BaseViewModel() {

    val flowStore = StoreObject<Flow<PagingData<TaskData>>>()
    val queryStore = StoreObject<String>()

    override fun doBind() {
        launchAsync("findAll") {
            queryStore.asFlow()
                .distinctUntilChanged()
                .collectLatest { query ->
                    val all = repository.findAll(query)
                    if (!flowStore.isNull()) {
                        delay(configSource.getSearchDebounce())
                    }
                    flowStore.set(all.cachedIn(viewModelScope))
                }
        }
    }

    fun onClick(data: TaskData?) {
        if (data == null) return
        launchAsync("onClick", appState) {
            data.task.completed = !data.task.completed
            repository.update(data)
        }
    }

}
