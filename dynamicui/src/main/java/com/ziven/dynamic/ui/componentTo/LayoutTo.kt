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

internal fun ComponentLayout?.toContentPadding(): PaddingValue? =
    if (this == null) {
        null
    } else if (contentStart == null && contentEnd == null && contentTop == null && contentBottom == null) {
        null
    } else {
        PaddingValue(
            start = contentStart.toDp(),
            top = contentTop.toDp(),
            end = contentEnd.toDp(),
            bottom = contentBottom.toDp(),
        )
    }

internal fun ComponentLayout?.toReverseLayout(): Boolean = this?.reverseLayout == true
