package com.example.weizhenbin.wangebug.base

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.View

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.weizhenbin.wangebug.views.emptyview.EmptyView
import com.example.weizhenbin.wangebug.views.emptyview.IEmptyViewProxy

/**
 * Created by weizhenbin on 2018/9/27.
 * JvmOverloads 生成多个参数重载方法
 */

abstract class BaseSimpleAdapter<T, K : BaseViewHolder> @JvmOverloads constructor(context: Context?, @LayoutRes layoutResId: Int, data: List<T>? = null) : BaseQuickAdapter<T, K>(layoutResId, data), IEmptyViewProxy {
    private val emptyView: EmptyView?

    init {
        mContext = context
        emptyView = EmptyView(context)
        setEmptyView(emptyView)
    }

    constructor(context: Context?, data: List<T>?) : this(context, 0, data) {}


    override fun setEmptyText(text: String) {
        emptyView?.setEmptyText(text)
    }

    override fun setActionText(text: String) {
        emptyView?.setActionText(text)
    }

    override fun setAction(clickListener: View.OnClickListener) {
        emptyView?.setAction(clickListener)

    }

    override fun setEventVisibility(visibility: Int) {
        emptyView?.setEventVisibility(visibility)
    }

    override fun loading() {
        loading(false)
    }

    override fun loading(isShowProgressBar: Boolean) {
        emptyView?.loading(isShowProgressBar)
    }

    override fun emptyData() {
        emptyData(true)
    }


    override fun emptyData(showAction: Boolean) {
        emptyView?.emptyData(showAction)
    }

    override fun setProgressBarVisibility(visibility: Int) {
        emptyView?.setProgressBarVisibility(visibility)
    }
}
