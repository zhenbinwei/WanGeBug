package com.example.weizhenbin.wangebug.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by weizhenbin on 2018/9/26.
 */

public class EventBusHandler {



    public static void register(Object subscriber){
        EventBus.getDefault().register(subscriber);
    }
    public static void unregister(Object subscriber){
        EventBus.getDefault().unregister(subscriber);
    }

    public static void post(Object event){
        EventBus.getDefault().post(event);
    }

}
