package com.example.weizhenbin.wangebug.views.remindbar

import android.animation.LayoutTransition
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView

import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.App

/**
 * Created by weizhenbin on 2018/9/18.
 */

class RemindBarLayout(view: View) {

    private val remindBarLayout: View
    private val parent: ViewGroup?

    private val tvMsg: TextView
    private val tvAction: TextView

    private var msgText: String? = null
    private var actionText: String? = null

    private var duration = RemindBar.LENGTH_LONG
    private val handler: RemindBarHandler


    init {
        parent = findSuitableParent(view)
        if (parent == null) {
            throw IllegalArgumentException("No suitable parent found from the given view. " + "Please provide a valid view.")
        }
        parent.layoutTransition = LayoutTransition()
        remindBarLayout = LayoutInflater.from(App.app.applicationContext).inflate(R.layout.remind_bar_layout, parent, false)
        tvMsg = remindBarLayout.findViewById(R.id.tv_msg)
        tvAction = remindBarLayout.findViewById(R.id.tv_action)
        handler = RemindBarHandler(this)
    }

    fun setBackgroundColor(color: Int): RemindBarLayout {
        remindBarLayout.setBackgroundColor(color)
        return this
    }

    fun setMsgTextColor(color: Int): RemindBarLayout {
        tvMsg.setTextColor(color)
        return this
    }


    fun setActionTextColor(color: Int): RemindBarLayout {
        tvAction.setTextColor(color)
        return this
    }

    fun setActionBackGround(background: Drawable): RemindBarLayout {
        tvAction.background = background
        return this
    }

    fun setMsgText(msgText: String): RemindBarLayout {
        this.msgText = msgText
        return this
    }

    fun setActionText(actionText: String): RemindBarLayout {
        this.actionText = actionText
        return this
    }

    fun setMarginBottom(px: Int): RemindBarLayout {
        val params = remindBarLayout.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin = px
        return this
    }

    fun setDuration(duration: Int): RemindBarLayout {
        this.duration = duration
        return this
    }


    fun setAction(text: CharSequence, listener: View.OnClickListener?): RemindBarLayout {
        if (TextUtils.isEmpty(text) || listener == null) {
            tvAction.visibility = View.GONE
            tvAction.setOnClickListener(null)
        } else {
            tvAction.visibility = View.VISIBLE
            tvAction.text = text
            tvAction.setOnClickListener { view ->
                listener.onClick(view)
                // Now dismiss the Snackbar
                remove()
            }
        }
        return this
    }


    fun show() {
        //-1 表示没有添加过
        val index = parent!!.indexOfChild(remindBarLayout)
        if (index != -1) {
            parent.removeView(remindBarLayout)
            handler.removeCallbacksAndMessages(null)
        }
        tvMsg.text = msgText
        parent.addView(remindBarLayout)
        handler.sendEmptyMessageDelayed(KEY_REMOVE_VIEW, duration.toLong())
    }


    fun remove() {
        val index = parent!!.indexOfChild(remindBarLayout)
        if (index != -1) {
            parent.removeView(remindBarLayout)
            handler.removeCallbacksAndMessages(null)
        }
    }


    private class RemindBarHandler  constructor(private val remindBarLayout: RemindBarLayout) : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == KEY_REMOVE_VIEW) {
                remindBarLayout.remove()
                this.removeCallbacksAndMessages(null)
            }
        }
    }

    companion object {

        private const val KEY_REMOVE_VIEW = 10

        private fun findSuitableParent(view: View?): ViewGroup? {
            var view = view
            var fallback: ViewGroup? = null
            do {
                if (view is FrameLayout) {
                    if (view.id == android.R.id.content) {
                        // If we've hit the decor content view, then we didn't find a CoL in the
                        // hierarchy, so use it.
                        return view
                    } else {
                        // It's not the content view but we'll use it as our fallback
                        fallback = view
                    }
                }

                if (view != null) {
                    // Else, we will loop and crawl up the view hierarchy and try to find a parent
                    val parent = view.parent
                    view = if (parent is View) parent else null
                }
            } while (view != null)

            // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
            return fallback
        }
    }
}
