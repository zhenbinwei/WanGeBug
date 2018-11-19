package com.example.weizhenbin.wangebug.base

import android.app.Activity
import android.os.Bundle

/**
 * Created by weizhenbin on 2018/9/21.
 */

abstract class AppActivityLifecycleListener {
     abstract fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?)
     abstract fun onActivityStarted(activity: Activity?)
     abstract fun onActivityResumed(activity: Activity?)
     abstract fun onActivityPaused(activity: Activity?)
     abstract fun onActivityStopped(activity: Activity?)
     abstract fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?)
     abstract fun onActivityDestroyed(activity: Activity?)

     abstract fun onAppForeground()
     abstract fun onAppBackground()
}
