package com.ziven.dynamic.ui.internal

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

internal const val LOG_ENABLE = true
internal const val LOG_TAG = "UIComponent"

internal fun logPrint(
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

internal fun <T> runSafe(
    tag: String = "runSafe",
    block: () -> T,
): T? =
    try {
        block()
    } catch (e: Exception) {
        logPrint("$tag error", e)
        null
    }

internal fun String?.toColor(): Color? = this?.let { runSafe("toColor") { Color(it.toColorInt()) } }

@OptIn(ExperimentalContracts::class)
internal fun String?.notEmpty(): Boolean {
    contract {
        returns(true) implies (this@notEmpty != null)
    }
    return !this.isNullOrEmpty()
}

internal fun <T> String?.notEmpty(block: (String) -> T?): T? = if (this.notEmpty()) block.invoke(this) else null

internal fun Float?.toDp(): Dp = this?.dp ?: 0.0.dp

internal fun Float?.toSp(): TextUnit = this?.sp ?: TextUnit.Unspecified
