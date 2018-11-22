package com.example.weizhenbin.wangebug.modules.news.adapters

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseMultipleAdapter
import com.example.weizhenbin.wangebug.base.UrlTypeEnum
import com.example.weizhenbin.wangebug.base.WebActivity
import com.example.weizhenbin.wangebug.image.DefImageConfig
import com.example.weizhenbin.wangebug.image.ImageLoader
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.Companion.MULTI_PIC
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.Companion.NO_PIC
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.Companion.SINGLE_PIC
import com.example.weizhenbin.wangebug.tools.PhoneTool
import com.example.weizhenbin.wangebug.views.RatioImageView

/**
 * Created by weizhenbin on 2018/8/23.
 */

class NewsListAdapter
/**
 * Same as QuickAdapter#QuickAdapter(Context,int) but with
 * some initialization data.
 *
 * @param data A new list is created out of this one to avoid mutable list
 */
(context: Context, data: List<YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>) : BaseMultipleAdapter<YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean, BaseViewHolder>(context, data) {

    init {
        addItemType(SINGLE_PIC, R.layout.news_single_pic_item)
        addItemType(MULTI_PIC, R.layout.news_multi_pic_item)
        addItemType(NO_PIC, R.layout.news_not_pic_item)
        onItemChildClickListener = OnItemChildClickListener { adapter, view, position ->
            val contentlistBean = data[position]

            contentlistBean.link?.let {
                WebActivity.startActivity(context,it, contentlistBean.title, UrlTypeEnum.NEWS)
            }
        }
    }

    override fun convert(helper: BaseViewHolder, item: YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean) {
        when (item.itemType) {
            SINGLE_PIC -> {
                helper.setText(R.id.tv_title, item.title)
                helper.setText(R.id.tv_channelName, item.channelName)
                helper.setText(R.id.tv_pub_time, item.pubDate)
                val ratioImageView = helper.getView<RatioImageView>(R.id.iv_pic)
               val url= if (item.imageurls != null && item.imageurls!!.size > 0) {
                     item.imageurls!![0].url
                } else {
                     null
                }
                if (TextUtils.isEmpty(url)) {
                    ratioImageView.visibility = View.GONE
                } else {
                    ratioImageView.visibility = View.VISIBLE
                    ImageLoader.getImageLoader().imageLoader(mContext, ratioImageView, url!!, DefImageConfig.smallImage())
                }
                helper.addOnClickListener(R.id.ll_item)
            }
            MULTI_PIC -> {
                helper.setText(R.id.tv_title, item.title)
                helper.setText(R.id.tv_channelName, item.channelName)
                helper.setText(R.id.tv_pub_time, item.pubDate)
                val linearLayout = helper.getView<LinearLayout>(R.id.ll_images)
                linearLayout.removeAllViews()
                if (item.imageurls != null) {
                    linearLayout.visibility = View.VISIBLE
                    val size = item.imageurls!!.size
                    for (i in 0 until size) {
                        if (i < 3) {
                            val url2 = item.imageurls!![i].url
                            val imageView = RatioImageView(mContext)
                            imageView.setRatio(0.7f)
                            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                            val layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT)
                            layoutParams.weight = 1f
                            if (size < 3) {
                                if (i < 1) {
                                    layoutParams.rightMargin = PhoneTool.dip2px(8f)
                                }
                            } else {
                                if (i < 2) {
                                    layoutParams.rightMargin = PhoneTool.dip2px(8f)
                                }
                            }
                            ImageLoader.getImageLoader().imageLoader(mContext, imageView, url2!!, DefImageConfig.smallImage())
                            linearLayout.addView(imageView, layoutParams)
                        }
                    }

                } else {
                    linearLayout.visibility = View.GONE
                }
                helper.addOnClickListener(R.id.ll_item)
            }
            NO_PIC -> {
                helper.setText(R.id.tv_title, item.title)
                helper.setText(R.id.tv_channelName, item.channelName)
                helper.setText(R.id.tv_pub_time, item.pubDate)
                helper.addOnClickListener(R.id.ll_item)
            }
        }
    }
}
