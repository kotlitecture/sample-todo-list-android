package app.ui.component.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

/**
 * Composable function for rendering an icon block.
 *
 * @param modifier The modifier to apply to the icon block.
 * @param tint The tint color to apply to the icon.
 * @param size The size of the icon.
 * @param model The model representing the icon. It can be an ImageVector, drawable resource ID, Color, or null.
 */
@Composable
fun AnyIcon(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    size: Dp = Dp.Unspecified,
    model: Any?
) {
    when (model) {
        is ImageVector -> {
            Icon(
                modifier = modifier.size(size),
                contentDescription = null,
                imageVector = model,
                tint = tint
            )
        }

        is Int -> {
            Icon(
                modifier = modifier.size(size),
                painter = painterResource(model),
                contentDescription = null,
                tint = tint
            )
        }

        is Color -> {
            Box(
                modifier = modifier
                    .size(size)
                    .background(model)
            )
        }

        else -> {
            Box(
                modifier = modifier
                    .size(size)
                    .background(tint)
            )
        }
    }
}