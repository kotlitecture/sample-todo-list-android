package app.userflow.task.active

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.builtins.serializer

object ActiveTasksDestination : NavigationDestination<Unit>() {

    override val id: String = "active_tasks_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.json(Unit.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { ActiveTasksScreen() }

}