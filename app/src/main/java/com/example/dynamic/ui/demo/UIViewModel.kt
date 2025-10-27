package com.example.dynamic.ui.demo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ui.dynamic.uiList
import com.ziven.dynamic.ui.UIComponent
import com.ziven.dynamic.ui.UIManager
import com.ziven.dynamic.ui.runSafe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class UIViewModel(
    application: Application,
) : AndroidViewModel(application) {
    fun updateUI() {
        viewModelScope.launch(Dispatchers.Main) {
            val result =
                viewModelScope
                    .async(Dispatchers.IO) {
                        val uiData = mutableListOf<UIComponent>()
                        uiList.forEach { json -> jsonToBean(json)?.let { ui -> uiData.add(ui) } }
                        uiData
                    }.await()
            result.let { UIManager.addComponents(it) }
        }
    }

    private fun jsonToBean(json: String): UIComponent? = runSafe("jsonToBean") { Json.decodeFromString<UIComponent>(json) }
}
