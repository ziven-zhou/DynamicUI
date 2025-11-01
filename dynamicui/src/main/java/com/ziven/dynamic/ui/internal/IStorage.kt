package com.ziven.dynamic.ui.internal

import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.forEachComponent

/**
 * @author Ziven
 * @date 2025/11/1
 */
internal interface IStorage {
    fun addAll(components: List<UIComponent>) {
        components.forEach { add(it) }
    }

    fun addAll(vararg components: UIComponent) {
        components.forEach { add(it) }
    }

    fun add(component: UIComponent) {
        component.forEachComponent {
            it.componentType.notEmpty { type -> set(type, component) }
        }
    }

    operator fun set(
        componentType: String,
        component: UIComponent,
    )

    operator fun get(componentType: String): UIComponent?
}
