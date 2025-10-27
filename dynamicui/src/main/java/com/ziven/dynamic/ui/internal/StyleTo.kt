package com.ziven.dynamic.ui.internal

import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.ziven.dynamic.ui.ComponentStyle
import com.ziven.dynamic.ui.toColor
import com.ziven.dynamic.ui.toDp
import com.ziven.dynamic.ui.toSp

@Composable
internal fun ComponentStyle?.toButtonColors(): ButtonColors =
    this.toBackgroundColor()?.let {
        ButtonDefaults.buttonColors(containerColor = it)
    } ?: ButtonDefaults.buttonColors()

@Composable
internal fun ComponentStyle?.toIconButtonColors(): IconButtonColors =
    IconButtonDefaults.iconButtonColors(
        containerColor = this.toBackgroundColor() ?: Color.Unspecified,
        contentColor = this.toForegroundColor() ?: LocalContentColor.current,
    )

internal fun ComponentStyle?.toBackgroundColor(): Color? = this?.backgroundColor.toColor()

internal fun ComponentStyle?.toForegroundColor(): Color? = this?.foregroundColor.toColor()

@Composable
internal fun ComponentStyle?.toShape(defSharp: Shape): Shape =
    this.toCornerValue()?.let {
        AbsoluteRoundedCornerShape(
            topLeft = it.topLeft,
            topRight = it.topRight,
            bottomLeft = it.bottomLeft,
            bottomRight = it.bottomRight,
        )
    } ?: defSharp

internal fun ComponentStyle?.toCornerValue(): CornerValue? =
    if (this == null) {
        null
    } else if (topLeft == null && topRight == null && bottomLeft == null && bottomRight == null) {
        null
    } else {
        CornerValue(
            topLeft = topLeft.toDp(),
            topRight = topRight.toDp(),
            bottomLeft = bottomLeft.toDp(),
            bottomRight = bottomRight.toDp(),
        )
    }

internal fun ComponentStyle?.toFontSize(): TextUnit = this?.fontSize?.toSp() ?: TextUnit.Unspecified

internal fun ComponentStyle?.toFontColor(): Color = this?.fontColor?.toColor() ?: Color.Unspecified

internal fun ComponentStyle?.toFontStyle(): FontStyle? =
    when (this?.fontStyle) {
        "Normal" -> FontStyle.Normal
        "Italic" -> FontStyle.Italic
        else -> null
    }

internal fun ComponentStyle?.toFontWeight(): FontWeight? =
    when (this?.fontWeight) {
        "Thin" -> FontWeight.Thin
        "ExtraLight" -> FontWeight.ExtraLight
        "Light" -> FontWeight.Light
        "Normal" -> FontWeight.Normal
        "Medium" -> FontWeight.Medium
        "SemiBold" -> FontWeight.SemiBold
        "Bold" -> FontWeight.Bold
        "ExtraBold" -> FontWeight.ExtraBold
        "Black" -> FontWeight.Black
        else -> null
    }

internal fun ComponentStyle?.toFontFamily(): FontFamily? =
    when (this?.fontFamily) {
        "SansSerif" -> FontFamily.SansSerif
        "Serif" -> FontFamily.Serif
        "Monospace" -> FontFamily.Monospace
        "Cursive" -> FontFamily.Cursive
        else -> null
    }

internal fun ComponentStyle?.toOverflow(): TextOverflow =
    when (this?.overflow) {
        "Clip" -> TextOverflow.Clip
        "Ellipsis" -> TextOverflow.Ellipsis
        "Visible" -> TextOverflow.Visible
        "StartEllipsis" -> TextOverflow.StartEllipsis
        "MiddleEllipsis" -> TextOverflow.MiddleEllipsis
        else -> TextOverflow.Clip
    }

internal fun ComponentStyle?.toMaxLines(): Int = this?.maxLines ?: Int.MAX_VALUE

internal fun ComponentStyle?.toMinLines(): Int = this?.minLines ?: 1

internal fun ComponentStyle?.toAlign(): Alignment =
    when (this?.align) {
        "TopStart" -> Alignment.TopStart
        "TopCenter" -> Alignment.TopCenter
        "TopEnd" -> Alignment.TopEnd

        "CenterStart" -> Alignment.CenterStart
        "Center" -> Alignment.Center
        "CenterEnd" -> Alignment.CenterEnd

        "BottomStart" -> Alignment.BottomStart
        "BottomCenter" -> Alignment.BottomCenter
        "BottomEnd" -> Alignment.BottomEnd
        else -> Alignment.Center
    }

internal fun ComponentStyle?.toVerticalAlign(): Alignment.Vertical =
    when (this?.align) {
        "Top" -> Alignment.Top
        "CenterVertically" -> Alignment.CenterVertically
        "Bottom" -> Alignment.Bottom
        else -> Alignment.CenterVertically
    }

internal fun ComponentStyle?.toHorizontalAlign(): Alignment.Horizontal =
    when (this?.align) {
        "Start" -> Alignment.Start
        "CenterHorizontally" -> Alignment.CenterHorizontally
        "End" -> Alignment.End
        else -> Alignment.CenterHorizontally
    }

internal fun ComponentStyle?.toScale(): ContentScale =
    when (this?.scale) {
        "Crop" -> ContentScale.Crop
        "Fit" -> ContentScale.Fit
        "FillHeight" -> ContentScale.FillHeight
        "FillWidth" -> ContentScale.FillWidth
        "Inside" -> ContentScale.Inside
        "None" -> ContentScale.None
        "FillBounds" -> ContentScale.FillBounds
        else -> ContentScale.Fit
    }

internal fun ComponentStyle?.toQuality(): FilterQuality =
    when (this?.quality) {
        "None" -> FilterQuality.None
        "High" -> FilterQuality.High
        "Medium" -> FilterQuality.Medium
        "Low" -> FilterQuality.Low
        else -> FilterQuality.Low
    }

internal fun ComponentStyle?.toFabPosition(): FabPosition =
    when (this?.fabPosition) {
        "End" -> FabPosition.End
        "Start" -> FabPosition.Start
        "EndOverlay" -> FabPosition.EndOverlay
        "Center" -> FabPosition.Center
        else -> FabPosition.End
    }
