package com.ziven.dynamic.ui.componentIn

import android.content.Intent
import android.widget.Toast
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
import java.net.URLEncoder

internal fun click(
    uiComponent: UIComponent,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
): () -> Unit {
    val componentId = uiComponent.componentId
    val componentValue = uiComponent.value?.copy()
    val componentAction = ComponentAction("Click", componentId, componentValue)
    return {
        if (dispatchClick(componentAction, componentState)) {
            onAction(componentAction)
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
    logPrint("ComponentClick: ${componentClick.action}")
    when (componentClick.action) {
        "Activity" -> if (toActivity(componentClick)) return true

        "DeepLink" -> if (toDeepLink(componentClick)) return true

        "Compose" -> if (toCompose(componentClick, componentState)) return true

        "SnackBar" -> if (toSnackBar(componentClick, componentState)) return true

        "Dialog" -> if (toDialog(componentClick)) return true

        "Toast" -> if (toToast(componentClick)) return true

        "Back" -> if (toBack(componentClick, componentState)) return true
    }
    return false
}

private fun toActivity(componentClick: ComponentClick): Boolean {
    val packageName = componentClick.packageName
    if (packageName.isNullOrEmpty()) return false
    val className = componentClick.className
    val activityParams = componentClick.activityParams
    val intent = newIntent(Intent.ACTION_MAIN, packageName, activityParams)
    className?.let { intent.setClassName(packageName, className) }
    return startActivity(intent)
}

private fun toDeepLink(componentClick: ComponentClick): Boolean {
    val deepLink = componentClick.deepLink
    if (deepLink.isNullOrEmpty()) return false
    val packageName = componentClick.packageName
    val activityParams = componentClick.activityParams
    val intent = newIntent(Intent.ACTION_VIEW, packageName, activityParams)
    intent.data = runSafe { deepLink.toUri() } ?: return false
    return startActivity(intent)
}

private fun newIntent(
    action: String,
    packageName: String? = null,
    activityParams: MutableMap<String, String>? = null,
) = Intent(action).apply {
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    `package` = packageName
    activityParams?.forEach { (key, value) ->
        putExtra(key, value)
    }
    logPrint("activityParams: $activityParams")
}

private fun startActivity(intent: Intent): Boolean {
    try {
        UIManager.getContext().startActivity(intent)
        return true
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

private fun toCompose(
    componentClick: ComponentClick,
    componentState: ComponentState? = null,
): Boolean {
    val control = componentState?.navHostController ?: return false
    var route = componentClick.routeName ?: return false
    componentClick.routeParams?.forEach {
        route += "/${URLEncoder.encode(it, "UTF-8")}"
    }
    logPrint("ComponentClick toCompose: $route")
    control.navigate(route)
    return true
}

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
            snackBarDuration(actionLabel, duration),
        )
    }
    return true
}

private fun snackBarDuration(
    actionLabel: String?,
    duration: String?,
): SnackbarDuration =
    when (duration) {
        "Long" -> SnackbarDuration.Long
        "Short" -> SnackbarDuration.Short
        "Indefinite" -> SnackbarDuration.Indefinite
        else -> if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite
    }

private fun toDialog(componentClick: ComponentClick): Boolean = false

private fun toToast(componentClick: ComponentClick): Boolean {
    val message = componentClick.content ?: return false
    val context = UIManager.getActivity() ?: UIManager.getContext()
    Toast.makeText(context, message, toastDuration(componentClick.duration)).show()
    return true
}

private fun toastDuration(duration: String?): Int =
    when (duration) {
        "Long" -> Toast.LENGTH_LONG
        else -> Toast.LENGTH_SHORT
    }

private fun toBack(
    componentClick: ComponentClick,
    componentState: ComponentState? = null,
): Boolean {
    logPrint("ComponentClick toBack: ${componentClick.backType}")
    when (componentClick.backType) {
        "Activity" -> {
            val activity = UIManager.getActivity() ?: return false
            activity.finish()
            return true
        }

        "Compose" -> {
            val controller = componentState?.navHostController ?: return false
            controller.popBackStack()
            return true
        }

        else -> return false
    }
}
