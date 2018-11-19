package com.example.weizhenbin.wangebug.base

import android.content.Context
import android.view.View

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.example.weizhenbin.wangebug.views.emptyview.EmptyView
import com.example.weizhenbin.wangebug.views.emptyview.IEmptyViewProxy

/**
 * Created by weizhenbin on 2018/9/27.
 */

abstract class BaseMultipleAdapter<T : MultiItemEntity, K : BaseViewHolder>
/**
 * Same as QuickAdapter#QuickAdapter(Context,int) but with
 * some initialization data.
 *
 * @param data A new list is created out of this one to avoid mutable list
 */
(context: Context?, data: List<T>) : BaseMultiItemQuickAdapter<T, K>(data), IEmptyViewProxy {

    private val emptyView: EmptyView?

    init {
        mContext = context
        emptyView = EmptyView(context)
        setEmptyView(emptyView)
    }

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
