# Usage

## Overview

- Component package: `app.ui.navigation.bottom`
- DI integration: `app.di.state.ProvidesNavigationBarState`
- State management: `app.ui.navigation.NavigationBarState`
- Pre-configured destinations package: `app.userflow.navigation`


## Configuration

Configure your destinations via `ProvidesNavigationBarState`.

```kotlin
@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNavigationBarState {

    @Provides
    @Singleton
    fun state(navigationState: NavigationState): NavigationBarState = NavigationBarState(
        pages = listOf(
            createPage(
                navigationState = navigationState,
                destination = ShowcasesDestination,
                getActiveIcon = { Icons.Filled.School },
                getInactiveIcon = { Icons.Outlined.School },
                getLabel = { "Showcases" }
            ),
            ...
        )
    )
}
```
