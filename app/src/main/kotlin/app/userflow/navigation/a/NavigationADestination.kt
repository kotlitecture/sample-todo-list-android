package app.userflow.navigation.a

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.Serializable

object NavigationADestination : NavigationDestination<NavigationADestination.Data>() {

    override val id: String = "navigation_a_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override val argsStrategy: ArgsStrategy<Data> = ArgsStrategy.json(Data.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { NavigationAScreen(it) }

    @Serializable
    data class Data(
        val title: String
    )
}