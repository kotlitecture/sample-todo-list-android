package app.ui.theme

import androidx.compose.runtime.Composable
import app.provideHiltViewModel
import core.ui.theme.ThemeProvider

/**
 * Composable function to provide the theme for the application.
 *
 * @param content The composable content to be themed.
 */
@Composable
fun ThemeProvider(content: @Composable () -> Unit) {
    val viewModel: ThemeViewModel = provideHiltViewModel()
    ThemeProvider(viewModel.themeState, content)
}