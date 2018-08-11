package com.example.weizhenbin.wangebug.net.retrofit.apiservice;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by weizhenbin on 2018/8/9.
 */

public interface CodeApi {

    String BASE_URL="http://www.wanandroid.com/";

    @GET("article/list/{page}/json")
    Observable<String> getArticleList(@Path("page") String page);
}
