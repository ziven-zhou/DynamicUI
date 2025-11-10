package com.ziven.dynamic.ui.componentIn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.ForEachChildComponent
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.componentTo.componentClick
import com.ziven.dynamic.ui.componentTo.componentUI
import com.ziven.dynamic.ui.componentTo.toAlign
import com.ziven.dynamic.ui.componentTo.toHorizontalAlign
import com.ziven.dynamic.ui.componentTo.toHorizontalAlign2
import com.ziven.dynamic.ui.componentTo.toVerticalAlign
import com.ziven.dynamic.ui.componentTo.toVerticalAlign2

@Composable
internal fun ColumnComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    Column(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onAction, componentList, componentState),
        verticalArrangement = uiComponent.style.toVerticalAlign2(),
        horizontalAlignment = uiComponent.style.toHorizontalAlign(),
    ) {
        uiComponent.ForEachChildComponent { child ->
            DispatchRenderComponent(
                child,
                Modifier.align(child.style.toHorizontalAlign()),
                onAction,
                componentList,
                componentState,
            )
        }
    }
}

@Composable
internal fun RowComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    Row(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onAction, componentList, componentState),
        horizontalArrangement = uiComponent.style.toHorizontalAlign2(),
        verticalAlignment = uiComponent.style.toVerticalAlign(),
    ) {
        uiComponent.ForEachChildComponent { child ->
            DispatchRenderComponent(
                child,
                Modifier.align(child.style.toVerticalAlign()),
                onAction,
                componentList,
                componentState,
            )
        }
    }
}

@Composable
internal fun BoxComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    Box(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onAction, componentList, componentState),
        contentAlignment = uiComponent.style.toAlign(),
    ) {
        uiComponent.ForEachChildComponent { child ->
            DispatchRenderComponent(
                child,
                Modifier.align(child.style.toAlign()),
                onAction,
                componentList,
                componentState,
            )
        }
    }
}
