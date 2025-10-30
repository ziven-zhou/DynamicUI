package com.ziven.dynamic.ui

import androidx.compose.material3.SnackbarHostState
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
data class ComponentStyle(
    @SerialName("foregroundColor")
    val foregroundColor: String? = null,
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

class ComponentState(
    val snackBarHostState: SnackbarHostState? = null,
)
