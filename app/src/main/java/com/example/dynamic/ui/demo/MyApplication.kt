package com.example.dynamic.ui.demo

import android.app.Application
import com.ziven.dynamic.ui.UIManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        UIManager.setContext(this, true)
    }
}