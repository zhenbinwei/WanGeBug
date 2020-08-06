package com.example.weizhenbin.wangebug.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.image.ImageLoader
import com.example.weizhenbin.wangebug.image.glide.GlideImageLoader
import com.example.weizhenbin.wangebug.modules.MyObjectBox
import com.example.weizhenbin.wangebug.views.floatingwindow.TodoFloatingWindowManager
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import io.objectbox.BoxStore
import java.util.*

/**
 * Created by weizhenbin on 2018/8/8.
 */

class App : Application() {
     val appStatusListeners = ArrayList<AppActivityLifecycleListener>()
    lateinit var boxStore: BoxStore

    override fun onCreate() {
        super.onCreate()
        app = this
        ImageLoader.getImageLoader().init(GlideImageLoader())
        initBugly()
        addLifecycleCallback()
        initObjectBox()
    }

    private fun initObjectBox() {
        boxStore = MyObjectBox.builder().androidContext(this).build()
      //  AndroidObjectBrowser(boxStore).start(this)
    }

    private fun initBugly() {
        Beta.upgradeDialogLayoutId = R.layout.bugly_custom_update_dialog
        Beta.tipsDialogLayoutId = R.layout.bugly_custom_dialog
        Beta.dialogFullScreen = true
        Bugly.init(applicationContext, "2e1751ae2e", true)
    }

    private inner class LifecycleCallback : Application.ActivityLifecycleCallbacks {

        private var activityCount = 0
        private var activityShow = 0

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
          //  Log.d("LifecycleCallback", "onActivityCreated$savedInstanceState")
            activityCount++
            for (appStatusListener in appStatusListeners) {
                appStatusListener.onActivityCreated(activity, savedInstanceState)
            }
        }

        override fun onActivityStarted(activity: Activity?) {
          //  Log.d("LifecycleCallback", "onActivityStarted${activity?.javaClass?.simpleName}")
            activityShow++
            if (activityShow > 0) {
                Log.d("LifecycleCallback", "应用处于前台")
                for (appStatusListener in appStatusListeners) {
                    appStatusListener.onAppForeground()
                }
            }
            for (appStatusListener in appStatusListeners) {
                appStatusListener.onActivityStarted(activity)
            }
        }

        override fun onActivityResumed(activity: Activity?) {
           // Log.d("LifecycleCallback", "onActivityResumed" + activity?.javaClass?.simpleName)
            for (appStatusListener in appStatusListeners) {
                appStatusListener.onActivityResumed(activity)
            }
        }

        override fun onActivityPaused(activity: Activity?) {
           // Log.d("LifecycleCallback", "onActivityPaused" + activity?.javaClass?.simpleName)
            for (appStatusListener in appStatusListeners) {
                appStatusListener.onActivityPaused(activity)
            }
        }

        override fun onActivityStopped(activity: Activity?) {
          //  Log.d("LifecycleCallback", "onActivityStopped" + activity?.javaClass?.simpleName)
            activityShow--
            if (activityShow <= 0) {
                Log.d("LifecycleCallback", "应该即将退出后台")
                for (appStatusListener in appStatusListeners) {
                    appStatusListener.onAppBackground()
                }
            }
            for (appStatusListener in appStatusListeners) {
                appStatusListener.onActivityStopped(activity)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            for (appStatusListener in appStatusListeners) {
                appStatusListener.onActivitySaveInstanceState(activity, outState)
            }
        }

        override fun onActivityDestroyed(activity: Activity?) {
            //对于直接杀进程 并不会调用onActivityDestroyed
          //  Log.d("LifecycleCallback", "onActivityDestroyed" + activity?.javaClass?.simpleName)
            activityCount--
            if (activityCount <= 0) {
                Log.d("LifecycleCallback", "应用退出")
                appStatusListeners.clear()
                onAppQuit()
            }
            for (appStatusListener in appStatusListeners) {
                appStatusListener.onActivityDestroyed(activity)
            }
        }
    }

    /**
     * 应用退出的时候
     */
    private fun onAppQuit() {
        TodoFloatingWindowManager.manager.cleanFloatingWindow()
    }

    /**
     * 添加全局生命周期
     */
    private fun addLifecycleCallback() {
        registerActivityLifecycleCallbacks(LifecycleCallback())
    }


    fun addAppStatusListener(appStatusListener: AppStatusListener?) {
        if (appStatusListener != null && !appStatusListeners.contains(appStatusListener)) {
            appStatusListeners.add(appStatusListener)
        }
    }

    fun removeAppStatusListener(appStatusListener: AppStatusListener?) {
        if (appStatusListener != null && appStatusListeners.contains(appStatusListener)) {
            appStatusListeners.remove(appStatusListener)
        }
    }

    companion object {

       lateinit var app: App
    }


}
