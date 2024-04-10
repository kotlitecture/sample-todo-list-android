package core.ui.navigation.command

import android.content.Intent
import core.ui.navigation.NavigationContext
import core.ui.navigation.NavigationCommand

/**
 * Command for opening the email application.
 *
 * @property title The title of the email application chooser dialog.
 */
data class OpenEmailCommand(
    private val title: String? = null
) : NavigationCommand() {

    override val id: String = "open_email"

    override fun doExecute(navigationContext: NavigationContext) {
        val context = navigationContext.context
        val intent = Intent(Intent.ACTION_MAIN)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addCategory(Intent.CATEGORY_APP_EMAIL)
        val chooserIntent = Intent.createChooser(intent, title.orEmpty())
        context.startActivity(chooserIntent)
    }
}