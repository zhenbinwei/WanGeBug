package com.example.weizhenbin.wangebug.net.retrofit.apiservice

import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsChannelBean
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import rx.Observable

/**
 * Created by weizhenbin on 2018/8/9.
 */

interface NewsApi {


    @get:Headers("Authorization: APPCODE $AppCode")
    @get:GET("/channelList")
    val newsChannel: Observable<YiYuanNewsChannelBean>


    @Headers("Authorization: APPCODE $AppCode")
    @GET("/newsList")
    fun getNewsList(@QueryMap hashMap: MutableMap<String, String>): Observable<YiYuanNewsBean>

    companion object {

       const val BASE_URL = "http://ali-news.showapi.com/"
       const val AppCode = "d3101bc0f9604ec99d11aa2274985ef7"
    }
}
