package com.ziven.dynamic.ui.componentTo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp

internal data class CornerValue(
    val topLeft: Dp,
    val topRight: Dp,
    val bottomLeft: Dp,
    val bottomRight: Dp,
)

internal data class PaddingValue(
    val start: Dp,
    val top: Dp,
    val end: Dp,
    val bottom: Dp,
)

internal fun PaddingValue?.toPaddingValues(defValue: PaddingValues = PaddingValues()): PaddingValues =
    if (this == null) {
        defValue
    } else {
        PaddingValues(
            start = start,
            top = top,
            end = end,
            bottom = bottom,
        )
    }
