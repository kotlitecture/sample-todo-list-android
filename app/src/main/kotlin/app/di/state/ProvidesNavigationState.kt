package app.di.state

import app.showcases.ShowcasesDestination
import app.ui.screen.template.TemplateDestination
import app.userflow.navigation.a.NavigationADestination
import app.userflow.navigation.b.NavigationBDestination
import app.userflow.navigation.c.NavigationCDestination
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
            NavigationADestination,
            NavigationBDestination,
            NavigationCDestination,
            ChangeThemeDestination,
            ChangeThemeDialogDestination
        )
    )

}
