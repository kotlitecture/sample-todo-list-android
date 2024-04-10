package core.ui.misc.extensions

import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.FragmentActivity

/**
 * Finds the nearest FragmentActivity from the given Context.
 *
 * @return The nearest FragmentActivity, or null if not found.
 */
fun Context.findActivity(): FragmentActivity? {
    if (this is FragmentActivity) return this
    var context = this
    while (context is ContextWrapper) {
        if (context is FragmentActivity) return context
        context = context.baseContext
    }
    return null
}