package core.ui.theme

import android.graphics.Color
import androidx.activity.SystemBarStyle
import androidx.compose.runtime.Immutable

/**
 * Abstract class representing a theme data provider.
 * Subclasses should provide the dark mode flag.
 *
 * @param D The type of theme data to be provided by the subclass.
 */
@Immutable
abstract class ThemeDataProvider<D : ThemeData> {

    /** Unique identifier of the theme provider. */
    abstract val id: String

    /** Indicates whether the theme is dark mode or not. */
    abstract val dark: Boolean

    protected open fun createSystemBarStyle(): SystemBarStyle {
        return if (dark) {
            SystemBarStyle.dark(Color.TRANSPARENT)
        } else {
            SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        }
    }

    /**
     * Provides the theme data based on the given configuration.
     *
     * @param config The theme configuration.
     * @return The theme data instance.
     */
    abstract fun provide(config: ThemeConfig): D

}