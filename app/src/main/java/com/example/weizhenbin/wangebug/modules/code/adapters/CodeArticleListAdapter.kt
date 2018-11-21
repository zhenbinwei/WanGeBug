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
import com.example.weizhenbin.wangebug.base.BaseMultipleAdapter
import com.example.weizhenbin.wangebug.base.UrlTypeEnum
import com.example.weizhenbin.wangebug.base.WebActivity
import com.example.weizhenbin.wangebug.image.DefImageConfig
import com.example.weizhenbin.wangebug.image.ImageLoader
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean.DataBean.DatasBean.Companion.HAS_PIC
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean.DataBean.DatasBean.Companion.NO_PIC

/**
 * Created by weizhenbin on 18/8/26.
 */

class CodeArticleListAdapter
/**
 * Same as QuickAdapter#QuickAdapter(Context,int) but with
 * some initialization data.
 *
 * @param data A new list is created out of this one to avoid mutable list
 */
(private val context: Context, data: List<ArticleListDataBean.DataBean.DatasBean>) : BaseMultipleAdapter<ArticleListDataBean.DataBean.DatasBean, BaseViewHolder>(context, data) {

    init {
        addItemType(HAS_PIC, R.layout.code_article_list_pic_item)
        addItemType(NO_PIC, R.layout.code_article_list_item)
        onItemChildClickListener = OnItemChildClickListener { adapter, view, position ->
            val datasBean = data[position]
            WebActivity.startActivity(context, datasBean.link!!, datasBean.title, UrlTypeEnum.CODE)
        }
    }

    override fun convert(helper: BaseViewHolder, item: ArticleListDataBean.DataBean.DatasBean) {
        when (item.itemType) {
            HAS_PIC -> {
                helper.setText(R.id.tv_author, item.author)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    helper.setText(R.id.tv_title, Html.fromHtml(item.title, FROM_HTML_MODE_COMPACT))
                } else {
                    helper.setText(R.id.tv_title, Html.fromHtml(item.title))
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    helper.setText(R.id.tv_chapterName, Html.fromHtml(item.chapterName, FROM_HTML_MODE_COMPACT))
                } else {
                    helper.setText(R.id.tv_chapterName, Html.fromHtml(item.chapterName))
                }
                helper.setText(R.id.tv_niceDate, item.niceDate)
                val imageView = helper.getView<ImageView>(R.id.iv_envelopePic)
                if (TextUtils.isEmpty(item.envelopePic)) {
                    imageView.visibility = View.GONE
                } else {
                    imageView.visibility = View.VISIBLE
                    ImageLoader.getImageLoader().imageLoader(context, imageView, item.envelopePic!!, DefImageConfig.smallImage())
                }
                helper.addOnClickListener(R.id.ll_item)
                helper.addOnLongClickListener(R.id.ll_item)
            }
            NO_PIC -> {
                helper.setText(R.id.tv_author, item.author)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    helper.setText(R.id.tv_title, Html.fromHtml(item.title, FROM_HTML_MODE_COMPACT))
                } else {
                    helper.setText(R.id.tv_title, Html.fromHtml(item.title))
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    helper.setText(R.id.tv_chapterName, Html.fromHtml(item.chapterName, FROM_HTML_MODE_COMPACT))
                } else {
                    helper.setText(R.id.tv_chapterName, Html.fromHtml(item.chapterName))
                }
                helper.setText(R.id.tv_niceDate, item.niceDate)
                helper.addOnClickListener(R.id.ll_item)
                helper.addOnLongClickListener(R.id.ll_item)
            }
        }
    }
}
