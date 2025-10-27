package com.ziven.dynamic.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UIComponent(
    @SerialName("componentType")
    val componentType: String? = null,
    @SerialName("componentId")
    val componentId: String? = null,
    @SerialName("componentName")
    val componentName: String,
    val layout: ComponentLayout? = null,
    @SerialName("style")
    val style: ComponentStyle? = null,
    @SerialName("value")
    var value: ComponentValue? = null,
    @SerialName("children")
    val children: List<UIComponent>? = null,
)

@Serializable
data class ComponentValue(
    @SerialName("text")
    var text: String? = null,
    @SerialName("image")
    var image: String? = null,
    @SerialName("url")
    var url: List<String>? = null,
    @SerialName("clickable")
    var clickable: Boolean? = null,
    @SerialName("extras")
    var extras: MutableMap<String, String>? = null,
)

@Serializable
data class ComponentLayout(
    @SerialName("width")
    val width: Int? = null,
    @SerialName("height")
    val height: Int? = null,
    @SerialName("start")
    val start: Float? = null,
    @SerialName("end")
    val end: Float? = null,
    @SerialName("top")
    val top: Float? = null,
    @SerialName("bottom")
    val bottom: Float? = null,
)

@Serializable
data class ComponentStyle(
    @SerialName("backgroundColor")
    val backgroundColor: String? = null,
    @SerialName("topLeft")
    val topLeft: Float? = null,
    @SerialName("topRight")
    val topRight: Float? = null,
    @SerialName("bottomLeft")
    val bottomLeft: Float? = null,
    @SerialName("bottomRight")
    val bottomRight: Float? = null,
    @SerialName("fontSize")
    val fontSize: Float? = null,
    @SerialName("fontColor")
    val fontColor: String? = null,
    @SerialName("fontStyle")
    val fontStyle: String? = null,
    @SerialName("fontWeight")
    val fontWeight: String? = null,
    @SerialName("fontFamily")
    val fontFamily: String? = null,
    @SerialName("overflow")
    val overflow: String? = null,
    @SerialName("maxLines")
    val maxLines: Int? = null,
    @SerialName("minLines")
    val minLines: Int? = null,
    @SerialName("align")
    val align: String? = null,
    @SerialName("scale")
    val scale: String? = null,
    @SerialName("quality")
    val quality: String? = null,
    @SerialName("fabPosition")
    val fabPosition: String? = null,
)

data class ComponentAction(
    val componentId: String? = null,
    val value: ComponentValue? = null,
)

class ComponentList(
    val componentSize: () -> MutableIntState,
    val componentKey: ((index: Int) -> Any)? = null,
    val componentType: (index: Int) -> String?,
    val componentData: (index: Int, componentId: String) -> ComponentValue?,
)

fun UIComponent.forEachComponent(block: (UIComponent) -> Unit) {
    block(this)
    children?.forEach { it.forEachComponent(block) }
}

@Composable
fun UIComponent.ForEachComponent(block: @Composable (UIComponent) -> Unit) {
    children?.forEach { block.invoke(it) }
}

fun UIComponent.findComponentWithName(componentName: String): UIComponent? {
    if (children != null) {
        for (child in children) {
            if (child.componentName == componentName) {
                return child
            }
        }
    }
    return null
}

fun UIComponent.findComponentWithId(componentId: String): UIComponent? {
    if (this.componentId == componentId) return this
    if (children != null) {
        for (child in children) {
            val component = child.findComponentWithId(componentId)
            if (component != null) {
                return component
            }
        }
    }
    return null
}

fun UIComponent.updateComponentValueWithId(values: Map<String, ComponentValue>) =
    values.forEach { (componentId, componentValue) ->
        updateComponentValueWithId(
            componentId,
            componentValue,
        )
    }

fun UIComponent.updateComponentValueWithId(vararg values: Pair<String, ComponentValue>) =
    values.forEach { updateComponentValueWithId(it.first, it.second) }

fun UIComponent.updateComponentValueWithId(
    componentId: String,
    componentValue: ComponentValue,
) = findComponentWithId(componentId)?.updateComponentValue(componentValue)

fun UIComponent.updateComponentValue(componentValue: ComponentValue) {
    val updateValue = this.value ?: ComponentValue()
    componentValue.text?.let { updateValue.text = it }
    componentValue.image?.let { updateValue.image = it }
    componentValue.url?.let { updateValue.url = it.toMutableList() }
    componentValue.extras?.let { updateValue.extras = it.toMutableMap() }
    componentValue.clickable?.let { updateValue.clickable = it }
    this.value = updateValue
}
