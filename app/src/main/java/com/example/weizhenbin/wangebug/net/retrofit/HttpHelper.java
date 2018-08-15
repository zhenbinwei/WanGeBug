package com.example.weizhenbin.wangebug.net.retrofit;

import android.text.TextUtils;

import com.example.weizhenbin.wangebug.net.retrofit.apiservice.CodeApi;
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.NewsApi;
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.RecreationApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by weizhenbin on 2018/8/9.
 */

public class HttpHelper {


    private static HttpHelper httpHelper;

    private String baseUrl="";


    private HttpHelper(){

    }


    public static HttpHelper getHttpHelper(){
        if (httpHelper==null){
            synchronized (HttpHelper.class){
                if (httpHelper==null){
                    httpHelper=new HttpHelper();
                }
            }
        }
        return httpHelper;
    }


    public HttpHelper setBaseUrl(String baseUrl){
        this.baseUrl=baseUrl;
        return httpHelper;
    }


    public <T>T getApi(Class<T> t){

            if(t==CodeApi.class){
                baseUrl=CodeApi.BASE_URL;
            }else if (t== NewsApi.class){
                baseUrl=NewsApi.BASE_URL;
            }else if (t== RecreationApi.class){
                baseUrl=RecreationApi.BASE_URL;
            }
        if (TextUtils.isEmpty(baseUrl)){
            throw new NullPointerException("baseUrl == null");
        }
        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) //http://fy.iciba.com/
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
      return  retrofit.create(t);
    }

    public static  <T> Observable.Transformer<T,T> setThread(){
        return new Observable.Transformer<T,T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
