package app.userflow.navigation.b

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.Serializable

object NavigationBDestination : NavigationDestination<NavigationBDestination.Data>() {

    override val id: String = "navigation_b_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override val argsStrategy: ArgsStrategy<Data> = ArgsStrategy.json(Data.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { NavigationBScreen(it) }

    @Serializable
    data class Data(
        val title: String
    )
}