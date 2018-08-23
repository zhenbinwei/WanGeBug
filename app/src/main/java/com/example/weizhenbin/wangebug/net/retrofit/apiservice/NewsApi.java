package com.example.weizhenbin.wangebug.net.retrofit.apiservice;

import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean;

import java.util.HashMap;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by weizhenbin on 2018/8/9.
 */

public interface NewsApi {

    String BASE_URL="http://ali-news.showapi.com/";
    String AppCode="d3101bc0f9604ec99d11aa2274985ef7";


    @Headers("Authorization: APPCODE "+AppCode)
    @GET("/newsList")
    Observable<YiYuanNewsBean> getNewsList(@QueryMap HashMap<String,String> hashMap);

}
