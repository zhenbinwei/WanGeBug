package com.example.weizhenbin.wangebug.modules.code.adapters

import android.content.Context
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter
import com.example.weizhenbin.wangebug.modules.code.entity.TBSearchHistoryKeyBean

/**
 * Created by weizhenbin on 2018/10/31.
 */
class CodeSearchHistoryAdapter(context: Context?, data: List<TBSearchHistoryKeyBean>?) : BaseSimpleAdapter<TBSearchHistoryKeyBean, BaseViewHolder>(context, R.layout.search_history_list_item, data) {

     var iDataCallback: IDataCallback? = null

    init {
        onItemChildClickListener = OnItemChildClickListener { adapter, view, position ->
            if (iDataCallback != null) {
                iDataCallback!!.onClickItem(position)
            }
        }
    }



    override fun convert(helper: BaseViewHolder, item: TBSearchHistoryKeyBean) {
        helper.setText(R.id.tv_history, item.key)
        helper.addOnClickListener(R.id.ll_item)
        helper.getView<View>(R.id.iv_del).setOnClickListener {
            if (iDataCallback != null) {
                iDataCallback!!.onDelItem(helper.layoutPosition)
            }
        }
    }


    interface IDataCallback {
        fun onClickItem(position: Int)
        fun onDelItem(position: Int)
    }
}
