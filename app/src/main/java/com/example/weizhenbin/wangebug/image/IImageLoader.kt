package com.example.weizhenbin.wangebug.image

import android.content.Context
import android.widget.ImageView

/**
 * Created by weizhenbin on 2018/8/15.
 */

interface IImageLoader {

    fun imageLoader(context: Context, imageView: ImageView, url: String)

    fun imageLoader(context: Context, imageView: ImageView, url: String, imageConfig: ImageConfig?)

    fun imageLoader(context: Context, imageView: ImageView, url: String, iImageLoadListener: IImageLoadListener?)

    fun imageLoader(context: Context, imageView: ImageView, url: String, imageConfig: ImageConfig?, iImageLoadListener: IImageLoadListener?)


    fun loadBitmap(context: Context, url: String, iImageLoadListener: IImageLoadListener?)

}
