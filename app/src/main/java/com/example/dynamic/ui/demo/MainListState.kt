package com.example.dynamic.ui.demo

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.ComponentValue
import com.ziven.dynamic.ui.addImage
import com.ziven.dynamic.ui.addText
import com.ziven.dynamic.ui.ofActivityClick
import com.ziven.dynamic.ui.ofComposeClick
import com.ziven.dynamic.ui.ofDeepLinkClick
import com.ziven.dynamic.ui.ofImageValue
import com.ziven.dynamic.ui.ofSnackBarClick
import com.ziven.dynamic.ui.ofTextValue
import com.ziven.dynamic.ui.toComponentValue
import com.ziven.dynamic.ui.updateComponentValueWithId

@Composable
internal fun mainList(model: DataViewModel): ComponentList {
    val data = remember { model.data }
    return ComponentList(
        componentSize = { which -> mutableIntStateOf(data.size) },
        componentKey = { which, index -> data.getOrNull(index)?.key ?: "" },
        componentType = { which, index -> data.getOrNull(index)?.type },
    ) { which, index, componentId ->
        data.getOrNull(index)?.let {
            Log.d("UIComponent", "Update: $it")
            when (componentId) {
                "itemButton" -> makeComponentValue(it)?.addText(it.content)
                "itemImage" ->
                    ofComposeClick(
                        "Detail",
                        mutableListOf(it.content, it.image ?: ""),
                    ).toComponentValue().addImage(it.image)
                else -> null
            }
        }
    }
}

private fun makeComponentValue(dataBean: DataBean): ComponentValue? =
    when (dataBean.action) {
        "Activity" ->
            ofActivityClick(
                dataBean.packageName,
                dataBean.className,
                mutableMapOf("title" to dataBean.content, "image" to (dataBean.image ?: "")),
            ).toComponentValue()
        "DeepLink" -> ofDeepLinkClick(dataBean.deepLink).toComponentValue()
        "Compose" ->
            ofComposeClick(
                "Detail",
                mutableListOf(dataBean.content, dataBean.image ?: ""),
            ).toComponentValue()
        "SnackBar" -> ofSnackBarClick(content = dataBean.content).toComponentValue()
        else -> null
    }

@Composable
internal fun mainState(): ComponentState {
    val snackBarHostState = remember { SnackbarHostState() }
    val navHostController = rememberNavController()
    return ComponentState(snackBarHostState, navHostController) { which, component, value ->
        Log.d("UIComponent", "ComponentState: which:$which value:$value")
        if (value.isNotEmpty()) {
            component
                .updateComponentValueWithId("DetailTopBar", ofTextValue(value["title"]))
                .updateComponentValueWithId("DetailImage", ofImageValue(value["image"]))
        }
        Log.d("UIComponent", "ComponentState: component:$component")
        true
    }
}
