package com.example.weizhenbin.wangebug.image;

import android.graphics.Bitmap;

/**
 * Created by weizhenbin on 2018/8/15.
 */

public interface IImageLoadListener {

    void onLoadStart(String url);

    void onLoadError(String url);

    void onLoadSuccess(Bitmap bitmap,String url);

}
