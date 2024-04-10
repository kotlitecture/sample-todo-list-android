package core.ui.navigation.command

import core.ui.misc.utils.WeakReferenceUtils
import core.ui.navigation.NavigationCommand
import core.ui.navigation.NavigationContext
import kotlinx.coroutines.launch

/**
 * Command for showing a Snackbar.
 *
 * @property text The text to display in the Snackbar.
 */
data class ShowSnackbarCommand(
    private val text: String
) : NavigationCommand() {

    override val id: String = "show_snackbar"

    override fun doExecute(navigationContext: NavigationContext) {
        val host = navigationContext.snackbarHostSate
        val scope = navigationContext.scope
        val job = scope.launch { host.showSnackbar(text) }
        WeakReferenceUtils.replace(id, job)?.cancel()
    }

}