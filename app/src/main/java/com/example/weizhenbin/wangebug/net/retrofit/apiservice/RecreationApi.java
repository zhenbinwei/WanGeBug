package com.example.weizhenbin.wangebug.net.retrofit.apiservice;

import com.example.weizhenbin.wangebug.modules.recreation.entity.TextJokeBean;
import com.example.weizhenbin.wangebug.net.retrofit.entity.AliBean;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by weizhenbin on 2018/8/9.
 */

public interface RecreationApi {

    String BASE_URL="http://ali-joke.showapi.com";
    String AppCode="d3101bc0f9604ec99d11aa2274985ef7";


    /**
     * time 时间起点，终点时间永远是现在。 格式：yyyy-MM-dd
     * maxResult 每页最大记录数。其值为1至50。
     * page 第几页。
     * */
    @Headers("Authorization: APPCODE "+AppCode)
    @GET("textJoke")
    Observable<AliBean<TextJokeBean>> getTextJoke(@Query("maxResult") String maxResult, @Query("page") String page, @Query("time") String time);


    @GET("picJoke")
    Observable<String> getPicJoke(@Query("maxResult") String maxResult,@Query("page") String page,@Query("time") String time);


    @GET("textJoke")
    Observable<String> getGifJoke(@Query("maxResult") String maxResult,@Query("page") String page);


}
