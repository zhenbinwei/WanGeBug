package com.example.weizhenbin.wangebug.net.retrofit.apiservice

import com.example.weizhenbin.wangebug.modules.recreation.entity.PicJokeBean
import com.example.weizhenbin.wangebug.modules.recreation.entity.TextJokeBean
import com.example.weizhenbin.wangebug.net.retrofit.entity.AliBean

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import rx.Observable

/**
 * Created by weizhenbin on 2018/8/9.
 */

interface RecreationApi {


    /**
     * time 时间起点，终点时间永远是现在。 格式：yyyy-MM-dd
     * maxResult 每页最大记录数。其值为1至50。
     * page 第几页。
     */
    @Headers("Authorization: APPCODE $AppCode")
    @GET("textJoke")
    fun getTextJoke(@Query("maxResult") maxResult: String, @Query("page") page: String, @Query("time") time: String): Observable<AliBean<TextJokeBean>>

    @Headers("Authorization: APPCODE $AppCode")
    @GET("picJoke")
    fun getPicJoke(@Query("maxResult") maxResult: String, @Query("page") page: String, @Query("time") time: String): Observable<AliBean<PicJokeBean>>


    @GET("textJoke")
    fun getGifJoke(@Query("maxResult") maxResult: String, @Query("page") page: String): Observable<String>

    companion object {

       const val BASE_URL = "http://ali-joke.showapi.com"
       const val AppCode = "d3101bc0f9604ec99d11aa2274985ef7"
    }


}
