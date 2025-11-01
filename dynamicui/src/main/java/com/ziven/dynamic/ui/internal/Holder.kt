package com.ziven.dynamic.ui.internal

import android.app.Application
import android.content.Context

/**
 * @author Ziven
 * @date 2025/11/1
 */
internal class Holder {
    private var application: Application? = null

    fun setContext(context: Context) {
        if (context is Application) {
            application = context
            return
        }
        val applicationContext = context.applicationContext
        if (applicationContext is Application) {
            application = applicationContext
        }
    }

    fun getContext(): Context? = application

    fun getApplication(): Application? = application
}
