package com.ziven.dynamic.ui.internal

import com.ziven.dynamic.ui.ComponentValue

internal fun ComponentValue?.toText() = this?.text ?: ""

internal fun ComponentValue?.toImage() = this?.image

internal fun ComponentValue?.toClickable() = this?.clickable ?: false
