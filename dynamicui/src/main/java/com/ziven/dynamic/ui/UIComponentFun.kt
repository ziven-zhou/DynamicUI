package com.ziven.dynamic.ui

import androidx.compose.runtime.Composable

fun UIComponent.forEachComponent(block: (UIComponent) -> Unit) {
    block(this)
    children?.forEach { it.forEachComponent(block) }
}

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
    var updateValue = this.value
    if (updateValue == null) {
        updateValue = ComponentValue()
        this.value = componentValue
    }
    componentValue.text?.let { updateValue.text = it }
    componentValue.image?.let { updateValue.image = it }
    componentValue.click?.let {
        val copy = mutableListOf<ComponentClick>()
        it.forEach { click -> copy.add(click.copy()) }
        updateValue.click = copy
    }
    componentValue.extras?.let { updateValue.extras = it.toMutableMap() }
    componentValue.clickable?.let { updateValue.clickable = it }
}
