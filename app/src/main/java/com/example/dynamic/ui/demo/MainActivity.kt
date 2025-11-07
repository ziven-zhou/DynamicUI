package com.example.dynamic.ui.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dynamic.ui.demo.ui.theme.DynamicUITheme
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
    RenderComponent(
        componentType = "Root",
        componentList = mainList(viewModel),
        componentState = mainState(),
    ) {
        Log.d("UIComponent", "Click: $it")
    }
}
