package com.example.weizhenbin.wangebug.base;

import android.app.Application;

import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.image.glide.GlideImageLoader;

/**
 * Created by weizhenbin on 2018/8/8.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.getImageLoader().init(new GlideImageLoader());
    }
}
