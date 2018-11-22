package com.example.weizhenbin.wangebug.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

/**
 * Created by weizhenbin on 2018/8/15.
 */

open class ImageLoadListenerAdapter : IImageLoadListener {


    override fun onLoadStart(url: String, defDrawable: Drawable?) {

    }

    override fun onLoadError(url: String, errorDrawable: Drawable?) {

    }

    override fun onLoadSuccess(bitmap: Bitmap, url: String) {

    }
}
