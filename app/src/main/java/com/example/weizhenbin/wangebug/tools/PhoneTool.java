package com.example.weizhenbin.wangebug.tools;

import com.example.weizhenbin.wangebug.base.App;

/**
 * Created by weizhenbin on 2018/8/21.
 */

public class PhoneTool {

    public static  int getScreenHeight(){
        return App.Companion.getApp().getResources().getDisplayMetrics().heightPixels;
    }
    public static  int getScreenWidth(){
        return App.Companion.getApp().getResources().getDisplayMetrics().widthPixels;
    }


    public static int dip2px(float dpValue) {

        final float scale = App.Companion.getApp().getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale +0.5f);

    }

    public static int px2dip(float pxValue) {

        final float scale = App.Companion.getApp().getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale +0.5f);

    }


}
