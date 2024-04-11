package app.userflow.task.active

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentPasteGo
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import app.R
import app.provideHiltViewModel
import app.ui.component.basic.ActionButton
import app.ui.container.FixedHeaderFooterLazyColumnLayout
import app.userflow.task.common.TaskList
import core.ui.theme.ThemeData

@Composable
fun ActiveTasksScreen() {
    val viewModel: ActiveTasksViewModel = provideHiltViewModel()
    val items = viewModel.flowStore.asStateValue()?.collectAsLazyPagingItems()
    FixedHeaderFooterLazyColumnLayout(
        header = {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                CreateTaskTextBlock(Modifier.weight(1f), viewModel)
                CreateTaskFromClipboardBlock(Modifier.wrapContentWidth(), viewModel)
            }
        },
        content = {
            TaskList(items, viewModel::onCheck)
        }
    )
}

@Composable
private fun CreateTaskTextBlock(modifier: Modifier, viewModel: ActiveTasksViewModel) {
    val noteStore = viewModel.noteStore
    OutlinedTextField(
        modifier = modifier.background(ThemeData.current.primary),
        shape = RoundedCornerShape(12.dp),
        value = noteStore.asStateValue().orEmpty(),
        onValueChange = noteStore::set,
        placeholder = { Text(text = stringResource(R.string.task_list_new)) },
        trailingIcon = { CreteTaskActionBlock(Modifier, viewModel) }
    )
}

@Composable
private fun CreteTaskActionBlock(modifier: Modifier, viewModel: ActiveTasksViewModel) {
    if (viewModel.noteStore.asStateValue().isNullOrBlank()) return
    ActionButton(
        modifier = modifier.padding(start = 16.dp),
        onClick = { viewModel.onCreate() },
        icon = Icons.Default.Add
    )
}

@Composable
private fun CreateTaskFromClipboardBlock(modifier: Modifier, viewModel: ActiveTasksViewModel) {
    val clipboard = viewModel.clipboardStore.asStateValue() ?: return
    ActionButton(
        modifier = modifier.padding(start = 16.dp),
        onClick = { viewModel.onCreate(clipboard) },
        icon = Icons.Default.ContentPasteGo
    )
}