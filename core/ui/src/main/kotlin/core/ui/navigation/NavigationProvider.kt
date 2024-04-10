package core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import core.ui.provideViewModel
import core.ui.state.ErrorStateProvider

/**
 * Composable function responsible for providing navigation functionality to the UI.
 *
 * @param navigationState The navigation state containing destination and navigation information.
 * @param navigationContext The application context containing navigation controller and other related components.
 */
@Composable
fun NavigationProvider(navigationState: NavigationState, navigationContext: NavigationContext) {
    val viewModel = provideViewModel<NavigationViewModel>()
    DisposableEffect(navigationState, navigationContext) {
        viewModel.onBind(navigationState, navigationContext)
        onDispose { viewModel.onUnbind(navigationState) }
    }
    ErrorStateProvider(navigationState)
}