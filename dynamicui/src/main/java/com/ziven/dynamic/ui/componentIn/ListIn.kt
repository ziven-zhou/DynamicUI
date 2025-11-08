package com.ziven.dynamic.ui.componentIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.UIManager
import com.ziven.dynamic.ui.componentTo.componentClick
import com.ziven.dynamic.ui.componentTo.componentUI
import com.ziven.dynamic.ui.componentTo.toContentPadding
import com.ziven.dynamic.ui.componentTo.toHorizontalAlign
import com.ziven.dynamic.ui.componentTo.toHorizontalAlign2
import com.ziven.dynamic.ui.componentTo.toPaddingValues
import com.ziven.dynamic.ui.componentTo.toReverseLayout
import com.ziven.dynamic.ui.componentTo.toVerticalAlign
import com.ziven.dynamic.ui.componentTo.toVerticalAlign2
import com.ziven.dynamic.ui.forEachComponent
import com.ziven.dynamic.ui.updateComponentValue

private fun makeWhich(
    uiComponent: UIComponent,
    componentState: ComponentState? = null,
): String {
    val route = componentState?.navHostController?.currentDestination?.route
    if (route.isNullOrEmpty()) {
        return uiComponent.listName ?: "/"
    }
    return uiComponent.listName?.let { "$it/$route" } ?: "/"
}

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
        contentPadding = uiComponent.layout.toContentPadding().toPaddingValues(),
        reverseLayout = uiComponent.layout.toReverseLayout(),
        horizontalAlignment = uiComponent.style.toHorizontalAlign(),
        verticalArrangement =
            uiComponent.style.toVerticalAlign2(
                if (!uiComponent.layout.toReverseLayout()) Arrangement.Top else Arrangement.Bottom,
            ),
    ) {
        val which = makeWhich(uiComponent, componentState)
        componentList?.let {
            items(
                count = it.componentSize(which).intValue,
                key =
                    if (it.componentKey != null) {
                        { index -> it.componentKey.invoke(which, index) }
                    } else {
                        null
                    },
                contentType = { index -> it.componentType(which, index) },
            ) { index ->
                ItemsComponent(which, index, onClick, componentList, componentState)
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
        contentPadding = uiComponent.layout.toContentPadding().toPaddingValues(),
        reverseLayout = uiComponent.layout.toReverseLayout(),
        verticalAlignment = uiComponent.style.toVerticalAlign(),
        horizontalArrangement =
            uiComponent.style.toHorizontalAlign2(
                if (!uiComponent.layout.toReverseLayout()) Arrangement.Start else Arrangement.End,
            ),
    ) {
        val which = makeWhich(uiComponent, componentState)
        componentList?.let {
            items(
                count = it.componentSize(which).intValue,
                key =
                    if (it.componentKey != null) {
                        { index -> it.componentKey.invoke(which, index) }
                    } else {
                        null
                    },
                contentType = { index -> it.componentType(which, index) },
            ) { index ->
                ItemsComponent(which, index, onClick, componentList, componentState)
            }
        }
    }
}

@Composable
private fun ItemsComponent(
    which: String,
    index: Int,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList,
    componentState: ComponentState? = null,
) {
    val type = componentList.componentType(which, index) ?: return
    val parent = UIManager.getComponent(type) ?: return
    parent.forEachComponent { child ->
        child.componentId?.let { childId ->
            componentList.componentData(which, index, childId)?.let {
                child.updateComponentValue(it)
            }
        }
    }
    DispatchRenderComponent(parent, Modifier, onClick, componentList, componentState)
}
