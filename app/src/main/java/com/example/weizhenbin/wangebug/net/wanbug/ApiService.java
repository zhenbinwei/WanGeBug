package com.example.weizhenbin.wangebug.net.wanbug;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by weizhenbin on 2018/8/8.
 */

public interface ApiService {
    @GET()
    Observable<String> getWanandroid(@Url String Url);
}
