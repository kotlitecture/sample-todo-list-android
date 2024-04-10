# Usage

## Overview

Themes are managed through `core.ui.theme.ThemeState` instance.
This instance is pre-configured in dependency injection (DI) through the `app.di.state.ProvidesThemeState` class.

```kotlin
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
    
    ...

}
```

This state instance is utilized by `app.ui.theme.ThemeProvider`, which is pre-defined at the activity level to furnish themes for the entire application.

```kotlin
@AndroidEntryPoint
class AppActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThemeProvider {
                // ...
                // app scaffold
                // ...
            }
        }
    }

}
```

## Store/Restore Theme

The logic for storing and restoring the current theme is managed by `app.ui.theme.ThemeViewModel`.
