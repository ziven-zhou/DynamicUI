package com.ziven.dynamic.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.UIManager
import com.ziven.dynamic.ui.forEachComponent
import com.ziven.dynamic.ui.internal.componentClick
import com.ziven.dynamic.ui.internal.componentUI
import com.ziven.dynamic.ui.updateComponentValue

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
