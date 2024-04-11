package app.di.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AssignmentTurnedIn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.R
import app.ui.navigation.NavigationBarPage
import app.ui.navigation.NavigationBarState
import app.userflow.settings.SettingsDestination
import app.userflow.task.active.ActiveTasksDestination
import app.userflow.task.completed.CompletedTasksDestination
import app.userflow.task.search.SearchTasksDestination
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
                destination = ActiveTasksDestination,
                getActiveIcon = { Icons.AutoMirrored.Filled.Assignment },
                getInactiveIcon = { Icons.AutoMirrored.Outlined.Assignment },
                getLabel = { stringResource(R.string.navigation_active) }
            ),
            createPage(
                navigationState = navigationState,
                destination = CompletedTasksDestination,
                getActiveIcon = { Icons.Filled.AssignmentTurnedIn },
                getInactiveIcon = { Icons.Outlined.AssignmentTurnedIn },
                getLabel = { stringResource(R.string.navigation_completed) }
            ),
            createPage(
                navigationState = navigationState,
                destination = SearchTasksDestination,
                getActiveIcon = { Icons.Filled.Search },
                getInactiveIcon = { Icons.Outlined.Search },
                getLabel = { stringResource(R.string.navigation_search) }
            ),
            createPage(
                navigationState = navigationState,
                destination = SettingsDestination,
                getActiveIcon = { Icons.Filled.Settings },
                getInactiveIcon = { Icons.Outlined.Settings },
                getLabel = { stringResource(R.string.navigation_settings) }
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
