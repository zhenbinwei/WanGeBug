package com.example.weizhenbin.wangebug.net.retrofit.entity

/**
 * Created by weizhenbin on 2018/8/11.
 */

class WanAndroidBean<T> {
    var errorCode: Int = 0
    var errorMsg: String? = null
    var data: T? = null
}
