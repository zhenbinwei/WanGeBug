package com.example.weizhenbin.wangebug.image;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by weizhenbin on 2018/8/15.
 */

public class ImageLoadListenerAdapter implements IImageLoadListener{


    @Override
    public void onLoadStart(String url, Drawable defDrawable) {

    }

    @Override
    public void onLoadError(String url, Drawable errorDrawable) {

    }

    @Override
    public void onLoadSuccess(Bitmap bitmap, String url) {

    }
}
