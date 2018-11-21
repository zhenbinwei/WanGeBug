package com.example.weizhenbin.wangebug.views

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration

/**
 * Created by weizhenbin on 2018/3/28.
 */

class SlideExitTouch(context: Context) : View.OnTouchListener {
    private var moveY = 0f
    private var downY = 0f
    private var firstDownY = 0f
    private val touchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop
    private var stateListener: IStateListener? = null
    private var mGestureDetector: GestureDetector? = null

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.pointerCount > 1) {
            return false
        }
        if (v is TouchImageView) {
            /* if(((TouchImageView) v).isZoomed()||((TouchImageView) v).canVerticalDrag()){
                return false;
            }*/
            if (v.isZoomed) {
                return false
            }
        }
        if (mGestureDetector == null) {
            mGestureDetector = GestureDetector(v.context, GestureListener(v))
        }
        if (v !is TouchImageView) {
            //长按 点击和onTouch 冲突问题  借助GestureDetector来解决 TouchImageView已经带有mGestureDetector如果不排除 会回调两次
            mGestureDetector!!.onTouchEvent(event)
        }
        if (event.pointerCount > 1) {
            return false
        }
        if (v is TouchImageView) {
            if (v.isZoomed) {
                return false
            }
        }
        if (event.action == MotionEvent.ACTION_DOWN) {
            downY = event.rawY
            firstDownY = event.rawY
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            if (v is TouchImageView) {
                moveY = event.rawY
                v.setY(v.getY() + (moveY - downY))
                downY = moveY
                v.setCanDrag(false)

                if (v.getY() > 0 && v.canScrollVertical(-1)) {
                    v.setY(0f)
                    v.setCanDrag(true)
                } else if (v.getY() < 0 && v.canScrollVertical(1)) {
                    v.setY(0f)
                    v.setCanDrag(true)
                }
            } else {
                moveY = event.rawY
                v.y = v.y + (moveY - downY)
                downY = moveY
            }
            if (stateListener != null && Math.abs(event.rawY - firstDownY) > touchSlop) {
                stateListener!!.onMove(v.y.toInt())
            }
        }
        if (event.action == MotionEvent.ACTION_UP) {
            if (Math.abs(v.y) > 250) {
                if (stateListener != null) {
                    stateListener!!.onFinish()
                }
            } else {
                v.y = 0f
                if (stateListener != null) {
                    stateListener!!.onUp()
                }
            }
        }
        return true
    }

    fun setStateListener(stateListener: IStateListener) {
        this.stateListener = stateListener
    }

    interface IStateListener {
        fun onMove(offset: Int)

        fun onFinish()

        fun onUp()
    }

    private inner class GestureListener(private val view: View?) : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return view != null && view.performClick()
        }

        override fun onLongPress(e: MotionEvent) {
            if (view == null) {
                return
            }
            view.performLongClick()
        }
    }
}
