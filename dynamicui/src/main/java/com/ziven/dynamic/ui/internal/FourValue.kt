package com.ziven.dynamic.ui.internal

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
