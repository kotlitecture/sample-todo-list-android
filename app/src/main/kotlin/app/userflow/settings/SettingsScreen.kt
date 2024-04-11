package app.userflow.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.R
import app.ui.container.FixedTopBarColumnLayout
import app.userflow.theme.change.ChangeThemeLayout

@Composable
fun SettingsScreen() {
    FixedTopBarColumnLayout(
        title = stringResource(R.string.settings),
        content = {
            ChangeThemeLayout(modifier = Modifier.padding(16.dp))
        }
    )
}