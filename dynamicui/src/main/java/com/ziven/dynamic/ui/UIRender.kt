package com.ziven.dynamic.ui

import androidx.compose.runtime.Composable
import com.ziven.dynamic.ui.componentIn.RouteComponent

@Composable
fun RenderComponent(
    componentType: String?,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
    onAction: (ComponentAction) -> Unit = {},
) = UIManager.RunComponent(componentType) {
    RouteComponent(it, componentList, componentState, onAction)
}

@Composable
fun RenderComponent(
    uiComponent: UIComponent,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
    onAction: (ComponentAction) -> Unit = {},
) = UIManager.AddComponent(uiComponent) {
    RouteComponent(it, componentList, componentState, onAction)
}
