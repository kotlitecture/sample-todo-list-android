package app.datasource.clipboard

import android.app.Application
import core.data.datasource.clipboard.BasicClipboardSource

/**
 * Decorator class for working with clipboard.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppClipboardSource(app: Application) : BasicClipboardSource(app)