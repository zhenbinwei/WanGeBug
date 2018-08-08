package com.example.weizhenbin.wangebug.base;

import android.app.Application;

import com.example.weizhenbin.wangebug.net.HttpHelper;
import com.example.weizhenbin.wangebug.net.wanbug.RetrofitProcessor;

/**
 * Created by weizhenbin on 2018/8/8.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitProcessor retrofitProcessor=new RetrofitProcessor();
        retrofitProcessor.addBaseUrl("http://fy.iciba.com/");
        HttpHelper.init(retrofitProcessor);
    }
}
