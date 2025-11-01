package com.ziven.dynamic.ui

import android.content.Context
import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import com.ziven.dynamic.ui.internal.Holder
import com.ziven.dynamic.ui.internal.VModel
import com.ziven.dynamic.ui.internal.notEmpty

object UIManager {
    private val holder = Holder()

    fun setContext(context: Context) {
        holder.setContext(context)
    }

    fun getContext() = holder.getContext()

    @MainThread
    fun addComponents(components: List<UIComponent>) = apply { VModel.storage.addAll(components) }

    @MainThread
    fun addComponents(vararg components: UIComponent) = apply { VModel.storage.addAll(*components) }

    @MainThread
    fun addComponent(component: UIComponent) = apply { VModel.storage.add(component) }

    @MainThread
    fun getComponent(componentType: String?) = componentType.notEmpty { VModel.storage[it] }

    @Composable
    fun RunComponent(
        componentType: String?,
        block: @Composable (UIComponent) -> Unit,
    ) = getComponent(componentType)?.let { block.invoke(it) }
}
