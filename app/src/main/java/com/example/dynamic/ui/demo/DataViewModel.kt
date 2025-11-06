package com.example.dynamic.ui.demo

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DataViewModel(
    application: Application,
) : AndroidViewModel(application) {
    val data: SnapshotStateList<DataBean> = mutableStateListOf()

    fun updateUi() {
        viewModelScope.launch(Dispatchers.Main) {
            val list =
                async(Dispatchers.IO) {
                    val result = mutableListOf<DataBean>()
                    repeat(40) {
                        when (it % 4) {
                            0 -> result.add(makeDataBean("Activity", it))
                            1 -> result.add(makeDataBean("Compose", it))
                            2 -> result.add(makeDataBean("SnackBar", it))
                            3 -> result.add(makeDataBean("DeepLink", it))
                        }
                    }
                    result
                }.await()
            data.clear()
            data.addAll(list)
        }
    }

    private fun makeDataBean(
        action: String,
        index: Int,
    ) = DataBean(
        key = "Key#$action$index",
        type = "item",
        content = "$action:$index",
        action = action,
        packageName = "com.example.dynamic.ui.demo",
        className = "com.example.dynamic.ui.demo.SecondActivity",
        deepLink = "https://www.baidu.com",
        image = "https://gips3.baidu.com/it/u=3886271102,3123389489&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960",
    )
}
