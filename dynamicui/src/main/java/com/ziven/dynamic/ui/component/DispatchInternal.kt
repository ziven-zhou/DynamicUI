package com.ziven.dynamic.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.UIManager
import com.ziven.dynamic.ui.logPrint

@Composable
internal fun DispatchRenderComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    logPrint("RenderComponent: $uiComponent")
    if (uiComponent.componentName.isNullOrEmpty()) return
    if (dispatchScaffoldComponent(uiComponent, modifier, onClick, componentList, componentState)) return
    if (dispatchListComponent(uiComponent, modifier, onClick, componentList, componentState)) return
    if (dispatchContainerComponent(uiComponent, modifier, onClick, componentList, componentState)) return
    if (dispatchElementComponent(uiComponent, modifier, onClick, componentList, componentState)) return
    UIManager.getComponent(uiComponent.componentType)?.let {
        DispatchRenderComponent(it, modifier, onClick, componentList, componentState)
    }
}

@Composable
private fun dispatchScaffoldComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) = when (uiComponent.componentName) {
    "Scaffold" -> {
        ScaffoldComponent(uiComponent, modifier, onClick, componentList, componentState)
        true
    }

    "TopBar" -> {
        TopBarComponent(uiComponent, modifier, onClick, componentList, componentState)
        true
    }

    "BottomBar" -> {
        BottomBarComponent(uiComponent, modifier, onClick, componentList, componentState)
        true
    }

    "SnackBar" -> {
        SnackBarComponent(uiComponent, modifier, onClick, componentList, componentState)
        true
    }

    "FloatingActionButton" -> {
        FloatingActionButtonComponent(uiComponent, modifier, onClick, componentList, componentState)
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
    componentState: ComponentState? = null,
) = when (uiComponent.componentName) {
    "LazyColumn" -> {
        LazyColumnComponent(uiComponent, modifier, onClick, componentList, componentState)
        true
    }

    "LazyRow" -> {
        LazyRowComponent(uiComponent, modifier, onClick, componentList, componentState)
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
    componentState: ComponentState? = null,
) = when (uiComponent.componentName) {
    "Column" -> {
        ColumnComponent(uiComponent, modifier, onClick, componentList, componentState)
        true
    }

    "Row" -> {
        RowComponent(uiComponent, modifier, onClick, componentList, componentState)
        true
    }

    "Box" -> {
        BoxComponent(uiComponent, modifier, onClick, componentList, componentState)
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
    componentState: ComponentState? = null,
) = when (uiComponent.componentName) {
    "Spacer" -> {
        SpacerComponent(uiComponent, modifier, componentList, componentState)
        true
    }

    "Text" -> {
        TextComponent(uiComponent, modifier, onClick, componentList, componentState)
        true
    }

    "Image" -> {
        ImageComponent(uiComponent, modifier, onClick, componentList, componentState)
        true
    }

    "Button" -> {
        ButtonComponent(uiComponent, modifier, onClick, componentList, componentState)
        true
    }

    "IconButton" -> {
        IconButtonComponent(uiComponent, modifier, onClick, componentList, componentState)
        true
    }

    else -> false
}
