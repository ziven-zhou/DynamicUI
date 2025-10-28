package com.ziven.dynamic.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ForEachChildComponent
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.internal.componentClick
import com.ziven.dynamic.ui.internal.componentUI
import com.ziven.dynamic.ui.internal.toAlign
import com.ziven.dynamic.ui.internal.toHorizontalAlign
import com.ziven.dynamic.ui.internal.toVerticalAlign

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
        uiComponent.ForEachChildComponent { child ->
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
        uiComponent.ForEachChildComponent { child ->
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
        uiComponent.ForEachChildComponent { child ->
            DispatchRenderComponent(
                child,
                Modifier.align(child.style.toAlign()),
                onClick,
                componentList,
            )
        }
    }
}
