package com.ziven.dynamic.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.component.DispatchRenderComponent

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

