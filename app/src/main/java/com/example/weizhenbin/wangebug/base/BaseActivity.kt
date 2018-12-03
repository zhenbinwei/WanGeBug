package com.example.weizhenbin.wangebug.base

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.WindowManager

import com.example.weizhenbin.wangebug.tools.TouchTool

/**
 * Created by weizhenbin on 2018/8/2.
 */

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            TouchTool.downX = ev.x
            TouchTool.downY = ev.y
        }
        return super.dispatchTouchEvent(ev)
    }
}
