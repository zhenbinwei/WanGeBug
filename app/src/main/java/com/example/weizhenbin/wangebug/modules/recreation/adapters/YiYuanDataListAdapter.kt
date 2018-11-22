package com.example.weizhenbin.wangebug.modules.recreation.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
import com.chad.library.adapter.base.BaseViewHolder
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter
import com.example.weizhenbin.wangebug.image.ImageConfig
import com.example.weizhenbin.wangebug.image.ImageLoader
import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanPicBean
import com.example.weizhenbin.wangebug.modules.recreation.uis.PicBrowserActivity
import java.util.*

/**
 * Created by weizhenbin on 2018/8/17.
 */

class YiYuanDataListAdapter(context: Context, @LayoutRes layoutResId: Int, data: List<YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>?) : BaseSimpleAdapter<YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean, BaseViewHolder>(context, layoutResId, data) {
    private  val imageConfig: ImageConfig


    private val picList: ArrayList<String>
        get() {
            val pics = ArrayList<String>()
            val size = data.size
            for (i in 0 until size) {
                pics.add(data[i].image0!!)
            }
            return pics
        }

    init {
        imageConfig = ImageConfig.Builder().setHeight(600).setWidth(600).build()
        onItemChildClickListener = OnItemChildClickListener { _, _, position -> PicBrowserActivity.startBrowserActivity(context, picList, position) }
    }

    override fun convert(helper: BaseViewHolder, item: YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean) {
        val ivPic = helper.getView<ImageView>(R.id.iv_pic)
        ImageLoader.getImageLoader().imageLoader(mContext, ivPic, item.image0!!, imageConfig)

        val text = item.text?.replace("\n", "")?.trim { it <= ' ' }

        helper.setText(R.id.tv_title, text)
        helper.addOnClickListener(R.id.iv_pic)
    }


}
