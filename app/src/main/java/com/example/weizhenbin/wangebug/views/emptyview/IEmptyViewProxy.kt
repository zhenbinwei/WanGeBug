package com.example.weizhenbin.wangebug.views.emptyview

import android.view.View

/**
 * Created by weizhenbin on 2018/9/27.
 */

interface IEmptyViewProxy {
    fun setEmptyText(text: String)

    fun setActionText(text: String)

    fun setAction(clickListener: View.OnClickListener?)
    fun setEventVisibility(visibility: Int)

    fun loading()
    fun loading(isShowProgressBar: Boolean)

    fun emptyData()

    fun emptyData(showAction: Boolean)

    fun setProgressBarVisibility(visibility: Int)
}
