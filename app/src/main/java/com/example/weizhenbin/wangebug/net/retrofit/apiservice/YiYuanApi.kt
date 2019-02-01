package com.example.weizhenbin.wangebug.net.retrofit.apiservice

import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanBSBDJBean
import retrofit2.http.GET
import retrofit2.http.QueryMap
import rx.Observable

/**
 * Created by weizhenbin on 2018/8/17.
 */

interface YiYuanApi {


    @GET("255-1")
    fun getData(@QueryMap hashMap: MutableMap<String, String>): Observable<YiYuanBSBDJBean>

    companion object {
        val BASE_URL = "http://route.showapi.com/"
        val sign = "76eb1ae9e37e4873961bc6ec1fb11f10"
        val clienId = "72879"
    }


}
