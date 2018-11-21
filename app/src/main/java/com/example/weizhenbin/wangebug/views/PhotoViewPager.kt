package com.example.weizhenbin.wangebug.views

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by weizhenbin on 2018/8/21.
 */


class PhotoViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet) : ViewPager(context,attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
       return try {
             super.onInterceptTouchEvent(ev)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
             false
        }

    }
}
