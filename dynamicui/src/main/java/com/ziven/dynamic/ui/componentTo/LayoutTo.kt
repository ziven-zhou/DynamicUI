package com.ziven.dynamic.ui.componentTo

import com.ziven.dynamic.ui.ComponentLayout
import com.ziven.dynamic.ui.internal.toDp

internal fun ComponentLayout?.toPadding(): PaddingValue? =
    if (this == null) {
        null
    } else if (start == null && end == null && top == null && bottom == null) {
        null
    } else {
        PaddingValue(
            start = start.toDp(),
            top = top.toDp(),
            end = end.toDp(),
            bottom = bottom.toDp(),
        )
    }
