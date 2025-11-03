package com.ziven.dynamic.ui.componentIn

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
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.componentTo.componentClick
import com.ziven.dynamic.ui.componentTo.componentUI
import com.ziven.dynamic.ui.componentTo.toAlign
import com.ziven.dynamic.ui.componentTo.toButtonColors
import com.ziven.dynamic.ui.componentTo.toContentPadding
import com.ziven.dynamic.ui.componentTo.toFontColor
import com.ziven.dynamic.ui.componentTo.toFontFamily
import com.ziven.dynamic.ui.componentTo.toFontSize
import com.ziven.dynamic.ui.componentTo.toFontStyle
import com.ziven.dynamic.ui.componentTo.toFontWeight
import com.ziven.dynamic.ui.componentTo.toIcon
import com.ziven.dynamic.ui.componentTo.toIconButtonColors
import com.ziven.dynamic.ui.componentTo.toImage
import com.ziven.dynamic.ui.componentTo.toMaxLines
import com.ziven.dynamic.ui.componentTo.toMinLines
import com.ziven.dynamic.ui.componentTo.toOverflow
import com.ziven.dynamic.ui.componentTo.toPaddingValues
import com.ziven.dynamic.ui.componentTo.toQuality
import com.ziven.dynamic.ui.componentTo.toScale
import com.ziven.dynamic.ui.componentTo.toShape
import com.ziven.dynamic.ui.componentTo.toText
import com.ziven.dynamic.ui.internal.logPrint

@Composable
internal fun SpacerComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    Spacer(modifier = modifier.componentUI(uiComponent))
}

@Composable
internal fun TextComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    Text(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onClick, componentList, componentState),
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
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    Image(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onClick, componentList, componentState),
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
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    logPrint("ButtonComponent: $uiComponent")
    Button(
        modifier =
            modifier
                .componentUI(uiComponent),
        onClick = click(uiComponent, onClick, componentList, componentState),
        shape = uiComponent.style.toShape(ButtonDefaults.shape),
        colors = uiComponent.style.toButtonColors(),
        contentPadding = uiComponent.layout.toContentPadding().toPaddingValues(ButtonDefaults.ContentPadding),
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
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    IconButton(
        modifier = modifier.componentUI(uiComponent),
        onClick = click(uiComponent, onClick, componentList, componentState),
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
