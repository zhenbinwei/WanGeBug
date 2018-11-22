package com.example.weizhenbin.wangebug.modules.collect.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildLongClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter
import com.example.weizhenbin.wangebug.base.UrlTypeEnum
import com.example.weizhenbin.wangebug.base.WebActivity
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler
import com.example.weizhenbin.wangebug.eventbus.EventCode
import com.example.weizhenbin.wangebug.eventbus.MessageEvent
import com.example.weizhenbin.wangebug.modules.collect.controllers.CollectController
import com.example.weizhenbin.wangebug.modules.collect.entity.TBCollectBean
import com.example.weizhenbin.wangebug.views.ListShortcutActionLayout
import com.example.weizhenbin.wangebug.views.remindbar.RemindBar
import com.example.weizhenbin.wangebug.views.remindbar.RemindBar.Companion.LENGTH_SHORT

/**
 * Created by weizhenbin on 2018/10/10.
 */
class CollectListAdapter(context: Context?, data: List<TBCollectBean>?) : BaseSimpleAdapter<TBCollectBean, BaseViewHolder>(context, R.layout.collect_list_item, data) {
    init {
        onItemChildClickListener = OnItemChildClickListener { _, _, position ->
            if (data == null) {
                return@OnItemChildClickListener
            }
            val datasBean = data[position]
            datasBean.url?.let {

                WebActivity.startActivity(context, it, datasBean.title, UrlTypeEnum.getValueFromType(datasBean.type))
            }
        }
        onItemChildLongClickListener = OnItemChildLongClickListener { _, view, position ->
            val items = arrayOf(mContext.getString(R.string.del_string), mContext.getString(R.string.copy_link_string))

            ListShortcutActionLayout.Builder(mContext).setAnchor(view).setItems(items).setiItemListener { which ->
                if (data == null) {
                    return@setiItemListener
                }
                val tbCollectBean = data[position]
                when (which) {
                    0 -> {
                        tbCollectBean.title?.let {
                            CollectController.removeCollectByTitle(it)
                            EventBusHandler.post(MessageEvent(EventCode.CANCEL_COLLECT_CODE, it))
                        }

                    }
                    1 -> {
                        tbCollectBean.url?.let {
                            copyLike(view,it)
                        }
                    }
                }
            }.build().show()
            true
        }
    }

    private fun copyLike(v: View, url: String) {
        val clipboardManager = mContext.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", url)
            clipboardManager.primaryClip = clipData
        RemindBar.make(v, mContext.getString(R.string.copy_success_string), LENGTH_SHORT)?.show()
    }

    override fun convert(helper: BaseViewHolder, item: TBCollectBean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            helper.setText(R.id.tv_title, Html.fromHtml(item.title, FROM_HTML_MODE_COMPACT))
            helper.setText(R.id.tv_desc, Html.fromHtml(item.url, FROM_HTML_MODE_COMPACT))
        } else {
            helper.setText(R.id.tv_title, Html.fromHtml(item.title))
            helper.setText(R.id.tv_desc, Html.fromHtml(item.url))
        }
        helper.addOnClickListener(R.id.ll_item)
        helper.addOnLongClickListener(R.id.ll_item)
    }
}
