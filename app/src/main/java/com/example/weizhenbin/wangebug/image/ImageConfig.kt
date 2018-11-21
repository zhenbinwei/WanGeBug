package com.example.weizhenbin.wangebug.image


import android.graphics.drawable.Drawable

import com.example.weizhenbin.wangebug.tools.PhoneTool

/**
 * Created by weizhenbin on 2018/8/15.
 */

class ImageConfig private constructor(builder: Builder) {

    var width = PhoneTool.screenWidth

    var height = PhoneTool.screenHeight

    val defDrawable: Drawable? //默认图
    val errorDrawable: Drawable? //加载失败时错误图
    val defIconResId: Int
    val errorIconResId: Int


    val isGif: Boolean


    init {
        this.width = builder.width
        this.height = builder.height
        this.defDrawable = builder.defDrawable
        this.defIconResId = builder.defIconResId
        this.errorDrawable = builder.errorDrawable
        this.errorIconResId = builder.errorIconResId
        this.isGif = builder.isGif
    }

    class Builder {
         var width = PhoneTool.screenWidth

         var height = PhoneTool.screenHeight

         var defDrawable: Drawable? = null //默认图
         var errorDrawable: Drawable? = null //加载失败时错误图
         var defIconResId: Int = 0
         var errorIconResId: Int = 0

         var isGif: Boolean = false
        fun setWidth(width: Int): Builder {
            this.width = width
            return this
        }

        fun setHeight(height: Int): Builder {
            this.height = height
            return this
        }

        fun setDefDrawable(defDrawable: Drawable): Builder {
            this.defDrawable = defDrawable
            return this
        }

        fun setErrorDrawable(errorDrawable: Drawable): Builder {
            this.errorDrawable = errorDrawable
            return this
        }

        fun setDefIconResId(defIconResId: Int): Builder {
            this.defIconResId = defIconResId
            return this
        }

        fun setErrorIconResId(errorIconResId: Int): Builder {
            this.errorIconResId = errorIconResId
            return this
        }

        fun setGif(gif: Boolean): Builder {
            isGif = gif
            return this
        }

        fun build(): ImageConfig {
            return ImageConfig(this)
        }
    }
}
