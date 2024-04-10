package app.userflow.loader.data

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import app.provideHiltViewModel
import core.ui.state.StoreState

/**
 * Composable function for providing a data loading indicator.
 * This function displays a loading dialog when the provided state indicates that data is being loaded.
 * The dialog contains a circular progress indicator.
 *
 * @param state The state object representing the current data loading state.
 */
@Composable
fun DataLoaderProvider(state: StoreState) {
    val viewModel: DataLoaderViewModel = provideHiltViewModel()
    LaunchedEffect(state) { viewModel.onBind(state) }
    DataLoaderBlock(viewModel)
}

@Composable
private fun DataLoaderBlock(viewModel: DataLoaderViewModel) {
    val isLoading = viewModel.isLoadingStore.asStateValueNotNull()
    if(!isLoading) return
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(40.dp),
            strokeWidth = 3.dp
        )
    }
    BackHandler {}
}