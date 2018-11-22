package com.example.weizhenbin.wangebug.modules.code.adapters

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter
import com.example.weizhenbin.wangebug.base.UrlTypeEnum
import com.example.weizhenbin.wangebug.base.WebActivity
import com.example.weizhenbin.wangebug.image.DefImageConfig
import com.example.weizhenbin.wangebug.image.ImageLoader
import com.example.weizhenbin.wangebug.modules.code.entity.ProjectListDataBean

/**
 * Created by weizhenbin on 2018/8/28.
 */

class CodeProjectListAdapter(private val context: Context, data: List<ProjectListDataBean.DataBean.DatasBean>?) : BaseSimpleAdapter<ProjectListDataBean.DataBean.DatasBean, BaseViewHolder>(context, R.layout.project_list_item, data) {

    init {
        onItemChildClickListener = OnItemChildClickListener { adapter, view, position ->
            if (data != null) {
                val datasBean = data[position]
                WebActivity.startActivity(context, datasBean.link!!, datasBean.title, UrlTypeEnum.CODE)
                // WebActivity.requestPermission(context,"https://github.com/caiyonglong/Notepad/issues");
            }
        }
    }

    override fun convert(helper: BaseViewHolder, item: ProjectListDataBean.DataBean.DatasBean) {
        val imageView = helper.getView<ImageView>(R.id.iv_pic)
        if (TextUtils.isEmpty(item.envelopePic)) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            ImageLoader.getImageLoader().imageLoader(context, imageView, item.envelopePic!!, DefImageConfig.smallImageLong())
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            helper.setText(R.id.tv_title, Html.fromHtml(item.title, FROM_HTML_MODE_COMPACT))
            helper.setText(R.id.tv_desc, Html.fromHtml(item.desc, FROM_HTML_MODE_COMPACT))
        } else {
            helper.setText(R.id.tv_title, Html.fromHtml(item.title))
            helper.setText(R.id.tv_desc, Html.fromHtml(item.desc))
        }
        helper.setText(R.id.tv_chapterName, item.chapterName)
        helper.setText(R.id.tv_niceDate, item.niceDate)
        helper.addOnClickListener(R.id.ll_item)

    }
}
