package com.example.weizhenbin.wangebug.image;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by weizhenbin on 2018/8/15.
 */

public interface IImageLoadListener {

    void onLoadStart(String url, Drawable defDrawable);

    void onLoadError(String url, Drawable errorDrawable);

    void onLoadSuccess(Bitmap bitmap,String url);

}
