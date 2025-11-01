package com.ziven.dynamic.ui.componentTo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentLayout
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.ComponentStyle
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.componentIn.click
import com.ziven.dynamic.ui.internal.toColor

internal fun Modifier.componentUI(uiComponent: UIComponent): Modifier =
    this.componentLayout(uiComponent.layout).componentStyle(uiComponent.style)

internal fun Modifier.componentLayout(layout: ComponentLayout?): Modifier =
    if (layout == null) this else componentSize(layout).componentPadding(layout)

internal fun Modifier.componentSize(layout: ComponentLayout): Modifier =
    this
        .let { modifier ->
            layout.width?.let { modifier.componentWidth(it) } ?: modifier
        }.let { modifier ->
            layout.height?.let { modifier.componentHeight(it) } ?: modifier
        }

internal fun Modifier.componentWidth(width: Int): Modifier =
    if (width == -1) {
        fillMaxWidth()
    } else if (width > 0) {
        width(width.dp)
    } else {
        this
    }

internal fun Modifier.componentHeight(height: Int): Modifier =
    if (height == -1) {
        fillMaxHeight()
    } else if (height > 0) {
        height(height.dp)
    } else {
        this
    }

internal fun Modifier.componentPadding(layout: ComponentLayout): Modifier =
    layout.toPadding()?.let {
        this.padding(
            start = it.start,
            end = it.end,
            top = it.top,
            bottom = it.bottom,
        )
    } ?: this

internal fun Modifier.componentStyle(style: ComponentStyle?): Modifier =
    if (style == null) this else componentSharp(style).componentBackgroundColor(style)

internal fun Modifier.componentSharp(style: ComponentStyle): Modifier =
    style.toCornerValue()?.let {
        this.clip(
            shape =
                AbsoluteRoundedCornerShape(
                    topLeft = it.topLeft,
                    topRight = it.topRight,
                    bottomLeft = it.bottomLeft,
                    bottomRight = it.bottomRight,
                ),
        )
    } ?: this

internal fun Modifier.componentBackgroundColor(style: ComponentStyle): Modifier =
    style.backgroundColor.toColor()?.let { this.background(color = it) } ?: this

internal fun Modifier.componentClick(
    uiComponent: UIComponent,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Modifier =
    if (uiComponent.value.toClickable()) {
        this.clickable { click(uiComponent, onClick, componentList, componentState)() }
    } else {
        this
    }
