package app.ui.theme

import core.data.serialization.JsonStrategy
import kotlinx.serialization.Serializable

/**
 * Represents the configuration data for themes.
 */
@Serializable
data class ThemeConfigData(
    val defaultThemeId: String? = null,
    val lightThemeId: String? = null,
    val darkThemeId: String? = null,
    val autoDark: Boolean? = null,
) {
    companion object {
        val serializationStrategy = JsonStrategy.create(serializer())
    }
}