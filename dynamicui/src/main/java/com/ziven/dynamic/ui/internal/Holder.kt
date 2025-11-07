package com.ziven.dynamic.ui.internal

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import java.lang.ref.WeakReference

/**
 * @author Ziven
 * @date 2025/11/1
 */
internal class Holder : Application.ActivityLifecycleCallbacks {
    private var application: Application? = null
    private var cacheActivity: WeakReference<Activity>? = null

    fun setContext(context: Context, cacheActivity: Boolean) {
        if (context is Application) {
            application = context
        } else {
            val applicationContext = context.applicationContext
            if (applicationContext is Application) {
                application = applicationContext
            }
        }
        if (cacheActivity) {
            logPrint("HolderTag:registerActivityLifecycleCallbacks")
            application?.registerActivityLifecycleCallbacks(this)
        }
    }

    fun getContext(): Context? = application

    fun getApplication(): Application? = application

    fun getActivity(): Activity? = cacheActivity?.get()

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?,
    ) = Unit

    override fun onActivityDestroyed(activity: Activity) = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityResumed(activity: Activity) {
        logPrint("HolderTag:onActivityResumed: $activity")
        if (cacheActivity?.get() != activity) {
            cacheActivity = WeakReference(activity)
        }
    }

    override fun onActivitySaveInstanceState(
        activity: Activity,
        outState: Bundle,
    ) = Unit

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit
}
