package com.example.weizhenbin.wangebug.modules.recreation.controllers

import com.example.weizhenbin.wangebug.base.DataResult
import com.example.weizhenbin.wangebug.modules.recreation.entity.PicJokeBean
import com.example.weizhenbin.wangebug.modules.recreation.entity.TextJokeBean
import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanBSBDJBean
import com.example.weizhenbin.wangebug.net.retrofit.HttpHelper
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.RecreationApi
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.YiYuanApi
import com.example.weizhenbin.wangebug.net.retrofit.entity.AliBean
import rx.Observer

/**
 * Created by weizhenbin on 2018/8/13.
 */

object JokeController {


    /**获取文本笑话
     */
    fun getTextJoke(page: Int, dataResult: DataResult<AliBean<TextJokeBean>>?) {
        dataResult?.onStart()
        HttpHelper
                .getApi(RecreationApi::class.java)
                .getTextJoke("20", "" + page, "")
                .compose(HttpHelper.setThread())
                .subscribe(object : Observer<AliBean<TextJokeBean>> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        dataResult?.onError(e)
                    }

                    override fun onNext(textJokeBeanAliBean: AliBean<TextJokeBean>) {
                        dataResult?.onSuccess(textJokeBeanAliBean)
                    }
                })
    }

    /**获取文本笑话
     */
    fun getPicJoke(page: Int, dataResult: DataResult<AliBean<PicJokeBean>>?) {
        dataResult?.onStart()
        HttpHelper
                .getApi(RecreationApi::class.java)
                .getPicJoke("20", "" + page, "")
                .compose(HttpHelper.setThread())
                .subscribe(object : Observer<AliBean<PicJokeBean>> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        dataResult?.onError(e)
                    }

                    override fun onNext(textJokeBeanAliBean: AliBean<PicJokeBean>) {
                        dataResult?.onSuccess(textJokeBeanAliBean)
                    }
                })
    }

    fun getYiYuanPicData(type:String,page: Int, dataResult: DataResult<YiYuanBSBDJBean>?) {
        dataResult?.onStart()
        val hashMap:MutableMap<String,String> =
                hashMapOf("showapi_appid" to YiYuanApi.clienId,
                        "showapi_sign" to YiYuanApi.sign,
                        "type" to  type,
                        "page" to  page.toString() + "")
        HttpHelper.getApi(YiYuanApi::class.java).getData(hashMap).compose(HttpHelper.setThread()).subscribe(object : Observer<YiYuanBSBDJBean> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                dataResult?.onError(e)
            }

            override fun onNext(s: YiYuanBSBDJBean) {
                dataResult?.onSuccess(s)
            }
        })
    }


}
