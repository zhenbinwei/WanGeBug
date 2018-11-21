package com.example.weizhenbin.wangebug.views

import android.content.Context
import android.view.Gravity
import android.view.View
import com.example.weizhenbin.wangebug.tools.PhoneTool
import com.example.weizhenbin.wangebug.tools.TouchTool

class ListShortcutActionLayout private constructor(builder: Builder?) {
    internal var listPopupWindow: ListPopupWindow
    private val context: Context?

    private val anchor: View?

    private val items: Array<String>?

    init {
        if (builder == null) {
            throw NullPointerException("builder 不能为空")
        }
        this.context = builder.context
        this.anchor = builder.anchor
        this.items = builder.items
        if (context == null) {
            throw NullPointerException("context 不能为空")
        }
        if (items == null) {
            throw NullPointerException("items 不能为空")
        }
        listPopupWindow = ListPopupWindow(context, items)
        listPopupWindow.setOnDismissListener {
            if (anchor != null) {
                anchor.isSelected = false
            }
        }
        listPopupWindow.setItemListener(builder.iItemListener)
    }


    fun show() {
        if (anchor == null) {
            throw NullPointerException("anchor 不能为空")
        }
        anchor.isSelected = true
        if (anchor.parent != null) {
            anchor.parent.requestDisallowInterceptTouchEvent(true)
        }

        val isLeft = TouchTool.downX < PhoneTool.screenWidth / 2
        val isTop = TouchTool.downY > listPopupWindow.height
        val x = if (isLeft) TouchTool.downX.toInt() else TouchTool.downX.toInt() - listPopupWindow.width
        val y = if (isTop) TouchTool.downY.toInt() - listPopupWindow.height else TouchTool.downY.toInt()
        listPopupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY,
                x,
                y)
    }


    class Builder( val context: Context) {

         var anchor: View? = null

         var items: Array<String>? = null

         var iItemListener: ((which: Int)->Unit)? = null

        fun setAnchor(anchor: View): Builder {
            this.anchor = anchor
            return this
        }

        fun setItems(items: Array<String>): Builder {
            this.items = items
            return this
        }

        fun setiItemListener(iItemListener: ((which: Int)->Unit)? ): Builder {
            this.iItemListener = iItemListener
            return this
        }

        fun build(): ListShortcutActionLayout {
            return ListShortcutActionLayout(this)
        }
    }


}
