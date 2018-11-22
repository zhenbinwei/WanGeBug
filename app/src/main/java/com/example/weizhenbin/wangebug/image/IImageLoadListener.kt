package com.example.weizhenbin.wangebug.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

/**
 * Created by weizhenbin on 2018/8/15.
 */

interface IImageLoadListener {

    fun onLoadStart(url: String, defDrawable: Drawable?)

    fun onLoadError(url: String, errorDrawable: Drawable?)

    fun onLoadSuccess(bitmap: Bitmap, url: String)

}
