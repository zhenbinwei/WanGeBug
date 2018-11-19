package com.example.weizhenbin.wangebug.net.retrofit

import android.text.TextUtils
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.CodeApi
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.NewsApi
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.RecreationApi
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.YiYuanApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by weizhenbin on 2018/8/9.
 */

object HttpHelper  {

    private var baseUrl = ""


    fun setBaseUrl(baseUrl: String): HttpHelper? {
        this.baseUrl = baseUrl
        return this@HttpHelper
    }


    fun <T> getApi(t: Class<T>): T {

        if (t == CodeApi::class.java) {
            baseUrl = CodeApi.BASE_URL
        } else if (t == NewsApi::class.java) {
            baseUrl = NewsApi.BASE_URL
        } else if (t == RecreationApi::class.java) {
            baseUrl = RecreationApi.BASE_URL
        } else if (t == YiYuanApi::class.java) {
            baseUrl = YiYuanApi.BASE_URL
        }
        if (TextUtils.isEmpty(baseUrl)) {
            throw NullPointerException("baseUrl == null")
        }
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl) //http://fy.iciba.com/
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        return retrofit.create(t)
    }

   /* companion object {


        *//*private var httpHelper: HttpHelper? = null


        fun getHttpHelper(): HttpHelper {
            if (httpHelper == null) {
                synchronized(HttpHelper::class.java) {
                    if (httpHelper == null) {
                        httpHelper = HttpHelper()
                    }
                }
            }
            return httpHelper
        }*//*


    }*/
    fun <T> setThread(): Observable.Transformer<T, T> {
        return Observable.Transformer { tObservable -> tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }

}
