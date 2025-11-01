package com.ziven.dynamic.ui.componentIn

import android.content.Context
import android.content.Intent
import androidx.compose.material3.SnackbarDuration
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentClick
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.internal.VModel
import com.ziven.dynamic.ui.internal.launch
import com.ziven.dynamic.ui.internal.logPrint
import com.ziven.dynamic.ui.internal.runSafe
import kotlinx.coroutines.Dispatchers
import androidx.core.net.toUri
import com.ziven.dynamic.ui.UIManager

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
            }
        }
    }
    return false
}

private fun toActivity(
    componentClick: ComponentClick,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): Boolean {
    val context = UIManager.getContext()
    if (context == null) {
        throw RuntimeException("Place, UIManager.setContext() first.")
    }
    if (toDeepLink(context, componentClick.packageName, componentClick.deepLink)) return true
    if (toActivity(context, componentClick.packageName, componentClick.className)) return true
    return false
}

private fun toActivity(
    context: Context,
    packageName: String? = null,
    className: String? = null,
): Boolean {
    if (packageName.isNullOrEmpty()) return false
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.`package` = packageName
    if (!className.isNullOrEmpty()) {
        intent.setClassName(packageName, className)
    }
    try {
        context.startActivity(intent)
        return true
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

private fun toDeepLink(
    context: Context,
    packageName: String? = null,
    deepLink: String? = null,
): Boolean {
    if (deepLink.isNullOrEmpty()) return false
    val intent = Intent(Intent.ACTION_VIEW)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.`package` = packageName
    intent.data = runSafe { deepLink.toUri() }
    try {
        context.startActivity(intent)
        return true
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

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
