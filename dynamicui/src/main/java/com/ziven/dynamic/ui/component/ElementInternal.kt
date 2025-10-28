package com.ziven.dynamic.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.internal.componentClick
import com.ziven.dynamic.ui.internal.componentUI
import com.ziven.dynamic.ui.internal.toAlign
import com.ziven.dynamic.ui.internal.toButtonColors
import com.ziven.dynamic.ui.internal.toFontColor
import com.ziven.dynamic.ui.internal.toFontFamily
import com.ziven.dynamic.ui.internal.toFontSize
import com.ziven.dynamic.ui.internal.toFontStyle
import com.ziven.dynamic.ui.internal.toFontWeight
import com.ziven.dynamic.ui.internal.toIcon
import com.ziven.dynamic.ui.internal.toIconButtonColors
import com.ziven.dynamic.ui.internal.toImage
import com.ziven.dynamic.ui.internal.toMaxLines
import com.ziven.dynamic.ui.internal.toMinLines
import com.ziven.dynamic.ui.internal.toOverflow
import com.ziven.dynamic.ui.internal.toQuality
import com.ziven.dynamic.ui.internal.toScale
import com.ziven.dynamic.ui.internal.toShape
import com.ziven.dynamic.ui.internal.toText

@Composable
internal fun SpacerComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = modifier.componentUI(uiComponent))
}

@Composable
internal fun TextComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
) {
    Text(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onClick),
        text = uiComponent.value.toText(),
        fontSize = uiComponent.style.toFontSize(),
        color = uiComponent.style.toFontColor(),
        fontWeight = uiComponent.style.toFontWeight(),
        fontStyle = uiComponent.style.toFontStyle(),
        fontFamily = uiComponent.style.toFontFamily(),
        overflow = uiComponent.style.toOverflow(),
        maxLines = uiComponent.style.toMaxLines(),
        minLines = uiComponent.style.toMinLines(),
    )
}

@Composable
internal fun ImageComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
) {
    Image(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onClick),
        painter =
            rememberAsyncImagePainter(
                model = uiComponent.value.toImage(),
                filterQuality = uiComponent.style.toQuality(),
                contentScale = uiComponent.style.toScale(),
            ),
        alignment = uiComponent.style.toAlign(),
        contentScale = uiComponent.style.toScale(),
        contentDescription = uiComponent.value.toText(),
    )
}

@Composable
internal fun ButtonComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
) {
    Button(
        modifier =
            modifier
                .componentUI(uiComponent),
        onClick = click(uiComponent, onClick),
        shape = uiComponent.style.toShape(ButtonDefaults.shape),
        colors = uiComponent.style.toButtonColors(),
    ) {
        Text(
            text = uiComponent.value.toText(),
            fontSize = uiComponent.style.toFontSize(),
            color = uiComponent.style.toFontColor(),
            fontWeight = uiComponent.style.toFontWeight(),
            fontStyle = uiComponent.style.toFontStyle(),
            fontFamily = uiComponent.style.toFontFamily(),
            overflow = uiComponent.style.toOverflow(),
            maxLines = uiComponent.style.toMaxLines(),
            minLines = uiComponent.style.toMinLines(),
        )
    }
}

@Composable
internal fun IconButtonComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
) {
    IconButton(
        modifier = modifier.componentUI(uiComponent),
        onClick = click(uiComponent, onClick),
        colors = uiComponent.style.toIconButtonColors(),
        shape = uiComponent.style.toShape(IconButtonDefaults.standardShape),
    ) {
        val icon = uiComponent.value.toIcon()
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = uiComponent.value.toText(),
            )
        } else {
            Icon(
                painter =
                    rememberAsyncImagePainter(
                        model = uiComponent.value.toImage(),
                        filterQuality = uiComponent.style.toQuality(),
                        contentScale = uiComponent.style.toScale(),
                    ),
                contentDescription = uiComponent.value.toText(),
            )
        }
    }
}
