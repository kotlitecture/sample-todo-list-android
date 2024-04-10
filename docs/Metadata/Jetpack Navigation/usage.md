# Usage

## Overview

The integration comes with a simple API for easier usage of Jetpack Navigation.
The API is available under the package `core.ui.navigation`.

## Create Destination

To create your own destination, utilize the provided template `app.ui.screen.template.TemplateDestination`.

```kotlin
object TemplateDestination : NavigationDestination<TemplateDestination.Data>() {

    /** Unique identifier for this destination. */
    override val id: String = "template_screen"
    /** Strategy for navigation behavior. */
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    /** Strategy for handling arguments associated with this destination. */
    override val argsStrategy: ArgsStrategy<Data> = ArgsStrategy.json(Data.serializer())
    /** Binds this destination to the provided NavGraphBuilder, using one of the available methods (composable, dialog, navigation). */
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { TemplateScreen(it) }
    
    /**
     * Data class representing the arguments for the template destination.
     */
    @Serializable
    data class Data(
        val title: String
    )
}
```

## Register Destination

All app destinations should be registered within an instance of the class `core.ui.navigation.NavigationState`.
This instance is already pre-configured in dependency injection (DI) through the `app.di.state.ProvidesNavigationState` class.
Simply place all your destinations within this instance.

```kotlin
@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNavigationState {

    @Provides
    @Singleton
    fun state(): NavigationState = NavigationState(
        listOf(
            TemplateDestination,
            WebToNativeDestination,
            ...
        )
    )

}
```

## Navigate to Destination

Once the navigation is aware of the destinations, you can initiate navigation to them using the `onBack` and `onNext` methods available in the `core.ui.navigation.NavigationState` class.
Simply inject `NavigationState` into your `ViewModel` or other dependency injection managed class, and navigate whenever necessary.

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationState: NavigationState
) : BaseViewModel() {

    fun onBack() {
        navigationState.onBack()
    }
    
    fun onShowSettings() {
        navigationState.onNext(SettingsDestination)
    }

}
```

## Set Initial Destination

When the app is first opened, you need to provide **NavigationState** with an initial destination. It can be done in pre-configured **AppViewModel** class.

```kotlin
@HiltViewModel
class AppViewModel @Inject constructor(
    val navigationState: NavigationState,
) : BaseViewModel() {

    override fun doBind() {
        launchAsync("doBind") {
            // You can perform some logic before setting the initial destination.
            navigationState.setStartDestination(TemplateDestination)
        }
    }

}
```
