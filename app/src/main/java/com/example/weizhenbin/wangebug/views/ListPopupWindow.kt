package com.example.weizhenbin.wangebug.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.tools.PhoneTool

/**
 * Created by weizhenbin on 2018/10/10.
 */
class ListPopupWindow(context: Context, items: Array<String>) : PopupWindow() {


    private var listView: ListView? = null
    private val arrayAdapter: ArrayAdapter<*>
    private var listener: ((which: Int)->Unit)? = null

    init {
        val view = View.inflate(context, R.layout.list_popupwindow_layout, null)
        initViews(view)
        contentView = view
        arrayAdapter = ArrayAdapter(context, R.layout.list_popupwindow_item, items)
        listView!!.adapter = arrayAdapter
        width = PhoneTool.dip2px(120f)
        height = WindowManager.LayoutParams.WRAP_CONTENT
        isFocusable = true
        isTouchable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        listView!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            dismiss()
            if (listener != null) {
                listener!!(position)
            }
        }
        setTouchInterceptor { v, event ->
            Log.d("ListPopupWindow", "onTouch")
            false
        }
    }


    override fun getHeight(): Int {
        return arrayAdapter.count * PhoneTool.dip2px(44f)
    }

    private fun initViews(view: View) {
        listView = view.findViewById(R.id.lv)
    }

    override fun showAsDropDown(anchor: View) {
        super.showAsDropDown(anchor)
    }

    interface IItemListener {
        fun onItemClick(which: Int)
    }

    fun setItemListener(listener: ((which: Int)->Unit)?): ListPopupWindow {
        this.listener = listener
        return this
    }
}
