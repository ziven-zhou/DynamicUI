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
                        result.add(
                            DataBean(
                                key = "Key#$it",
                                type = "item",
                                title = "I am a item$it",
                                click =
                                    when (it % 3) {
                                        0 -> "Activity"
                                        1 -> "Compose"
                                        2 -> "SnackBar"
                                        else -> "Other"
                                    },
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
