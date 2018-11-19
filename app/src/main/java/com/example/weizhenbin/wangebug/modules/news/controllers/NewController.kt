package com.example.weizhenbin.wangebug.modules.news.controllers

import com.example.weizhenbin.wangebug.base.DataResult
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsChannelBean
import com.example.weizhenbin.wangebug.net.retrofit.HttpHelper
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.NewsApi
import rx.Observer
import java.util.*

/**
 * Created by weizhenbin on 2018/8/22.
 */

object NewController {
    fun getNewsListData(page: Int, channelId: String, dataResult: DataResult<YiYuanNewsBean>?) {
        dataResult?.onStart()
        val hashMap:MutableMap<String,String> = HashMap()
        hashMap["channelId"] = channelId
        hashMap["channelName"] = ""
        hashMap["maxResult"] = "20"
        hashMap["needAllList"] = "0"
        hashMap["needContent"] = "0"
        hashMap["needHtml"] = "1"
        hashMap["page"] = page.toString() + ""
        HttpHelper.getApi(NewsApi::class.java).getNewsList(hashMap).compose(HttpHelper.setThread<YiYuanNewsBean>()).subscribe(object : Observer<YiYuanNewsBean> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                dataResult?.onError(e)
            }

            override fun onNext(s: YiYuanNewsBean) {
                dataResult?.onSuccess(s)
            }
        })
    }

    fun getNewsChannelData(dataResult: DataResult<YiYuanNewsChannelBean>?) {
        dataResult?.onStart()
        HttpHelper.getApi(NewsApi::class.java).newsChannel.compose(HttpHelper.setThread()).subscribe(object : Observer<YiYuanNewsChannelBean> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                dataResult?.onError(e)
            }

            override fun onNext(s: YiYuanNewsChannelBean) {
                dataResult?.onSuccess(s)
            }
        })
    }


}
