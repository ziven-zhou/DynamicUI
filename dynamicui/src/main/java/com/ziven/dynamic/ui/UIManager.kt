package com.ziven.dynamic.ui

import androidx.annotation.MainThread
import androidx.compose.runtime.mutableStateMapOf

object UIManager {
    private val componentMap = mutableStateMapOf<String, UIComponent>()

    @MainThread
    fun addComponents(components: List<UIComponent>) = components.forEach { addComponent(it) }

    @MainThread
    fun addComponents(vararg components: UIComponent) = components.forEach { addComponent(it) }

    @MainThread
    fun addComponent(parent: UIComponent) =
        parent.forEachComponent { child ->
            child.componentType?.let { type ->
                logPrint("addComponent: $type: $child")
                componentMap[type] = child
            }
        }

    @MainThread
    fun getComponent(componentId: String?) =
        let {
            val component = if (componentId.isNullOrEmpty()) null else componentMap[componentId]?.copy()
            logPrint("getComponent: $componentId: $component")
            component
        }
}
