package com.example.weizhenbin.wangebug.net.retrofit.apiservice;

import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanPicBean;

import java.util.HashMap;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by weizhenbin on 2018/8/17.
 */

public interface YiYuanApi {
    String BASE_URL="http://route.showapi.com/";
    String sign="76eb1ae9e37e4873961bc6ec1fb11f10";
    String clienId="72879";


    @GET("255-1")
    Observable<YiYuanPicBean> getData(@QueryMap HashMap<String,String> hashMap);


}
