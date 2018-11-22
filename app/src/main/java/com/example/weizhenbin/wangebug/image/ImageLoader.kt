package com.example.weizhenbin.wangebug.image

import android.content.Context
import android.widget.ImageView

/**
 * Created by weizhenbin on 2018/8/15.
 */

class ImageLoader private constructor() : IImageLoader {


    private var realImageLoader: IImageLoader? = null

    fun init(iImageLoader: IImageLoader?) {
        if (iImageLoader == null) {
            throw NullPointerException("iImageLoader 不能为空")
        }
        this.realImageLoader = iImageLoader
    }


    override fun imageLoader(context: Context, imageView: ImageView, url: String) {
        checkNull()
        realImageLoader!!.imageLoader(context, imageView, url)
    }

    override fun imageLoader(context: Context, imageView: ImageView, url: String, imageConfig: ImageConfig?) {
        checkNull()
        realImageLoader!!.imageLoader(context, imageView, url, imageConfig)
    }

    override fun imageLoader(context: Context, imageView: ImageView, url: String, iImageLoadListener: IImageLoadListener?) {
        checkNull()
        realImageLoader!!.imageLoader(context, imageView, url, iImageLoadListener)
    }

    override fun imageLoader(context: Context, imageView: ImageView, url: String, imageConfig: ImageConfig?, iImageLoadListener: IImageLoadListener?) {
        checkNull()
        realImageLoader!!.imageLoader(context, imageView, url, imageConfig, iImageLoadListener)
    }

    override fun loadBitmap(context: Context, url: String, iImageLoadListener: IImageLoadListener?) {
        checkNull()
        realImageLoader!!.loadBitmap(context, url, iImageLoadListener)
    }

    private fun checkNull() {
        if (realImageLoader == null) {
            throw NullPointerException("没有调用init")
        }
    }


    private object SingletonHolder{
        val holder=ImageLoader()
    }



    companion object {

        fun getImageLoader()=SingletonHolder.holder
    }




}
