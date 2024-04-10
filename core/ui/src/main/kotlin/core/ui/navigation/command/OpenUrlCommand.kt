package core.ui.navigation.command

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import core.ui.navigation.NavigationCommand
import core.ui.navigation.NavigationContext

/**
 * Command for opening a URL.
 *
 * @property url The URL to open.
 * @property title The title of the chooser dialog.
 * @property external Flag indicating whether to open the URL externally or in a Chrome custom tab.
 */
data class OpenUrlCommand(
    private val url: String,
    private val title: String? = null,
    private val external: Boolean = false
) : NavigationCommand() {

    override val id: String = "open_url"

    override fun doExecute(navigationContext: NavigationContext) {
        val context = navigationContext.context
        if (external) {
            openUrl(context, url, title)
        } else {
            openUrlInChromeTab(context, url)
        }
    }

    private fun openUrl(context: Context, url: String, title: String? = null) {
        val intent = Intent(Intent.ACTION_VIEW)
            .setData(Uri.parse(url))
        val chooserIntent = Intent.createChooser(intent, title.orEmpty())
        context.startActivity(chooserIntent)
    }

    private fun openUrlInChromeTab(context: Context, url: String) {
        try {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        } catch (e: Exception) {
            openUrl(context, url)
        }
    }
}