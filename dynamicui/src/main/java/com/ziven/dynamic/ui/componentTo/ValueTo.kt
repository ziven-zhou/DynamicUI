package com.ziven.dynamic.ui.componentTo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.AccessAlarms
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Storage
import androidx.compose.ui.graphics.vector.ImageVector
import com.ziven.dynamic.ui.ComponentValue

internal fun ComponentValue?.toText() = this?.text ?: ""

internal fun ComponentValue?.toImage() = this?.image

internal fun ComponentValue?.toClickable() = this?.clickable ?: false

internal fun ComponentValue?.toExtras(): MutableMap<String, String> = this?.extras?.toMutableMap() ?: mutableMapOf()

internal fun ComponentValue?.toKeys(): MutableList<String> = this.toExtras().keys.toMutableList()

internal fun ComponentValue?.toValues(): MutableList<String> = this.toExtras().values.toMutableList()

internal fun ComponentValue?.toIcon(): ImageVector? =
    when (this?.image) {
        "Menu" -> Icons.Default.Menu
        "Add" -> Icons.Default.Add
        "Close" -> Icons.Default.Close
        "Delete" -> Icons.Default.Delete
        "Storage" -> Icons.Default.Storage
        "Abc" -> Icons.Default.Abc
        "AcUnit" -> Icons.Default.AcUnit
        "AccessAlarm" -> Icons.Default.AccessAlarm
        "AccessAlarms" -> Icons.Default.AccessAlarms
        "ArrowDropDown" -> Icons.Default.ArrowDropDown
        else -> null
    }
