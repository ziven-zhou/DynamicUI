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
                        var title = "Other:$it"
                        var click = "Other"
                        when (it % 3) {
                            0 -> {
                                title = "Activity:$it"
                                click = "Activity"
                            }
                            1 -> {
                                title = "Compose:$it"
                                click = "Compose"
                            }
                            2 -> {
                                title = "SnackBar:$it"
                                click = "SnackBar"
                            }
                        }
                        result.add(
                            DataBean(
                                key = "Key#$it",
                                type = "item",
                                title = title,
                                click = click,
                                packageName = "com.example.dynamic.ui.demo",
                                className = "com.example.dynamic.ui.demo.SecondActivity",
                                deepLink = "https://www.baidu.com",
                                icon = R.drawable.ic_launcher_background,
                                image = "https://gips3.baidu.com/it/u=3886271102,3123389489&fm=3028&app=3028&f=JPEG&fmt=auto?w=1280&h=960",
                            ),
                        )
                    }
                    result
                }.await()
            data.clear()
            data.addAll(list)
        }
    }
}
