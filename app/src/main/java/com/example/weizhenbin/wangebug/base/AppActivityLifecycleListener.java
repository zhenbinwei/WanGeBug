package com.example.weizhenbin.wangebug.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by weizhenbin on 2018/9/21.
 */

public abstract class AppActivityLifecycleListener {
    protected abstract void onActivityCreated(Activity activity, Bundle savedInstanceState);
    protected abstract void onActivityStarted(Activity activity);
    protected abstract  void onActivityResumed(Activity activity);
    protected abstract  void onActivityPaused(Activity activity);
    protected abstract  void onActivityStopped(Activity activity);
    protected abstract void onActivitySaveInstanceState(Activity activity, Bundle outState);
    protected abstract  void onActivityDestroyed(Activity activity);

    protected abstract void onAppForeground();
    protected abstract void onAppBackground();
}
