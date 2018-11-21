package com.example.weizhenbin.wangebug.image

/**
 * Created by weizhenbin on 18/8/26.
 */

object DefImageConfig {

    fun smallImage(): ImageConfig {
        return ImageConfig.Builder().setWidth(480).setHeight(480).setGif(false).build()
    }

    fun smallImageLong(): ImageConfig {
        return ImageConfig.Builder().setWidth(640).setHeight(480).setGif(false).build()
    }
}
