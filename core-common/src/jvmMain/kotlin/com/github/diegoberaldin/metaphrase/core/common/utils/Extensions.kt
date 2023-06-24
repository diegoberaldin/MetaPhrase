package com.github.diegoberaldin.metaphrase.core.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.PI

/**
 * Converts a device independent measure to local pixel accounting for local density.
 *
 * @receiver [Dp] original measure
 * @return converted measure
 */
@Composable
fun Dp.toLocalPixel(): Float {
    return with(LocalDensity.current) {
        this@toLocalPixel.toPx()
    }
}

/**
 * Convert degrees to radians.
 *
 * @receiver angle in degree
 * @return angle in radians
 */
fun Float.toRadians(): Float {
    return (this / 180f * PI).toFloat()
}

/**
 * Retrieves the last path segment (considering platform-specific separator).
 *
 * @receiver Full path
 * @return last path component (e.g. file name)
 */
fun String.lastPathSegment(): String {
    val lastSeparatorIndex = this.lastIndexOf(System.getProperty("file.separator"))
    return substring(lastSeparatorIndex + 1)
}

/**
 * Strip the extension from a file name
 *
 * @receiver File name with extension
 * @return File name without extension
 */
fun String.stripExtension(): String {
    val lastSeparatorIndex = this.lastIndexOf(".").coerceAtLeast(0)
    return substring(0, lastSeparatorIndex)
}
