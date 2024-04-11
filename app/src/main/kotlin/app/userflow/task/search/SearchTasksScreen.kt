package app.userflow.task.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
fun SearchTasksScreen() {
    val viewModel: SearchTasksViewModel = provideHiltViewModel()
    val items = viewModel.flowStore.asStateValue()?.collectAsLazyPagingItems()
    FixedHeaderFooterLazyColumnLayout(
        header = { SearchTaskTextBlock(Modifier, viewModel) },
        content = { TaskList(items, viewModel::onClick) }
    )
}

@Composable
private fun SearchTaskTextBlock(modifier: Modifier, viewModel: SearchTasksViewModel) {
    val queryStore = viewModel.queryStore
    val focusRequester = remember { FocusRequester() }
    OutlinedTextField(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(ThemeData.current.primary)
            .focusRequester(focusRequester),
        shape = RoundedCornerShape(12.dp),
        value = queryStore.asStateValue().orEmpty(),
        onValueChange = queryStore::set,
        placeholder = { Text(text = stringResource(R.string.task_list_search)) },
        trailingIcon = {
            if (!queryStore.asStateValue().isNullOrBlank()) {
                ActionButton(
                    onClick = queryStore::clear,
                    icon = Icons.Default.Cancel
                )
            }
        }
    )
    LaunchedEffect(viewModel) {
        focusRequester.requestFocus()
    }
}