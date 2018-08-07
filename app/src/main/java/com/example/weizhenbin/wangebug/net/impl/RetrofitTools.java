package com.example.weizhenbin.wangebug.net.impl;

import android.util.Log;

import com.example.weizhenbin.wangebug.fragments.NewsFragment;
import com.example.weizhenbin.wangebug.net.interfaces.IRequest;
import com.example.weizhenbin.wangebug.net.interfaces.IResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by weizhenbin on 2018/8/7.
 */

public class RetrofitTools<T> implements IRequest<T> {



    @Override
    public void get(String url, HashMap<String, String> param, final IResult<T> iResult) {
        //步骤4:创建Retrofit对象
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
                        Log.d("RetrofitTools", book);

                        if (iResult!=null){
                            ParameterizedType type = (ParameterizedType) iResult.getClass()
                                    .getGenericSuperclass();
                            Log.d("RetrofitTools", "type.getActualTypeArguments()[0]:" + type.getActualTypeArguments()[0]);
                        }
                       // NewsFragment.Translation vo = JSON.parseObject(book, NewsFragment.Translation.class);
                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(String.class, new JsonSerializer() {

                                    @Override
                                    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
                                        Log.d("RetrofitTools", "src:" + src);
                                        return null;
                                    }
                                })
                                .create();
                        NewsFragment.Translation translation=  gson.fromJson(book, NewsFragment.Translation.class);
                        translation.show();
                        //vo.show();
                    }
                });}

    @Override
    public void post(String url, HashMap<String, String> param, IResult<T> iResult) {

    }
}
