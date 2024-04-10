package app.showcases

import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the showcases screen.
 * It manages the state and actions related to showcases.
 */
@HiltViewModel
class ShowcasesViewModel @Inject constructor(
    val navigationState: NavigationState,
) : BaseViewModel() {

    /**
     * Store object for managing the visibility of hints.
     */
    val hintStore = StoreObject(false)

    /**
     * Store object for managing the list of showcases.
     */
    val showcasesStore = StoreObject(Showcases.all)

    /**
     * Sets the hint visibility to true.
     */
    fun onShowHint() {
        hintStore.set(true)
    }

}
