package app.di.state

import app.showcases.ShowcasesDestination
import app.ui.screen.template.TemplateDestination
import app.userflow.settings.SettingsDestination
import app.userflow.task.active.ActiveTasksDestination
import app.userflow.task.completed.CompletedTasksDestination
import app.userflow.task.search.SearchTasksDestination
import app.userflow.theme.change.ChangeThemeDestination
import app.userflow.theme.change.ChangeThemeDialogDestination
import core.ui.navigation.NavigationState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNavigationState {

    @Provides
    @Singleton
    fun state(): NavigationState = NavigationState(
        destinations = listOf(
            ShowcasesDestination,
            TemplateDestination,
            ActiveTasksDestination,
            CompletedTasksDestination,
            SearchTasksDestination,
            SettingsDestination,
            ChangeThemeDestination,
            ChangeThemeDialogDestination
        )
    )

}
