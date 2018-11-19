package com.example.weizhenbin.wangebug.base

/**
 * Created by weizhenbin on 2018/8/22.
 * in 泛型T处于参数位置 out处于返回值位置
 */
interface DataResult<in T> {

    fun onStart()

    fun onError(throwable: Throwable)

    fun onSuccess(t: T?)

}
