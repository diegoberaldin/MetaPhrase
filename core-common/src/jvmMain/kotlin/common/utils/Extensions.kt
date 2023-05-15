package common.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.PI

@Composable
fun Dp.toLocalPixel(): Float {
    return with(LocalDensity.current) {
        this@toLocalPixel.toPx()
    }
}

fun Float.toRadians(): Float {
    return (this / 180f * PI).toFloat()
}

fun String.lastPathSegment(): String {
    val lastSeparatorIndex = this.lastIndexOf(System.getProperty("file.separator"))
    return substring(lastSeparatorIndex + 1)
}

fun String.stripExtension(): String {
    val lastSeparatorIndex = this.lastIndexOf(".").coerceAtLeast(0)
    return substring(0, lastSeparatorIndex)
}
