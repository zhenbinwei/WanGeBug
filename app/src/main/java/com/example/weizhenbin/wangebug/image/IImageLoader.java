package com.example.weizhenbin.wangebug.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by weizhenbin on 2018/8/15.
 */

public interface IImageLoader {

    void imageLoader(Context context, ImageView imageView,String url);

    void imageLoader(Context context, ImageView imageView,String url,ImageConfig imageConfig);

    void imageLoader(Context context, ImageView imageView,String url,IImageLoadListener iImageLoadListener);

    void imageLoader(Context context, ImageView imageView,String url,ImageConfig imageConfig,IImageLoadListener iImageLoadListener);

}
