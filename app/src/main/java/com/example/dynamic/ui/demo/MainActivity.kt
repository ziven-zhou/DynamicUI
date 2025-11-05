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
import androidx.navigation.compose.rememberNavController
import com.example.dynamic.ui.demo.ui.theme.DynamicUITheme
import com.ziven.dynamic.ui.ComponentClick
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentState
import com.ziven.dynamic.ui.ComponentValue
import com.ziven.dynamic.ui.RenderComponent
import com.ziven.dynamic.ui.UIManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UIManager.setContext(this)
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
    val navHostController = rememberNavController()
    RenderComponent(
        componentType = "Root",
        componentList =
            ComponentList(
                componentSize = { which -> mutableIntStateOf(viewModel.data.size) },
                componentKey = { which, index -> viewModel.data.getOrNull(index)?.key ?: "" },
                componentType = { which, index -> viewModel.data.getOrNull(index)?.type },
            ) { which, index, componentId ->
                viewModel.data.getOrNull(index)?.let {
                    Log.d("UIComponent", "Update: $it")
                    when (componentId) {
                        "itemButton" ->
                            ComponentValue(
                                text = it.title,
                                click =
                                    listOf(
                                        ComponentClick(
                                            action = it.click,
                                            content = "I am dynamic ui.",
                                            tryFirst = true,
                                            deepLink = it.deepLink,
                                            packageName = it.packageName,
                                            className = it.className,
                                        ),
                                    ),
                            )

                        "itemImage" ->
                            ComponentValue(
                                image = it.image,
                                click =
                                    listOf(
                                        ComponentClick(
                                            action = it.click,
                                            content = "I am dynamic ui.",
                                            tryFirst = true,
                                            deepLink = it.deepLink,
                                            packageName = it.packageName,
                                            className = it.className,
                                        ),
                                    ),
                            )

                        else -> null
                    }
                }
            },
        componentState = ComponentState(snackBarHostState, navHostController),
    ) {
        Log.d("UIComponent", "Click: $it")
    }
}
