package app.di.state

import android.app.Application
import core.ui.theme.ThemeConfig
import core.ui.theme.ThemeState
import core.ui.theme.material3.Material3Themes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesThemeState {

    @Provides
    @Singleton
    fun state(app: Application): ThemeState {
        return ThemeState(
            defaultConfig = createDefaultConfig(),
            dynamicConfig = createDynamicConfig(app)
        )
    }

    private fun createDefaultConfig(): ThemeConfig {
        val light = Material3Themes.light()
        val dark = Material3Themes.dark()
        return ThemeConfig(
            defaultTheme = light,
            lightTheme = light,
            darkTheme = dark,
            autoDark = true
        )
    }

    private fun createDynamicConfig(app: Application): ThemeConfig? {
        val light = Material3Themes.dynamicLight(app) ?: return null
        val dark = Material3Themes.dynamicDark(app) ?: return null
        return ThemeConfig(
            defaultTheme = light,
            lightTheme = light,
            darkTheme = dark,
            autoDark = true
        )
    }

}