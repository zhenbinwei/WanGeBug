package com.example.weizhenbin.wangebug.modules.recreation.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by weizhenbin on 2018/8/13.
 */

open class JokeContentlistBaseBean : MultiItemEntity {


    var type: Int = 0

    override fun getItemType(): Int {

        return type
    }

    companion object {

        val TEXT = 1
        val PIC = 2
        val GIF = 3
    }
}
