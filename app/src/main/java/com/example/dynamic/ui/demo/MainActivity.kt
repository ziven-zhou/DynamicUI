package com.example.dynamic.ui.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dynamic.ui.demo.ui.theme.DynamicUITheme
import com.ziven.dynamic.ui.ComponentClick
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.ComponentValue
import com.ziven.dynamic.ui.RenderComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { DynamicUITheme { DynamicUIView() } }
    }
}

@Composable
fun DynamicUIView() {
    val viewModel = viewModel<DataViewModel>()
    val uiViewModel = viewModel<UIViewModel>()
    LaunchedEffect("LoadUpdateUi") {
        uiViewModel.updateUI()
        viewModel.updateUi()
    }
    val snackBarHostState = remember { SnackbarHostState() }
    RenderComponent(
        componentType = "Scaffold",
        componentList =
            ComponentList(
                componentSize = { mutableIntStateOf(viewModel.data.size) },
                componentType = { viewModel.data.getOrNull(it)?.type },
            ) { index, componentId ->
                viewModel.data.getOrNull(index)?.let {
                    when (componentId) {
                        "itemButton" ->
                            ComponentValue(
                                text = it.title,
                                click =
                                    listOf(
                                        ComponentClick(
                                            type = it.click,
                                            content = "I am dynamic ui.",
                                            tryFirst = true,
                                            deepLink = "https://www.baidu.com",
                                        ),
                                    ),
                            )

                        "itemImage" -> ComponentValue(image = it.image)
                        else -> null
                    }
                }
            },
        componentState = ComponentState(snackBarHostState),
    ) {
        Log.d("DynamicUIView", "Click: $it")
    }
}
