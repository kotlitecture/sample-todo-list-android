package app.di.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.LocalDrink
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.WineBar
import androidx.compose.runtime.Composable
import app.showcases.ShowcasesDestination
import app.ui.navigation.NavigationBarPage
import app.ui.navigation.NavigationBarState
import app.userflow.navigation.a.NavigationADestination
import app.userflow.navigation.b.NavigationBDestination
import app.userflow.navigation.c.NavigationCDestination
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationState
import core.ui.navigation.NavigationStrategy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
            createPage(
                navigationState = navigationState,
                destination = NavigationADestination,
                getActiveIcon = { Icons.Filled.WineBar },
                getInactiveIcon = { Icons.Outlined.WineBar },
                getLabel = { "Page 1" }
            ),
            createPage(
                navigationState = navigationState,
                destination = NavigationBDestination,
                getActiveIcon = { Icons.Filled.LocalDrink },
                getInactiveIcon = { Icons.Outlined.LocalDrink },
                getLabel = { "Page 2" }
            ),
            createPage(
                navigationState = navigationState,
                destination = NavigationCDestination,
                getActiveIcon = { Icons.Filled.Coffee },
                getInactiveIcon = { Icons.Outlined.Coffee },
                getLabel = { "Page 3" }
            )
        )
    )

    private fun <D> createPage(
        navigationState: NavigationState,
        destination: NavigationDestination<D>,
        getInactiveIcon: () -> Any,
        getActiveIcon: () -> Any,
        getLabel: @Composable () -> String?,
    ): NavigationBarPage {
        return NavigationBarPage(
            enabled = true,
            id = destination.id,
            getLabel = getLabel,
            alwaysShowLabel = false,
            getActiveIcon = getActiveIcon,
            getInactiveIcon = getInactiveIcon,
            onClick = { navigate(navigationState, destination) }
        )
    }

    private fun <D> navigate(
        navigationState: NavigationState,
        destination: NavigationDestination<D>
    ) {
        navigationState.onNext(
            destination = destination,
            strategy = NavigationStrategy.SingleInstance
        )
    }

}
