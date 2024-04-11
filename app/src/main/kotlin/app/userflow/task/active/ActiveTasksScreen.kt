package app.userflow.task.active

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.provideHiltViewModel

@Composable
fun ActiveTasksScreen() {
    val viewModel: ActiveTasksViewModel = provideHiltViewModel()
    Box(Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Active Tasks"
        )
    }
}