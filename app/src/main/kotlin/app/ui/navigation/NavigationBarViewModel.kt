package app.ui.navigation

import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NavigationBarViewModel @Inject constructor(
    navigationBarState: NavigationBarState,
    private val navigationState: NavigationState,
) : BaseViewModel() {

    val pagesStore = navigationBarState.pagesStore
    val visibilityStore = navigationBarState.visibilityStore
    val selectedPageStore = navigationBarState.selectedPageStore

    override fun doBind() {
        launchAsync("doBind") {
            val destStore = navigationState.currentDestinationStore
            pagesStore.asFlow()
                .filterNotNull()
                .map { it.associateBy { it.id } }
                .flatMapLatest { pages -> destStore.asFlow().map { pages to it } }
                .map { pair -> pair.second?.id?.let(pair.first::get) }
                .collectLatest(selectedPageStore::set)
        }
    }

}