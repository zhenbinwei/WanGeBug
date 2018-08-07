package com.example.weizhenbin.wangebug.net.impl;

import android.util.Log;

import com.example.weizhenbin.wangebug.fragments.NewsFragment;
import com.example.weizhenbin.wangebug.net.interfaces.IHttpProcessor;
import com.example.weizhenbin.wangebug.net.interfaces.IResult;
import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by weizhenbin on 2018/8/7.
 */

public class RetrofitProcessor  implements IHttpProcessor{
    //真正干活的
    public interface GetRequestInterface {

        // 注解里传入 网络请求 的部分URL地址
        // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
        // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
        // getCall()是接受网络请求数据的方法

        @GET("ajax.php")
        Observable<String> getCall(@QueryMap HashMap<String,String> hashMap);
    }
    @Override
    public void get(String url, HashMap<String, String> param, final IResult iResult) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") //http://fy.iciba.com/
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        // 步骤5:创建 网络请求接口 的实例
        NewsFragment.GetRequestInterface request = retrofit.create(NewsFragment.GetRequestInterface.class);
        HashMap<String,String> hashMap=new HashMap<>();
        //?a=fy&f=auto&t=auto&w=你好
        hashMap.put("a","fy");
        hashMap.put("f","auto");
        hashMap.put("t","auto");
        hashMap.put("w","你好");
        Observable<String> call = request.getCall(hashMap);

        call.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                .subscribe(new Observer<String>() {//订阅
                    @Override
                    public void onCompleted() {
                        //所有事件都完成，可以做些操作。。。
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace(); //请求过程中发生错误
                    }

                    @Override
                    public void onNext(String book) {//这里的book就是我们请求接口返回的实体类
                        //  iResult.onSuccess(book);
                        Log.d("RetrofitProcessor", book);

                        if (iResult!=null){
                            iResult.onSuccess(book);
                        }

                    }
                });
    }

    @Override
    public void post(String url, HashMap<String, String> param, IResult iResult) {

    }


}
