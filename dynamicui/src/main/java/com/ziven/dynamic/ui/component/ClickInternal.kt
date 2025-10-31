package com.ziven.dynamic.ui.component

import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.toComponentAction

fun click(
    uiComponent: UIComponent,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): () -> Unit =
    {
        if (!dispatchClick(
                uiComponent,
                componentList,
                componentState,
            )
        ) {
            onClick(uiComponent.toComponentAction())
        }
    }

private fun dispatchClick(
    uiComponent: UIComponent,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Boolean {
    val clicks = uiComponent.value?.click ?: return false
    if (clicks.isEmpty()) return false
    for (click in clicks) {
        if (click.tryFirst == true) {
            when (click.type) {
                "Activity" ->
                    if (toActivity(uiComponent, componentList, componentState)) return true

                "Compose" ->
                    if (toCompose(uiComponent, componentList, componentState)) return true

                "SnackBar" ->
                    if (toSnackBar(uiComponent, componentList, componentState)) return true

                "Dialog" ->
                    if (toDialog(uiComponent, componentList, componentState)) return true
            }
        }
    }
    return false
}

private fun toActivity(
    uiComponent: UIComponent,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Boolean = false

private fun toCompose(
    uiComponent: UIComponent,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Boolean = false

private fun toSnackBar(
    uiComponent: UIComponent,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Boolean = false

private fun toDialog(
    uiComponent: UIComponent,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Boolean = false
