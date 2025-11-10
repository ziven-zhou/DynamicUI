package com.ziven.dynamic.ui.componentTo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.contentColorFor
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
import com.ziven.dynamic.ui.internal.toColor
import com.ziven.dynamic.ui.internal.toDp
import com.ziven.dynamic.ui.internal.toSp

@Composable
internal fun ComponentStyle?.toButtonColors(): ButtonColors =
    this?.backgroundColor.toColor()?.let {
        ButtonDefaults.buttonColors(containerColor = it)
    } ?: ButtonDefaults.buttonColors()

@Composable
internal fun ComponentStyle?.toIconButtonColors(): IconButtonColors =
    IconButtonDefaults.iconButtonColors(
        containerColor = this.toBackgroundColor(Color.Unspecified),
        contentColor = this.toForegroundColor(LocalContentColor.current),
    )

internal fun ComponentStyle?.toBackgroundColor(defColor: Color): Color = this?.backgroundColor.toColor() ?: defColor

@Composable
internal fun ComponentStyle?.toContainerColor(defColor: Color): Color = this.toBackgroundColor(defColor)

internal fun ComponentStyle?.toForegroundColor(defColor: Color): Color = this?.foregroundColor.toColor() ?: defColor

@Composable
internal fun ComponentStyle?.toContentColor(defColor: Color): Color =
    this.toForegroundColor(contentColorFor(this.toContainerColor(defColor)))

internal fun ComponentStyle?.toColors(): Map<String, Color>? {
    val colors = this?.colors ?: return null
    val result = mutableMapOf<String, Color>()
    colors.forEach { (key, value) ->
        val color = value.toColor()
        if (color != null) {
            result[key] = color
        }
    }
    return result.ifEmpty { null }
}

@Composable
internal fun Map<String, Color>?.toSwitchColors(): SwitchColors =
    this?.let { map ->
        SwitchDefaults.colors().let { def ->
            def.copy(
                checkedThumbColor = map["checkedThumbColor"] ?: def.checkedThumbColor,
                checkedTrackColor = map["checkedTrackColor"] ?: def.checkedTrackColor,
                checkedBorderColor = map["checkedBorderColor"] ?: def.checkedBorderColor,
                checkedIconColor = map["checkedIconColor"] ?: def.checkedIconColor,
                uncheckedThumbColor = map["uncheckedThumbColor"] ?: def.uncheckedThumbColor,
                uncheckedTrackColor = map["uncheckedTrackColor"] ?: def.uncheckedTrackColor,
                uncheckedBorderColor = map["uncheckedBorderColor"] ?: def.uncheckedBorderColor,
                uncheckedIconColor = map["uncheckedIconColor"] ?: def.uncheckedIconColor,
                disabledCheckedThumbColor = map["disabledCheckedThumbColor"] ?: def.disabledCheckedThumbColor,
                disabledUncheckedThumbColor = map["disabledUncheckedThumbColor"] ?: def.disabledUncheckedThumbColor,
                disabledCheckedTrackColor = map["disabledCheckedTrackColor"] ?: def.disabledCheckedTrackColor,
                disabledUncheckedTrackColor = map["disabledUncheckedTrackColor"] ?: def.disabledUncheckedTrackColor,
                disabledCheckedBorderColor = map["disabledCheckedBorderColor"] ?: def.disabledCheckedBorderColor,
                disabledUncheckedBorderColor = map["disabledUncheckedBorderColor"] ?: def.disabledUncheckedBorderColor,
                disabledCheckedIconColor = map["disabledCheckedIconColor"] ?: def.disabledCheckedIconColor,
                disabledUncheckedIconColor = map["disabledUncheckedIconColor"] ?: def.disabledUncheckedIconColor,
            )
        }
    } ?: SwitchDefaults.colors()

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

internal fun ComponentStyle?.toOverflow(defValue: TextOverflow = TextOverflow.Clip): TextOverflow =
    when (this?.overflow) {
        "Clip" -> TextOverflow.Clip
        "Ellipsis" -> TextOverflow.Ellipsis
        "Visible" -> TextOverflow.Visible
        "StartEllipsis" -> TextOverflow.StartEllipsis
        "MiddleEllipsis" -> TextOverflow.MiddleEllipsis
        else -> defValue
    }

internal fun ComponentStyle?.toMaxLines(): Int = this?.maxLines ?: Int.MAX_VALUE

internal fun ComponentStyle?.toMinLines(): Int = this?.minLines ?: 1

internal fun ComponentStyle?.toAlign(defValue: Alignment = Alignment.Center): Alignment =
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
        else -> defValue
    }

internal fun ComponentStyle?.toVerticalAlign(defValue: Alignment.Vertical = Alignment.Top): Alignment.Vertical =
    when (this?.align) {
        "Top" -> Alignment.Top
        "CenterVertically" -> Alignment.CenterVertically
        "Bottom" -> Alignment.Bottom
        else -> defValue
    }

internal fun ComponentStyle?.toHorizontalAlign(defValue: Alignment.Horizontal = Alignment.Start): Alignment.Horizontal =
    when (this?.align) {
        "Start" -> Alignment.Start
        "CenterHorizontally" -> Alignment.CenterHorizontally
        "End" -> Alignment.End
        else -> defValue
    }

internal fun ComponentStyle?.toVerticalAlign2(defValue: Arrangement.Vertical = Arrangement.Top): Arrangement.Vertical =
    when (this?.align) {
        "Top" -> Arrangement.Top
        "Bottom" -> Arrangement.Bottom
        "Center" -> Arrangement.Center
        "SpaceAround" -> Arrangement.SpaceAround
        "SpaceBetween" -> Arrangement.SpaceBetween
        "SpaceEvenly" -> Arrangement.SpaceEvenly
        else -> defValue
    }

internal fun ComponentStyle?.toHorizontalAlign2(defValue: Arrangement.Horizontal = Arrangement.Start): Arrangement.Horizontal =
    when (this?.align) {
        "Start" -> Arrangement.Start
        "End" -> Arrangement.End
        "Center" -> Arrangement.Center
        "SpaceAround" -> Arrangement.SpaceAround
        "SpaceBetween" -> Arrangement.SpaceBetween
        "SpaceEvenly" -> Arrangement.SpaceEvenly
        else -> defValue
    }

internal fun ComponentStyle?.toScale(defValue: ContentScale = ContentScale.Fit): ContentScale =
    when (this?.scale) {
        "Crop" -> ContentScale.Crop
        "Fit" -> ContentScale.Fit
        "FillHeight" -> ContentScale.FillHeight
        "FillWidth" -> ContentScale.FillWidth
        "Inside" -> ContentScale.Inside
        "None" -> ContentScale.None
        "FillBounds" -> ContentScale.FillBounds
        else -> defValue
    }

internal fun ComponentStyle?.toQuality(defValue: FilterQuality = FilterQuality.Low): FilterQuality =
    when (this?.quality) {
        "None" -> FilterQuality.None
        "High" -> FilterQuality.High
        "Medium" -> FilterQuality.Medium
        "Low" -> FilterQuality.Low
        else -> defValue
    }

internal fun ComponentStyle?.toFabPosition(defValue: FabPosition = FabPosition.End): FabPosition =
    when (this?.fabPosition) {
        "End" -> FabPosition.End
        "Start" -> FabPosition.Start
        "EndOverlay" -> FabPosition.EndOverlay
        "Center" -> FabPosition.Center
        else -> defValue
    }
