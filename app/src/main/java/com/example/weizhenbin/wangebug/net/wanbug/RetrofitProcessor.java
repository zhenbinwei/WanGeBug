package com.example.weizhenbin.wangebug.net.wanbug;

import android.text.TextUtils;

import com.example.weizhenbin.wangebug.net.interfaces.IHttpProcessor;
import com.example.weizhenbin.wangebug.net.interfaces.IResult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class RetrofitProcessor  implements IHttpProcessor{


    private static List<String> baseUrls=new ArrayList<>();

    public void addBaseUrl(String baseUrl){
        baseUrls.add(baseUrl);
    }

    @Override
    public void get(String url, HashMap<String, String> param, final IResult iResult) {

        request("get",url, param, iResult);

    }

    private void request(String type,String url, HashMap<String, String> param, final IResult iResult) {
        if (TextUtils.isEmpty(url)){
            return;
        }
        Retrofit retrofit = null;

        retrofit = new Retrofit.Builder()
                .baseUrl(url) //http://fy.iciba.com/
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        //截取后缀
        //通过后缀反射找注解
            ApiService request = retrofit.create(ApiService.class);

            Method[] methods = ApiService.class.getDeclaredMethods();

            Observable<String>  call=request.getWanandroid(url);



            /*for (int i = 0; i < methods.length; i++) {
                Method method=methods[i];
                method.setAccessible(true);
                if("get".equals(type)){
                    GET annotation = method.getAnnotation(GET.class);
                    if(annotation!=null&&!TextUtils.isEmpty(suffix)&&suffix.equals(annotation.value())){
                        try {
                            call= (Observable<String>) method.invoke(request,param1);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }else {
                    POST annotation = method.getAnnotation(POST.class);
                    if(annotation!=null&&!TextUtils.isEmpty(suffix)&&suffix.equals(annotation.value())){
                        try {
                            call= (Observable<String>) method.invoke(request,param1);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }

            }*/
            if (call!=null){
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
                                if (iResult!=null){
                                    iResult.onFail(e);
                                }
                            }

                            @Override
                            public void onNext(String book) {//这里的book就是我们请求接口返回的实体类
                                if (iResult!=null){
                                    iResult.onSuccess(book);
                                }
                            }
                        });
            }
    }

    @Override
    public void post(String url, HashMap<String, String> param, IResult iResult) {
        request("post",url, param, iResult);
    }


}
