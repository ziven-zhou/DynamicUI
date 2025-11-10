package com.ziven.dynamic.ui.componentIn

import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ziven.dynamic.ui.ComponentAction
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.ForEachChildComponent
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.componentTo.componentUI
import com.ziven.dynamic.ui.componentTo.setChecked
import com.ziven.dynamic.ui.componentTo.toChecked
import com.ziven.dynamic.ui.componentTo.toColors
import com.ziven.dynamic.ui.componentTo.toEnabled
import com.ziven.dynamic.ui.componentTo.toSwitchColors

@Composable
internal fun SwitchComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
    val componentId = uiComponent.componentId
    val componentValue = uiComponent.value?.copy()
    Switch(
        modifier = modifier.componentUI(uiComponent),
        checked = componentValue.toChecked(),
        onCheckedChange = {
            componentValue.setChecked()
            onAction(ComponentAction("Switch", componentId, componentValue))
        },
        thumbContent = {
            uiComponent.ForEachChildComponent { child ->
                if (componentValue.toChecked()) {
                    DispatchRenderComponent(
                        child,
                        Modifier,
                        onAction,
                        componentList,
                        componentState,
                    )
                }
            }
        },
        enabled = uiComponent.value.toEnabled(),
        colors = uiComponent.style.toColors().toSwitchColors(),
    )
}

@Composable
internal fun RadioButtonComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
//    val componentId = uiComponent.componentId
//    val componentValue = uiComponent.value?.copy()
//    RadioButton(
//        modifier = modifier.componentUI(uiComponent),
//        selected = componentValue.toChecked(),
//        onClick = {
//            componentValue.setChecked()
//            onAction(ComponentAction("RadioButton", componentId, componentValue))
//        },
//        colors = uiComponent.style.toColors().toRadioButtonColors(),
//    )
}

@Composable
internal fun CheckBoxComponent(
    uiComponent: UIComponent,
    modifier: Modifier = Modifier,
    onAction: (ComponentAction) -> Unit,
    componentList: ComponentList? = null,
    componentState: ComponentState? = null,
) {
//    val componentId = uiComponent.componentId
//    val componentValue = uiComponent.value?.copy()
//    Checkbox(
//        modifier = modifier.componentUI(uiComponent),
//        checked = componentValue.toChecked(),
//        onCheckedChange = {
//            componentValue.setChecked()
//            onAction(ComponentAction("CheckBox", componentId, componentValue))
//        },
//        colors = uiComponent.style.toColors().toCheckboxColors(),
//    )
}
