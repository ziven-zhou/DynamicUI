package com.ziven.dynamic.ui.componentIn

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.UIManager
import com.ziven.dynamic.ui.internal.logPrint

@Composable
internal fun DispatchRenderComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    logPrint("RenderComponent: $uiComponent")
    if (uiComponent.componentName.isNullOrEmpty()) return
    uiComponent.componentId?.let {
        componentState?.updateValue?.invoke("$it/", uiComponent, emptyMap())
    }
    if (dispatchScaffoldComponent(
            uiComponent,
            modifier,
            onAction,
            componentList,
            componentState,
        )
    ) {
        return
    }
    if (dispatchListComponent(
            uiComponent,
            modifier,
            onAction,
            componentList,
            componentState,
        )
    ) {
        return
    }
    if (dispatchContainerComponent(
            uiComponent,
            modifier,
            onAction,
            componentList,
            componentState,
        )
    ) {
        return
    }
    if (dispatchElementComponent(
            uiComponent,
            modifier,
            onAction,
            componentList,
            componentState,
        )
    ) {
        return
    }
    UIManager.getComponent(uiComponent.componentType)?.let {
        DispatchRenderComponent(it, modifier, onAction, componentList, componentState)
    }
}

@Composable
private fun dispatchScaffoldComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) = when (uiComponent.componentName) {
    "Scaffold" -> {
        ScaffoldComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "TopBar" -> {
        TopBarComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "BottomBar" -> {
        BottomBarComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "SnackBar" -> {
        SnackBarComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "FloatingActionButton" -> {
        FloatingActionButtonComponent(
            uiComponent,
            modifier,
            onAction,
            componentList,
            componentState,
        )
        true
    }

    else -> false
}

@Composable
private fun dispatchListComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) = when (uiComponent.componentName) {
    "LazyColumn" -> {
        LazyColumnComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "LazyRow" -> {
        LazyRowComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    else -> false
}

@Composable
private fun dispatchContainerComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) = when (uiComponent.componentName) {
    "Column" -> {
        ColumnComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "Row" -> {
        RowComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "Box" -> {
        BoxComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    else -> false
}

@Composable
private fun dispatchElementComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) = when (uiComponent.componentName) {
    "Spacer" -> {
        SpacerComponent(uiComponent, modifier, componentList, componentState)
        true
    }

    "Text" -> {
        TextComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "Image" -> {
        ImageComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "Button" -> {
        ButtonComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "IconButton" -> {
        IconButtonComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "Icon" -> {
        IconComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    else -> false
}

@Composable
private fun dispatchSelectionComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) = when (uiComponent.componentName) {
    "Switch" -> {
        SwitchComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "RadioButton" -> {
        RadioButtonComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    "CheckBox" -> {
        CheckBoxComponent(uiComponent, modifier, onAction, componentList, componentState)
        true
    }

    else -> false
}
