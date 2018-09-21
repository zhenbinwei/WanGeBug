package com.example.weizhenbin.wangebug.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by weizhenbin on 2018/9/18.
 */

public abstract class AppStatusListener extends AppActivityLifecycleListener {
    protected abstract void onAppForeground();
    protected abstract void onAppBackground();


    @Override
    protected void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    protected void onActivityStarted(Activity activity) {

    }

    @Override
    protected void onActivityResumed(Activity activity) {

    }

    @Override
    protected void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    protected void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    protected void onActivityDestroyed(Activity activity) {

    }
}
