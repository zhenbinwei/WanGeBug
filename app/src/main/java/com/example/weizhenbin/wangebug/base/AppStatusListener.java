package com.example.weizhenbin.wangebug.base;

/**
 * Created by weizhenbin on 2018/9/18.
 */

public abstract class AppStatusListener{
    protected abstract void onAppForeground();
    protected abstract void onAppBackground();
}
