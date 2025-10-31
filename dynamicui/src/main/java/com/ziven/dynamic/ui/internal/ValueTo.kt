package com.ziven.dynamic.ui.internal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.ziven.dynamic.ui.ComponentValue

internal fun ComponentValue?.toText() = this?.text ?: ""

internal fun ComponentValue?.toImage() = this?.image

internal fun ComponentValue?.toClickable() = this?.clickable ?: false

internal fun ComponentValue?.toIcon(): ImageVector? =
    when (this?.image) {
        "Menu" -> Icons.Default.Menu
        "Add" -> Icons.Default.Add
        "Close" -> Icons.Default.Close
        "Delete" -> Icons.Default.Delete
        "ArrowDropDown" -> Icons.Default.ArrowDropDown
        else -> null
    }
