package com.ziven.dynamic.ui.component

import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.toComponentAction

fun click(
    uiComponent: UIComponent,
    onClick: (ComponentAction) -> Unit,
): () -> Unit =
    {
        onClick(uiComponent.toComponentAction())
    }
