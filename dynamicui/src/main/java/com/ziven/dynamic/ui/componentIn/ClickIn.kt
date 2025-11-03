package com.ziven.dynamic.ui.componentIn

import android.content.Intent
import androidx.compose.material3.SnackbarDuration
import androidx.core.net.toUri
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentClick
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.UIManager
import com.ziven.dynamic.ui.internal.VModel
import com.ziven.dynamic.ui.internal.launch
import com.ziven.dynamic.ui.internal.logPrint
import com.ziven.dynamic.ui.internal.runSafe
import kotlinx.coroutines.Dispatchers

fun click(
    uiComponent: UIComponent,
    onClick: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): () -> Unit {
    val componentId = uiComponent.componentId
    val componentValue = uiComponent.value?.copy()
    val componentAction = ComponentAction(componentId, componentValue)
    return {
        if (dispatchClick(componentAction, componentState)) {
            onClick(componentAction)
        }
    }
}

private fun dispatchClick(
    componentAction: ComponentAction,
    componentState: ComponentState? = null,
): Boolean {
    val componentClicks = componentAction.value?.click ?: return true
    if (componentClicks.isEmpty()) return true
    for (componentClick in componentClicks) {
        logPrint("ComponentClick: $componentClick")
        if (componentClick.tryFirst == true && dispatchClick(componentClick, componentState)) {
            return componentClick.returnAny == true
        }
    }
    return true
}

private fun dispatchClick(
    componentClick: ComponentClick,
    componentState: ComponentState? = null,
): Boolean {
    when (componentClick.action) {
        "Activity" ->
            if (toActivity(componentClick)) return true

        "Compose" ->
            if (toCompose(componentClick, componentState)) return true

        "SnackBar" ->
            if (toSnackBar(componentClick, componentState)) return true

        "Dialog" -> false
        "Toast" -> false
        "Back" -> false
    }
    return false
}

private fun toActivity(componentClick: ComponentClick): Boolean {
    val packageName = componentClick.packageName
    val deepLink = componentClick.deepLink
    val className = componentClick.className
    if (toDeepLink(packageName, deepLink)) return true
    if (toActivity(packageName, className)) return true
    return false
}

private fun toActivity(
    packageName: String? = null,
    className: String? = null,
): Boolean {
    if (packageName.isNullOrEmpty()) return false
    val intent = newIntent(Intent.ACTION_MAIN, packageName)
    className?.let { intent.setClassName(packageName, className) }
    return startActivity(intent)
}

private fun toDeepLink(
    packageName: String? = null,
    deepLink: String? = null,
): Boolean {
    if (deepLink.isNullOrEmpty()) return false
    val intent = newIntent(Intent.ACTION_VIEW, packageName)
    intent.data = runSafe { deepLink.toUri() } ?: return false
    return startActivity(intent)
}

private fun newIntent(
    action: String,
    packageName: String? = null,
) = Intent(action).apply {
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    `package` = packageName
}

private fun startActivity(intent: Intent): Boolean {
    val context = UIManager.getContext()
    if (context == null) {
        throw RuntimeException("Place, UIManager.setContext() first.")
    }
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
    componentState: ComponentState? = null,
): Boolean = false

private fun toSnackBar(
    componentClick: ComponentClick,
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
