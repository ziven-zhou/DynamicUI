package com.ziven.dynamic.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.UIManager
import com.ziven.dynamic.ui.logPrint

@Composable
internal fun DispatchRenderComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) {
    logPrint("RenderComponent: $uiComponent")
    if (dispatchScaffoldComponent(uiComponent, modifier, onClick, componentList)) return
    if (dispatchListComponent(uiComponent, modifier, onClick, componentList)) return
    if (dispatchContainerComponent(uiComponent, modifier, onClick, componentList)) return
    if (dispatchElementComponent(uiComponent, modifier, onClick, componentList)) return
    UIManager.getComponent(uiComponent.componentType)?.let {
        DispatchRenderComponent(it, modifier, onClick, componentList)
    }
}

@Composable
private fun dispatchScaffoldComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) = when (uiComponent.componentName) {
    "Scaffold" -> {
        ScaffoldComponent(uiComponent, modifier, onClick, componentList)
        true
    }

    "TopBar" -> {
        TopBarComponent(uiComponent, modifier, onClick)
        true
    }

    "BottomBar" -> {
        BottomBarComponent(uiComponent, modifier, onClick)
        true
    }

    "SnackBar" -> {
        SnackBarComponent(uiComponent, modifier, onClick)
        true
    }

    "FloatingActionButton" -> {
        FloatingActionButtonComponent(uiComponent, modifier, onClick)
        true
    }

    else -> false
}

@Composable
private fun dispatchListComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) = when (uiComponent.componentName) {
    "LazyColumn" -> {
        LazyColumnComponent(uiComponent, modifier, onClick, componentList)
        true
    }

    "LazyRow" -> {
        LazyRowComponent(uiComponent, modifier, onClick, componentList)
        true
    }

    else -> false
}

@Composable
private fun dispatchContainerComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) = when (uiComponent.componentName) {
    "Column" -> {
        ColumnComponent(uiComponent, modifier, onClick, componentList)
        true
    }

    "Row" -> {
        RowComponent(uiComponent, modifier, onClick, componentList)
        true
    }

    "Box" -> {
        BoxComponent(uiComponent, modifier, onClick, componentList)
        true
    }

    else -> false
}

@Composable
private fun dispatchElementComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
) = when (uiComponent.componentName) {
    "Spacer" -> {
        SpacerComponent(uiComponent, modifier)
        true
    }

    "Text" -> {
        TextComponent(uiComponent, modifier, onClick)
        true
    }

    "Image" -> {
        ImageComponent(uiComponent, modifier, onClick)
        true
    }

    "Button" -> {
        ButtonComponent(uiComponent, modifier, onClick)
        true
    }

    "IconButton" -> {
        IconButtonComponent(uiComponent, modifier, onClick)
        true
    }

    else -> false
}
