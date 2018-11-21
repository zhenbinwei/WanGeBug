package com.example.weizhenbin.wangebug.tools

import com.example.weizhenbin.wangebug.base.App

/**
 * Created by weizhenbin on 2018/8/21.
 */

object PhoneTool {

    val screenHeight: Int
        get() = App.app.resources.displayMetrics.heightPixels
    val screenWidth: Int
        get() = App.app.resources.displayMetrics.widthPixels


    fun dip2px(dpValue: Float): Int {

        val scale = App.app.resources.displayMetrics.density

        return (dpValue * scale + 0.5f).toInt()

    }

    fun px2dip(pxValue: Float): Int {

        val scale = App.app.resources.displayMetrics.density

        return (pxValue / scale + 0.5f).toInt()

    }


}
