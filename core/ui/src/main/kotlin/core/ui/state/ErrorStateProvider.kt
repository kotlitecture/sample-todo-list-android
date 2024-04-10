package core.ui.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.ui.theme.ThemeData

/**
 * Provides a composable UI component for handling data loading and error states.
 *
 * @param state The [StoreState] object containing the data state.
 * @param content The composable function to be executed when an error state occurs. It receives a [DataState.Error]
 *     object as a parameter, which contains information about the error.
 */
@Composable
fun ErrorStateProvider(
    state: StoreState,
    content: @Composable (error: DataState.Error) -> Unit = { error ->
        val radius = 24.dp
        val padding = 24.dp
        val contentPadding = 16.dp
        val height = LocalConfiguration.current.screenHeightDp.times(0.55f).dp
        Box(
            modifier = Modifier
                .padding(horizontal = padding)
                .clip(RoundedCornerShape(radius))
                .heightIn(max = height)
                .background(ThemeData.current.primary)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = contentPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(contentPadding))
                Text(
                    text = error.id,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W600,
                    color = ThemeData.current.onPrimary
                )
                Spacer(modifier = Modifier.height(contentPadding))
                Text(
                    text = error.th.stackTraceToString(),
                    fontSize = 16.sp,
                    color = ThemeData.current.onPrimary
                )
                Spacer(modifier = Modifier.height(contentPadding))
            }
        }
    }
) {
    val error = state.dataStateStore.asStateValue() as? DataState.Error ?: return
    Dialog(
        onDismissRequest = state.dataStateStore::clear,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        content(error)
    }
}