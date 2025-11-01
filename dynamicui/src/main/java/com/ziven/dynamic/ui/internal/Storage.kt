package com.ziven.dynamic.ui.internal

import androidx.compose.runtime.mutableStateMapOf
import com.ziven.dynamic.ui.UIComponent

/**
 * @author Ziven
 * @date 2025/11/1
 */
internal class Storage : IStorage {
    private val storage = mutableStateMapOf<String, UIComponent>()

    override fun set(
        componentType: String,
        component: UIComponent,
    ) {
        storage[componentType] = component
    }

    override fun get(componentType: String): UIComponent? = storage[componentType]
}
