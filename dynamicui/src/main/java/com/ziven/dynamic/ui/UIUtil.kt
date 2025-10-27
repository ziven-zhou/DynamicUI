package com.ziven.dynamic.ui

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

internal const val LOG_ENABLE = true
internal const val LOG_TAG = "UIComponent"

fun logPrint(
    msg: String,
    th: Throwable? = null,
) {
    if (LOG_ENABLE) {
        if (th == null) {
            Log.d(LOG_TAG, msg)
        } else {
            Log.d(LOG_TAG, msg, th)
        }
    }
}

fun <T> runSafe(
    tag: String = "runSafe",
    block: () -> T,
): T? =
    try {
        block()
    } catch (e: Exception) {
        logPrint("$tag error", e)
        null
    }

internal fun String?.toColor(): Color? =
    if (this == null) {
        null
    } else {
        runSafe("toColor") {
            Color(
                toColorInt(),
            )
        }
    }

internal fun Float?.toDp(): Dp = this?.dp ?: 0.0.dp

internal fun Float?.toSp(): TextUnit = this?.sp ?: TextUnit.Unspecified
