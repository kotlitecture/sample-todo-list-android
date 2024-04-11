package app

import app.userflow.task.active.ActiveTasksDestination
import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the main activity of the app.
 *
 * @param navigationState The navigation state for managing app navigation.
 * @param appState The app state for managing app-wide state.
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    val navigationState: NavigationState,
    val appState: AppState,
) : BaseViewModel() {

    override fun doBind() {
        launchAsync("doBind") {
            navigationState.setStartDestination(ActiveTasksDestination)
        }
    }

}