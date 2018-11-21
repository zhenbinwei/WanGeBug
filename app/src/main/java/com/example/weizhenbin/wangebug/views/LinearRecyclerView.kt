package com.example.weizhenbin.wangebug.views

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.example.weizhenbin.wangebug.R

/**
 * Created by weizhenbin on 2018/11/1.
 */
class LinearRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : RecyclerView(context, attrs, defStyle) {

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        val drawable = resources.getDrawable(R.drawable.divider_line)
        itemDecoration.setDrawable(drawable)
        addItemDecoration(itemDecoration)
    }

}
