package app.ui.theme

import app.datasource.keyvalue.AppKeyValueSource
import core.ui.BaseViewModel
import core.ui.theme.ThemeConfig
import core.ui.theme.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * ViewModel class responsible for managing the theme state.
 *
 * @param themeState The state of the theme.
 * @param keyValueSource The key-value source for persistence.
 */
@HiltViewModel
class ThemeViewModel @Inject constructor(
    val themeState: ThemeState,
    private val keyValueSource: AppKeyValueSource
) : BaseViewModel() {

    /**
     * Performs the binding operation.
     */
    override fun doBind() {
        launchAsync("config") {
            val key = themeState.persistentKey
            if (key == null) {
                themeState.configStore.set(themeState.defaultConfig)
            } else {
                val strategy = ThemeConfigData.serializationStrategy
                val config = keyValueSource.read(key, strategy)?.let { mapToModel(it) }
                    ?: themeState.defaultConfig
                themeState.configStore.set(config)
                themeState.configStore.asFlow()
                    .filterNotNull()
                    .filter { it !== config }
                    .map { mapToData(it) }
                    .collectLatest { data -> keyValueSource.save(key, data, strategy) }
            }
        }
    }

    /**
     * Maps data from [ThemeConfigData] to [ThemeConfig].
     *
     * @param from The source data.
     * @return The mapped [ThemeConfig].
     */
    private fun mapToModel(from: ThemeConfigData): ThemeConfig {
        val initial = themeState.defaultConfig
        return initial.copy(
            defaultTheme = themeState.findProviderById(from.defaultThemeId) ?: initial.defaultTheme,
            lightTheme = themeState.findProviderById(from.lightThemeId) ?: initial.lightTheme,
            darkTheme = themeState.findProviderById(from.darkThemeId) ?: initial.darkTheme,
            autoDark = from.autoDark ?: initial.autoDark
        )
    }

    /**
     * Maps data from [ThemeConfig] to [ThemeConfigData].
     *
     * @param from The source data.
     * @return The mapped [ThemeConfigData].
     */
    private fun mapToData(from: ThemeConfig): ThemeConfigData {
        return ThemeConfigData(
            defaultThemeId = from.defaultTheme.id,
            lightThemeId = from.lightTheme.id,
            darkThemeId = from.darkTheme.id,
            autoDark = from.autoDark
        )
    }

}