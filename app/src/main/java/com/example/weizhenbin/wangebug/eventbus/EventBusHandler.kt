package com.example.weizhenbin.wangebug.eventbus

import org.greenrobot.eventbus.EventBus

/**
 * Created by weizhenbin on 2018/9/26.
 */

object EventBusHandler {


    fun register(subscriber: Any) {
        EventBus.getDefault().register(subscriber)
    }

    fun unregister(subscriber: Any) {
        EventBus.getDefault().unregister(subscriber)
    }

    fun post(event: Any) {
        EventBus.getDefault().post(event)
    }

}
