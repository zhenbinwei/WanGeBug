package com.example.weizhenbin.wangebug.base

import android.app.Activity
import android.os.Bundle

/**
 * Created by weizhenbin on 2018/9/18.
 */

abstract class AppStatusListener : AppActivityLifecycleListener() {
    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onAppForeground() {
    }

    override fun onAppBackground() {
    }
}
