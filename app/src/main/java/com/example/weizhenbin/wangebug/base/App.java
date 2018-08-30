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
       /* HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, false);
        QbSdk.initTbsSettings(map);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);*/

    }
}
