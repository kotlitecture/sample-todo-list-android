package app.userflow.navigation.b

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.provideHiltViewModel

@Composable
fun NavigationBScreen(data: NavigationBDestination.Data?) {
    val viewModel: NavigationBViewModel = provideHiltViewModel()
    Box(Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "B"
        )
    }
}