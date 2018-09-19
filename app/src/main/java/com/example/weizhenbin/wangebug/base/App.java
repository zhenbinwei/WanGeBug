package com.example.weizhenbin.wangebug.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.image.glide.GlideImageLoader;
import com.example.weizhenbin.wangebug.views.floatingwindow.TodoFloatingWindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/8.
 */

public class App extends Application {

    public static App app;
    private List<AppStatusListener> appStatusListeners=new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        ImageLoader.getImageLoader().init(new GlideImageLoader());
       /* HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, false);
        QbSdk.initTbsSettings(map);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);*/
        addLifecycleCallback();
    }


    private class  LifecycleCallback implements ActivityLifecycleCallbacks{

        private int activityCount=0;
        private int activityShow=0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.d("LifecycleCallback", "onActivityCreated"+activity.getClass().getSimpleName());
            activityCount++;
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.d("LifecycleCallback", "onActivityStarted"+activity.getClass().getSimpleName());
            activityShow++;
            if (activityShow>0){
                Log.d("LifecycleCallback", "应用处于前台");
                for (AppStatusListener appStatusListener:appStatusListeners){
                    appStatusListener.onAppForeground();
                }
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d("LifecycleCallback", "onActivityResumed"+activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d("LifecycleCallback", "onActivityPaused"+activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.d("LifecycleCallback", "onActivityStopped"+activity.getClass().getSimpleName());
            activityShow--;
            if (activityShow<=0){
                Log.d("LifecycleCallback", "应该即将退出后台");
                for (AppStatusListener appStatusListener:appStatusListeners){
                    appStatusListener.onAppBackground();
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            //对于直接杀进程 并不会调用onActivityDestroyed
            Log.d("LifecycleCallback", "onActivityDestroyed"+activity.getClass().getSimpleName());
            activityCount--;
            if (activityCount<=0){
                Log.d("LifecycleCallback", "应用退出");
                appStatusListeners.clear();
                onAppQuit();
            }
        }
    }

    /**
     * 应用退出的时候
     * */
    private void onAppQuit() {
        TodoFloatingWindowManager.getManager().cleanFloatingWindow();
    }

    /**
     * 添加全局生命周期
     * */
    private void addLifecycleCallback() {
        registerActivityLifecycleCallbacks(new LifecycleCallback());
    }


    public void addAppStatusListener(AppStatusListener appStatusListener){
        if (appStatusListener!=null&&!appStatusListeners.contains(appStatusListener)){
            appStatusListeners.add(appStatusListener);
        }
    }

    public void removeAppStatusListener(AppStatusListener appStatusListener){
        if (appStatusListener!=null&&appStatusListeners.contains(appStatusListener)){
            appStatusListeners.remove(appStatusListener);
        }
    }




}
