package core.ui.navigation.command

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import core.ui.navigation.NavigationCommand
import core.ui.navigation.NavigationContext

/**
 * Command for copying text to the clipboard.
 *
 * @property text The text to be copied.
 * @property label The label associated with the copied text.
 * @property toast The toast message to display after copying.
 */
data class CopyTextCommand(
    private val text: String,
    private val label: String? = null,
    private val toast: String? = null
) : NavigationCommand() {

    override val id: String = "copy_text"

    override fun doExecute(navigationContext: NavigationContext) {
        val context = navigationContext.context
        val navigationState = navigationContext.navigationState
        val service = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label.orEmpty(), text)
        service.setPrimaryClip(clip)
        toast?.let { ShowToastCommand(it) }?.let(navigationState::onCommand)
    }
}