package com.ziven.dynamic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.ziven.dynamic.ui.internal.componentClick
import com.ziven.dynamic.ui.internal.componentLayout
import com.ziven.dynamic.ui.internal.componentUI
import com.ziven.dynamic.ui.internal.toAlign
import com.ziven.dynamic.ui.internal.toBackgroundColor
import com.ziven.dynamic.ui.internal.toButtonColors
import com.ziven.dynamic.ui.internal.toFabPosition
import com.ziven.dynamic.ui.internal.toFontColor
import com.ziven.dynamic.ui.internal.toFontFamily
import com.ziven.dynamic.ui.internal.toFontSize
import com.ziven.dynamic.ui.internal.toFontStyle
import com.ziven.dynamic.ui.internal.toFontWeight
import com.ziven.dynamic.ui.internal.toForegroundColor
import com.ziven.dynamic.ui.internal.toHorizontalAlign
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
import com.ziven.dynamic.ui.internal.toVerticalAlign

@Composable
fun RenderComponent(
    componentType: String?,
    componentList: ComponentList? = null,
    onClick: (ComponentAction) -> Unit,
) = UIManager.getComponent(componentType)?.let { RenderComponent(it, componentList, onClick) }

@Composable
fun RenderComponent(
    uiComponent: UIComponent,
    componentList: ComponentList? = null,
    onClick: (ComponentAction) -> Unit,
) = DispatchRenderComponent(uiComponent, Modifier, onClick, componentList)

@Composable
internal fun DispatchRenderComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) {
    logPrint("RenderComponent: $uiComponent")
    when (uiComponent.componentName) {
        "Scaffold" -> ScaffoldComponent(uiComponent, modifier, onClick, componentList)
        "TopBar" -> TopBarComponent(uiComponent, modifier, onClick)
        "BottomBar" -> BottomBarComponent(uiComponent, modifier, onClick)
        "SnackBar" -> SnackBarComponent(uiComponent, modifier, onClick)
        "FloatingActionButton" -> FloatingActionButtonComponent(uiComponent, modifier, onClick)
        "LazyColumn" -> LazyColumnComponent(uiComponent, modifier, onClick, componentList)
        "LazyRow" -> LazyRowComponent(uiComponent, modifier, onClick, componentList)
        "Column" -> ColumnComponent(uiComponent, modifier, onClick, componentList)
        "Row" -> RowComponent(uiComponent, modifier, onClick, componentList)
        "Box" -> BoxComponent(uiComponent, modifier, onClick, componentList)
        "Spacer" -> SpacerComponent(uiComponent, modifier)
        "Text" -> TextComponent(uiComponent, modifier, onClick)
        "Image" -> ImageComponent(uiComponent, modifier, onClick)
        "Button" -> ButtonComponent(uiComponent, modifier, onClick)
        "IconButton" -> IconButtonComponent(uiComponent, modifier, onClick)
        else ->
            UIManager.getComponent(uiComponent.componentType)?.let {
                DispatchRenderComponent(it, modifier, onClick, componentList)
            }
    }
}

@Composable
internal fun ScaffoldComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) {
    Scaffold(
        modifier = uiComponent.layout?.let { modifier.componentLayout(it) } ?: modifier,
        topBar = {
            uiComponent.findComponentWithName("TopBar")?.let {
                DispatchRenderComponent(it, Modifier, onClick, componentList)
            }
        },
        bottomBar = {
            uiComponent.findComponentWithName("BottomBar")?.let {
                DispatchRenderComponent(it, Modifier, onClick, componentList)
            }
        },
        snackbarHost = {
            uiComponent.findComponentWithName("SnackBar")?.let {
                DispatchRenderComponent(it, Modifier, onClick, componentList)
            }
        },
        floatingActionButton = {
            uiComponent.findComponentWithName("FloatingActionButton")?.let {
                DispatchRenderComponent(it, Modifier, onClick, componentList)
            }
        },
        floatingActionButtonPosition = uiComponent.style.toFabPosition(),
        containerColor =
            uiComponent.style.toBackgroundColor()
                ?: MaterialTheme.colorScheme.background,
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            uiComponent.ForEachComponent { child ->
                when (child.componentName) {
                    "TopBar" -> return@ForEachComponent
                    "BottomBar" -> return@ForEachComponent
                    "SnackBar" -> return@ForEachComponent
                    "FloatingActionButton" -> return@ForEachComponent
                    else -> DispatchRenderComponent(child, Modifier, onClick, componentList)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopBarComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
) {
    TopAppBar(
        modifier = modifier.componentUI(uiComponent),
        title = {
            Text(uiComponent.value.toText())
        },
        navigationIcon = {
            uiComponent.ForEachComponent { child ->
                DispatchRenderComponent(child, Modifier, onClick)
            }
        },
    )
}

@Composable
internal fun BottomBarComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
) {
    BottomAppBar(
        modifier = uiComponent.layout?.let { modifier.componentLayout(it) } ?: modifier,
        containerColor =
            uiComponent.style.toBackgroundColor()
                ?: FloatingActionButtonDefaults.containerColor,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            uiComponent.ForEachComponent {
                DispatchRenderComponent(
                    it,
                    Modifier.align(Alignment.CenterVertically),
                    onClick,
                )
            }
        }
    }
}

@Composable
internal fun SnackBarComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
) {
    Snackbar(
        modifier = modifier.componentUI(uiComponent),
    ) {
        uiComponent.ForEachComponent { DispatchRenderComponent(it, Modifier, onClick) }
    }
}

@Composable
internal fun FloatingActionButtonComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
) {
    val containerColor =
        uiComponent.style.toBackgroundColor() ?: FloatingActionButtonDefaults.containerColor
    val contentColor = uiComponent.style.toForegroundColor() ?: contentColorFor(containerColor)
    FloatingActionButton(
        onClick = {
            onClick(
                ComponentAction(
                    componentId = uiComponent.componentId,
                    value = uiComponent.value,
                ),
            )
        },
        modifier = uiComponent.layout?.let { modifier.componentLayout(it) } ?: modifier,
        shape = uiComponent.style.toShape(FloatingActionButtonDefaults.shape),
        containerColor = containerColor,
        contentColor = contentColor,
    ) {
        uiComponent.ForEachComponent { DispatchRenderComponent(it, Modifier, onClick) }
    }
}

@Composable
internal fun LazyColumnComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) {
    LazyColumn(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onClick),
    ) {
        componentList?.let {
            val size = it.componentSize()
            items(
                count = size.intValue,
                key = it.componentKey,
                contentType = { index -> it.componentType(index) },
            ) { index ->
                ItemsComponent(index, it, onClick)
            }
        }
    }
}

@Composable
internal fun LazyRowComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) {
    LazyRow(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onClick),
    ) {
        componentList?.let {
            val size = it.componentSize()
            items(
                count = size.intValue,
                key = it.componentKey,
                contentType = { index -> it.componentType(index) },
            ) { index ->
                ItemsComponent(index, it, onClick)
            }
        }
    }
}

@Composable
private fun ItemsComponent(
    index: Int,
    componentList: ComponentList,
    onClick: (ComponentAction) -> Unit,
) {
    val type = componentList.componentType(index) ?: return
    val parent = UIManager.getComponent(type) ?: return
    parent.forEachComponent { child ->
        child.componentId?.let { childId ->
            componentList.componentData(index, childId)?.let {
                child.updateComponentValue(it)
            }
        }
    }
    DispatchRenderComponent(parent, Modifier, onClick, componentList)
}

@Composable
internal fun ColumnComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) {
    Column(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onClick),
    ) {
        uiComponent.ForEachComponent { child ->
            DispatchRenderComponent(
                child,
                Modifier.align(child.style.toHorizontalAlign()),
                onClick,
                componentList,
            )
        }
    }
}

@Composable
internal fun RowComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) {
    Row(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onClick),
    ) {
        uiComponent.ForEachComponent { child ->
            DispatchRenderComponent(
                child,
                Modifier.align(child.style.toVerticalAlign()),
                onClick,
                componentList,
            )
        }
    }
}

@Composable
internal fun BoxComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) {
    Box(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onClick),
    ) {
        uiComponent.ForEachComponent { child ->
            DispatchRenderComponent(
                child,
                Modifier.align(child.style.toAlign()),
                onClick,
                componentList,
            )
        }
    }
}

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
        onClick = {
            onClick(
                ComponentAction(
                    uiComponent.componentId,
                    uiComponent.value,
                ),
            )
        },
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
        modifier =
            modifier
                .componentUI(uiComponent),
        onClick = {
            onClick(
                ComponentAction(
                    uiComponent.componentId,
                    uiComponent.value,
                ),
            )
        },
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
