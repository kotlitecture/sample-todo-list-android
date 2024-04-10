package app.userflow.navigation.c

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.Serializable

object NavigationCDestination : NavigationDestination<NavigationCDestination.Data>() {

    override val id: String = "navigation_c_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override val argsStrategy: ArgsStrategy<Data> = ArgsStrategy.json(Data.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { NavigationCScreen(it) }

    @Serializable
    data class Data(
        val title: String
    )
}