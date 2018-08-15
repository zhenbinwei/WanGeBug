package com.example.weizhenbin.wangebug.image.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.weizhenbin.wangebug.image.IImageLoadListener;
import com.example.weizhenbin.wangebug.image.IImageLoader;
import com.example.weizhenbin.wangebug.image.ImageConfig;

/**
 * Created by weizhenbin on 2018/8/15.
 */

public class GlideImageLoader implements IImageLoader {




    @Override
    public void imageLoader(Context context, ImageView imageView, String url) {
        this.imageLoader(context,imageView,url,null,null);
    }

    @Override
    public void imageLoader(Context context, ImageView imageView, String url, ImageConfig imageConfig) {
         this.imageLoader(context,imageView,url,imageConfig,null);
    }

    @Override
    public void imageLoader(Context context, ImageView imageView, String url, IImageLoadListener iImageLoadListener) {
          this.imageLoader(context,imageView,url,null,iImageLoadListener);
    }

    @Override
    public void imageLoader(Context context, ImageView imageView, String url, ImageConfig imageConfig, IImageLoadListener iImageLoadListener) {
        Glide.with(context).asBitmap().load(url).into(imageView);
    }
}
