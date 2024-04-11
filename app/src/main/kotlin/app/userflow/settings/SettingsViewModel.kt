package app.userflow.settings

import app.AppState
import core.ui.BaseViewModel
import core.ui.navigation.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val navigationState: NavigationState,
    private val appState: AppState
) : BaseViewModel() {

    fun onBack() {
        navigationState.onBack()
    }

}
