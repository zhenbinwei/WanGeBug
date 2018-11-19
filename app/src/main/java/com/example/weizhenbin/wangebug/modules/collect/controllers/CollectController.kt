package com.example.weizhenbin.wangebug.modules.collect.controllers

import android.text.TextUtils
import com.example.weizhenbin.wangebug.base.App
import com.example.weizhenbin.wangebug.modules.collect.entity.TBCollectBean
import com.example.weizhenbin.wangebug.modules.collect.entity.TBCollectBean_

/**
 * Created by weizhenbin on 2018/10/10.
 */
object CollectController {


    fun addCollect(tbCollectBean: TBCollectBean) {
        val tbCollectBeanBox = App.app.boxStore.boxFor(TBCollectBean::class.java)
        tbCollectBeanBox.put(tbCollectBean)
    }


    fun removeCollect(id: Long) {
        val tbCollectBeanBox = App.app.boxStore.boxFor(TBCollectBean::class.java)
        tbCollectBeanBox.remove(id)
    }

    fun removeCollectByTitle(title: String) {
        val tbCollectBeanBox = App.app.boxStore.boxFor(TBCollectBean::class.java)
        tbCollectBeanBox.query().equal(TBCollectBean_.title, title).build().remove()
    }

    fun getCollectList(page: Int, pageCount: Int): List<TBCollectBean> {
        val tbCollectBeanBox = App.app.boxStore.boxFor(TBCollectBean::class.java)
        return tbCollectBeanBox.query().orderDesc(TBCollectBean_.id).build().find(((page - 1) * pageCount).toLong(), pageCount.toLong())
    }


    /**
     * 通过标题查询
     */
    fun isExistByTitle(title: String): Boolean {
        if (TextUtils.isEmpty(title)) {
            return false
        }
        val tbCollectBeanBox = App.app.boxStore.boxFor(TBCollectBean::class.java)
        val tbCollectBean = tbCollectBeanBox.query().equal(TBCollectBean_.title, title).build().findFirst()
        return tbCollectBean != null
    }

}
