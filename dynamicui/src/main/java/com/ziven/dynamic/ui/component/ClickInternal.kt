package com.ziven.dynamic.ui.component

import androidx.compose.material3.SnackbarDuration
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentClick
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.internal.VModel
import com.ziven.dynamic.ui.internal.launch
import com.ziven.dynamic.ui.logPrint
import kotlinx.coroutines.Dispatchers

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
            onClick(toComponentAction(uiComponent))
        }
    }

private fun toComponentAction(uiComponent: UIComponent) =
    ComponentAction(
        componentId = uiComponent.componentId,
        value = uiComponent.value?.copy(),
    )

private fun dispatchClick(
    uiComponent: UIComponent,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Boolean {
    val clicks = uiComponent.value?.click ?: return false
    logPrint("dispatchClick: $clicks")
    if (clicks.isEmpty()) return false
    for (click in clicks) {
        if (click.tryFirst == true) {
            when (click.type) {
                "Activity" ->
                    if (toActivity(click, componentList, componentState)) return true

                "Compose" ->
                    if (toCompose(click, componentList, componentState)) return true

                "SnackBar" ->
                    if (toSnackBar(click, componentList, componentState)) return true

                "Dialog" ->
                    if (toDialog(click, componentList, componentState)) return true
            }
        }
    }
    return false
}

private fun toActivity(
    componentClick: ComponentClick,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Boolean = false

private fun toCompose(
    componentClick: ComponentClick,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Boolean = false

private fun toSnackBar(
    componentClick: ComponentClick,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Boolean {
    val snackBarHostState = componentState?.snackBarHostState ?: return false
    val message = componentClick.content ?: return false
    val duration = componentClick.duration
    val actionLabel = componentClick.actionLabel
    val withDismissAction = componentClick.withDismissAction ?: false
    VModel.launch(Dispatchers.Main) {
        snackBarHostState.showSnackbar(
            message,
            actionLabel,
            withDismissAction,
            duration(actionLabel, duration),
        )
    }
    return true
}

private fun duration(
    actionLabel: String?,
    duration: String?,
): SnackbarDuration =
    when (duration) {
        "Long" -> SnackbarDuration.Long
        "Short" -> SnackbarDuration.Short
        "Indefinite" -> SnackbarDuration.Indefinite
        else -> if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite
    }

private fun toDialog(
    componentClick: ComponentClick,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Boolean = false
