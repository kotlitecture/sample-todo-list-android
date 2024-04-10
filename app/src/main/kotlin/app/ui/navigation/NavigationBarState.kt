package app.ui.navigation

import core.ui.state.StoreObject
import core.ui.state.StoreState

/**
 * Represents the state of the navigation bar.
 *
 * @param pages The list of available pages in the navigation bar.
 */
data class NavigationBarState(
    val pages: List<NavigationBarPage>,
) : StoreState() {

    /** Store navigation visibility state. */
    val visibilityStore: StoreObject<Boolean> = StoreObject()

    /** Store object for the available pages. */
    val pagesStore: StoreObject<List<NavigationBarPage>> = StoreObject(pages)

    /** Store object for the active page. */
    val selectedPageStore: StoreObject<NavigationBarPage> = StoreObject()

}