package com.ziven.dynamic.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.componentIn.DispatchRenderComponent

@Composable
fun RenderComponent(
    componentType: String?,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
    onClick: (ComponentAction) -> Unit = {},
) = UIManager.RunComponent(componentType) {
    RenderComponent(it, componentList, componentState, onClick)
}

@Composable
fun RenderComponent(
    uiComponent: UIComponent,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
    onClick: (ComponentAction) -> Unit = {},
) = DispatchRenderComponent(uiComponent, Modifier, onClick, componentList, componentState)
