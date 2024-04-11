package app.userflow.task.completed

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import app.R
import app.provideHiltViewModel
import app.ui.container.FixedTopBarLazyColumnLayout
import app.userflow.task.common.TaskList

@Composable
fun ActiveTasksScreen() {
    val viewModel: CompletedTasksViewModel = provideHiltViewModel()
    val items = viewModel.flowStore.asStateValue()?.collectAsLazyPagingItems()
    FixedTopBarLazyColumnLayout(
        title = stringResource(R.string.task_list_completed),
        content = { TaskList(items, viewModel::onCheck) }
    )
}