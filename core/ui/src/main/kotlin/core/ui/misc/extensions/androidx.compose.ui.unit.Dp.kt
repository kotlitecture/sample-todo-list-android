package core.ui.misc.extensions

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.misc.DisplayUnit

@Stable
fun Dp.toPx() = DisplayUnit.DP.toPx(value)

@Stable
fun Int.pxToDp() = DisplayUnit.PX.toDp(this).dp