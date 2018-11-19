package com.example.weizhenbin.wangebug.base

import android.app.Activity
import android.os.Bundle

/**
 * Created by weizhenbin on 2018/9/18.
 */

abstract class AppStatusListener : AppActivityLifecycleListener() {
    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityStarted(activity: Activity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResumed(activity: Activity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityPaused(activity: Activity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityStopped(activity: Activity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityDestroyed(activity: Activity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAppForeground() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAppBackground() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
