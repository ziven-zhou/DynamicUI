package com.ziven.dynamic.ui.componentIn

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.UIManager
import com.ziven.dynamic.ui.forEachComponent
import com.ziven.dynamic.ui.componentTo.componentClick
import com.ziven.dynamic.ui.componentTo.componentUI
import com.ziven.dynamic.ui.updateComponentValue

@Composable
internal fun LazyColumnComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    LazyColumn(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onClick, componentList, componentState),
    ) {
        componentList?.let {
            items(
                count = it.componentSize().intValue,
                key = it.componentKey,
                contentType = { index -> it.componentType(index) },
            ) { index ->
                ItemsComponent(index, onClick, componentList, componentState)
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
    componentState: ComponentState? = null,
) {
    LazyRow(
        modifier =
            modifier
                .componentUI(uiComponent)
                .componentClick(uiComponent, onClick, componentList, componentState),
    ) {
        componentList?.let {
            items(
                count = it.componentSize().intValue,
                key = it.componentKey,
                contentType = { index -> it.componentType(index) },
            ) { index ->
                ItemsComponent(index, onClick, componentList, componentState)
            }
        }
    }
}

@Composable
private fun ItemsComponent(
    index: Int,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList,
    componentState: ComponentState? = null,
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
    DispatchRenderComponent(parent, Modifier, onClick, componentList, componentState)
}
