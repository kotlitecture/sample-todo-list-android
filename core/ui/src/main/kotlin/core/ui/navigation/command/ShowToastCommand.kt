package core.ui.navigation.command

import android.widget.Toast
import core.ui.misc.utils.WeakReferenceUtils
import core.ui.navigation.NavigationCommand
import core.ui.navigation.NavigationContext

/**
 * Command for showing a Toast.
 *
 * @property text The text to display in the Toast.
 */
data class ShowToastCommand(
    private val text: String
) : NavigationCommand() {

    override val id: String = "show_toast"

    override fun doExecute(navigationContext: NavigationContext) {
        val context = navigationContext.context.applicationContext
        val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        WeakReferenceUtils.replace(id, toast)?.cancel()
        toast.show()
    }
}