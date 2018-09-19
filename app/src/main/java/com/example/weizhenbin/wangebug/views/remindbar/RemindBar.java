package com.example.weizhenbin.wangebug.views.remindbar;

import android.view.View;

/**
 * Created by weizhenbin on 2018/9/19.
 */

public class RemindBar {
    public static final int LENGTH_SHORT = 1500;

    public static final int LENGTH_LONG =3000;

    private static RemindBar remindBar;

    private RemindBarLayout barLayout;

    private RemindBar() {
    }

    public static RemindBarLayout make(View view,String msg, int duration){
        if (remindBar==null){
            synchronized (RemindBar.class){
                if (remindBar==null){
                    remindBar=new RemindBar();
                }
            }
        }
        if (remindBar.barLayout==null){
            remindBar.barLayout=new RemindBarLayout(view);
        }
        remindBar.barLayout.setDuration(duration);
        remindBar.barLayout.setMsgText(msg);
        return remindBar.barLayout;
    }


}
