package app.userflow.loader.data

import app.datasource.config.AppConfigSource
import core.ui.BaseViewModel
import core.ui.state.DataState
import core.ui.state.StoreObject
import core.ui.state.StoreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DataLoaderViewModel @Inject constructor(
    private val configSource: AppConfigSource
) : BaseViewModel() {

    val isLoadingStore = StoreObject(false)

    fun onBind(state: StoreState) {
        launchAsync("dataStateStore") {
            state.dataStateStore.asFlow()
                .filterNotNull()
                .map { it is DataState.Loading }
                .distinctUntilChanged()
                .collectLatest { loading ->
                    delay(configSource.getUiLoadingDelay())
                    if (loading) {
                        isLoadingStore.set(true)
                        delay(configSource.getUiLoadingTimeout())
                        isLoadingStore.set(false)
                    } else {
                        isLoadingStore.set(false)
                    }
                }
        }
    }

}