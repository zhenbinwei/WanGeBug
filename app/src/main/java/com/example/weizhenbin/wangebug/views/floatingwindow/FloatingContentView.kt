package com.example.weizhenbin.wangebug.views.floatingwindow

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout

/**
 * Created by weizhenbin on 18/9/16.
 */

class FloatingContentView @JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0): FrameLayout(context,attrs,defStyleAttr) {

    private var mOnKeyListener: View.OnKeyListener? = null


    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP) {
            if (event.keyCode == KeyEvent.KEYCODE_BACK || event.keyCode == KeyEvent.KEYCODE_SETTINGS) {
                if (mOnKeyListener != null) {
                    mOnKeyListener!!.onKey(this, KeyEvent.KEYCODE_BACK, event)
                    return true
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }

    override fun setOnKeyListener(l: View.OnKeyListener) {
        mOnKeyListener = l

        super.setOnKeyListener(l)
    }
}
