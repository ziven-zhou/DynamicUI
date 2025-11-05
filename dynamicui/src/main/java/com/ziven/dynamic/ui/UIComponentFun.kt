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
) = findComponentWithId(componentId)?.updateComponentValue(componentValue)

fun UIComponent.updateComponentValue(componentValue: ComponentValue) {
    val updateValue = this.toValue()
    componentValue.text?.let { updateValue.text = it }
    componentValue.image?.let { updateValue.image = it }
    componentValue.click?.let {
        val copy = mutableListOf<ComponentClick>()
        it.forEach { click -> copy.add(click.copy()) }
        updateValue.click = copy
    }
    componentValue.extras?.let { this.toExtras().putAll(it) }
    componentValue.clickable?.let { updateValue.clickable = it }
}

fun UIComponent.addExtras(
    key: String,
    value: String,
) = apply { this.toExtras()[key] = value }

fun UIComponent.addExtras(vararg extras: Pair<String, String>) = apply { addExtras(extras.toMap()) }

fun UIComponent.addExtras(extras: Map<String, String>) = apply { this.toExtras().putAll(extras) }

private fun UIComponent.toValue(): ComponentValue {
    var updateValue = value
    if (updateValue == null) {
        updateValue = ComponentValue()
        value = updateValue
    }
    return updateValue
}

private fun UIComponent.toExtras(): MutableMap<String, String> {
    val value = toValue()
    var extras = value.extras
    if (extras == null) {
        extras = mutableMapOf()
        value.extras = extras
    }
    return extras
}
