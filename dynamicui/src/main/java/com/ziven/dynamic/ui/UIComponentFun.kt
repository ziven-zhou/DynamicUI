package com.ziven.dynamic.ui

import androidx.compose.runtime.Composable

@Composable
fun UIComponent.ForEachChildComponent(block: @Composable (UIComponent) -> Unit) {
    children?.forEach { block.invoke(it) }
}

fun UIComponent.findChildComponentWithName(componentName: String): UIComponent? {
    if (this.children == null) return null
    for (child in this.children) {
        if (child.componentName == componentName) return child
    }
    return null
}

fun UIComponent.forEachChildComponent(block: (UIComponent) -> Unit) {
    children?.forEach { block.invoke(it) }
}

fun UIComponent.forEachComponent(block: (UIComponent) -> Unit) {
    block(this)
    children?.forEach { it.forEachComponent(block) }
}

fun UIComponent.findComponentWithId(componentId: String): UIComponent? {
    if (this.componentId == componentId) return this
    if (this.children == null) return null
    for (child in this.children) {
        val component = child.findComponentWithId(componentId)
        if (component != null) return component
    }
    return null
}

fun UIComponent.updateComponentValueWithId(values: Map<String, ComponentValue>) =
    values.forEach { (componentId, componentValue) ->
        updateComponentValueWithId(componentId, componentValue)
    }

fun UIComponent.updateComponentValueWithId(vararg values: Pair<String, ComponentValue>) =
    values.forEach { updateComponentValueWithId(it.first, it.second) }

fun UIComponent.updateComponentValueWithId(
    componentId: String,
    componentValue: ComponentValue,
) = apply { findComponentWithId(componentId)?.updateComponentValue(componentValue) }

fun UIComponent.updateComponentValue(componentValue: ComponentValue) = apply { this.value = componentValue }

fun UIComponent.addExtras(
    key: String,
    value: String,
) = apply { this.toExtras()[key] = value }

fun UIComponent.addExtras(vararg extras: Pair<String, String>) = apply { this.addExtras(extras.toMap()) }

fun UIComponent.addExtras(extras: Map<String, String>) = apply { this.toExtras().putAll(extras) }

private fun UIComponent.toValue(): ComponentValue {
    var updateValue = this.value
    if (updateValue == null) {
        updateValue = ComponentValue()
        this.value = updateValue
    }
    return updateValue
}

private fun UIComponent.toExtras(): MutableMap<String, String> {
    val updateValue = this.toValue()
    var extras = updateValue.extras
    if (extras == null) {
        extras = mutableMapOf()
        updateValue.extras = extras
    }
    return extras
}

fun ofComponentValue(
    text: String? = null,
    image: String? = null,
    clickable: Boolean? = false,
    click: List<ComponentClick>? = null,
    extras: MutableMap<String, String>? = null,
): ComponentValue =
    ComponentValue(
        text = text,
        image = image,
        clickable = clickable,
        click = click,
        extras = extras,
    )

fun ofTextValue(text: String? = null): ComponentValue = ofComponentValue(text = text)

fun ofImageValue(image: String? = null): ComponentValue = ofComponentValue(image = image)

fun ofClickValue(vararg click: ComponentClick): ComponentValue = ofClickValue(click = click.toList())

fun ofClickValue(click: List<ComponentClick>? = null): ComponentValue = ofComponentValue(clickable = true, click = click)

fun ComponentValue.addText(text: String? = null): ComponentValue = apply { this.text = text }

fun ComponentValue.addImage(image: String? = null): ComponentValue = apply { this.image = image }

fun ofComponentClick(
    content: String? = null,
    action: String? = null,
    tryFirst: Boolean? = true,
    returnAny: Boolean? = false,
    deepLink: String? = null,
    packageName: String? = null,
    className: String? = null,
    actionLabel: String? = null,
    withDismissAction: Boolean? = null,
    duration: String? = null,
    routeName: String? = null,
    routeParams: MutableList<String>? = null,
    activityParams: MutableMap<String, String>? = null,
    backType: String? = null,
): ComponentClick =
    ComponentClick(
        content = content,
        action = action,
        tryFirst = tryFirst,
        returnAny = returnAny,
        deepLink = deepLink,
        packageName = packageName,
        className = className,
        actionLabel = actionLabel,
        withDismissAction = withDismissAction,
        duration = duration,
        routeName = routeName,
        routeParams = routeParams,
        activityParams = activityParams,
        backType = backType,
    )

fun ofActivityClick(
    packageName: String?,
    className: String? = null,
    activityParams: MutableMap<String, String>? = null,
): ComponentClick =
    ofComponentClick(
        action = "Activity",
        packageName = packageName,
        className = className,
        activityParams = activityParams,
    )

fun ofDeepLinkClick(
    deepLink: String?,
    packageName: String? = null,
    activityParams: MutableMap<String, String>? = null,
): ComponentClick =
    ofComponentClick(
        action = "DeepLink",
        packageName = packageName,
        deepLink = deepLink,
        activityParams = activityParams,
    )

fun ofComposeClick(
    routeName: String?,
    routeParams: MutableList<String>? = null,
): ComponentClick =
    ofComponentClick(
        action = "Compose",
        routeName = routeName,
        routeParams = routeParams,
    )

fun ofSnackBarClick(
    content: String?,
    actionLabel: String? = null,
    withDismissAction: Boolean? = null,
    duration: String? = null,
): ComponentClick =
    ofComponentClick(
        content = content,
        action = "SnackBar",
        actionLabel = actionLabel,
        withDismissAction = withDismissAction,
        duration = duration,
    )

fun ofToastClick(
    content: String?,
    duration: String? = null,
): ComponentClick =
    ofComponentClick(
        action = "Toast",
        content = content,
        duration = duration,
    )

fun ofBackClick(backType: String?): ComponentClick =
    ofComponentClick(
        action = "Back",
        backType = backType,
    )

fun ComponentClick.toList(): List<ComponentClick> = listOf(this)

fun ComponentClick.toComponentValue(): ComponentValue = ofClickValue(this)
