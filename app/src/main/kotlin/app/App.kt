package app

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.hilt.navigation.compose.hiltViewModel
import core.ui.BaseViewModel
import core.ui.createViewModel
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for the app.
 */
@HiltAndroidApp
class App : Application()

/**
 * Provides a Hilt ViewModel of the specified type.
 *
 * @param VM The type of ViewModel to provide.
 * @param key Optional key for the ViewModel.
 * @param activityScoped Boolean indicating whether the ViewModel is scoped to the activity.
 * @return The Hilt ViewModel of the specified type.
 */
@Stable
@Composable
inline fun <reified VM : BaseViewModel> provideHiltViewModel(
    key: String? = null,
    activityScoped: Boolean = false
): VM {
    return createViewModel(activityScoped) { hiltViewModel(it, key) }
}