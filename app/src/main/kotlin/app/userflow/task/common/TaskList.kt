package app.userflow.task.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import app.R
import app.data.TaskData
import app.ui.component.basic.Spacer8

fun LazyListScope.TaskList(
    items: LazyPagingItems<TaskData>?,
    onClick: (data: TaskData?) -> Unit,
    emptyState: @Composable () -> Unit = {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.task_list_empty),
            textAlign = TextAlign.Center
        )
    },
    loadingState: @Composable () -> Unit = {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.task_list_loading),
            textAlign = TextAlign.Center
        )
    },
) {
    val refreshState = items?.loadState?.refresh
    when {
        items == null -> {

        }

        items.itemCount == 0 && refreshState is LoadState.Loading -> {
            item { loadingState() }
        }

        items.itemCount == 0 && refreshState is LoadState.NotLoading -> {
            item { emptyState() }
        }

        else -> {
            items(items.itemCount, key = { items.peek(it)?.task?.id ?: it }) { index ->
                NoteBlock(data = items[index], onClick)
                Spacer8()
            }
        }
    }
}