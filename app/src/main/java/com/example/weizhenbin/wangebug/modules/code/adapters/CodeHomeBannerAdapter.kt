package com.example.weizhenbin.wangebug.modules.code.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.weizhenbin.wangebug.base.UrlTypeEnum
import com.example.weizhenbin.wangebug.base.WebActivity
import com.example.weizhenbin.wangebug.image.DefImageConfig
import com.example.weizhenbin.wangebug.image.ImageLoader
import com.example.weizhenbin.wangebug.modules.code.entity.BannerDataBean
import com.example.weizhenbin.wangebug.views.autoscrolllayout.AutoScrollerAdapter
import java.util.*

/**
 * Created by weizhenbin on 2018/8/27.
 */

class CodeHomeBannerAdapter(private val context: Context, private val data: List<BannerDataBean.DataBean>?) : AutoScrollerAdapter() {
    private val imageViews = ArrayList<ImageView>()

    init {
        initViews(data)
    }

    private fun initViews(data: List<BannerDataBean.DataBean>?) {
        if (data == null || data.isEmpty()) {
            return
        }
        imageViews.clear()
        for (i in data.indices) {
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageViews.add(imageView)
        }
    }


    override fun readCount(): Int {
        return data?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var imageView: ImageView? = null
        if (readCount() > 0) {
            val newPosition = position % readCount()
            imageView = imageViews[newPosition]
            ImageLoader.getImageLoader().imageLoader(container.context, imageView, data!![newPosition].imagePath!!, DefImageConfig.smallImage())
           // Glide.with(context).asBitmap().load(data!![newPosition].imagePath).into(imageView)
            imageView.setOnClickListener {
                val dataBean = data[newPosition]
                WebActivity.startActivity(container.context, dataBean.url!!, dataBean.title, UrlTypeEnum.CODE)
            }
        }
        if (imageView == null) {
            imageView = ImageView(context)
        }
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


}
