package com.example.weizhenbin.wangebug.tools;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by weizhenbin on 2018/8/21.
 */

public class PhoneTool {

    public static  int getScreenHeight(){
        return new DisplayMetrics().heightPixels;
    }
    public static  int getScreenWidth(){
        return new DisplayMetrics().widthPixels;
    }


    public static int dip2px(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale +0.5f);

    }

    public static int px2dip(Context context, float pxValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale +0.5f);

    }


}
