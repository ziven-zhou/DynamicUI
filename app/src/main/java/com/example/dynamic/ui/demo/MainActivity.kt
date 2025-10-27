package com.example.dynamic.ui.demo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dynamic.ui.demo.ui.theme.DynamicUITheme
import com.ziven.dynamic.ui.ComponentList
import com.ziven.dynamic.ui.ComponentValue
import com.ziven.dynamic.ui.RenderComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DynamicUITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    DynamicUIView()
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
        componentType = "Scaffold",
        componentList =
            ComponentList(
                componentSize = { mutableIntStateOf(viewModel.data.size) },
                componentType = { viewModel.data.getOrNull(it)?.type },
            ) { index, componentId ->
                val item = viewModel.data.getOrNull(index) ?: return@ComponentList null
                val value =
                    ComponentValue(
                        text = item.title,
                        image = item.image,
                    )
                Log.d("DynamicUIView", "DynamicUIView: $item $value")
                value
            },
    ) {
        Log.d("DynamicUIView", "RenderComponent: $it")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DynamicUITheme {
        Greeting("Android")
    }
}