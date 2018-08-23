package com.example.weizhenbin.wangebug.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by weizhenbin on 2018/8/15.
 */

public class ImageLoader implements IImageLoader {


    private IImageLoader realImageLoader;

    private static ImageLoader imageLoader;

    private ImageLoader(){

    }

    public void init(IImageLoader iImageLoader){
        if(iImageLoader==null){
            throw new NullPointerException("iImageLoader 不能为空");
        }
        this.realImageLoader=iImageLoader;
    }


    public static ImageLoader getImageLoader(){
        if(imageLoader==null){
            synchronized (ImageLoader.class){
                if (imageLoader==null){
                    imageLoader=new ImageLoader();
                }
            }
        }
        return imageLoader;
    }


    @Override
    public void imageLoader(Context context, ImageView imageView, String url) {
        checkNull();
        realImageLoader.imageLoader(context,imageView,url);
    }

    @Override
    public void imageLoader(Context context, ImageView imageView, String url, ImageConfig imageConfig) {
        checkNull();
        realImageLoader.imageLoader(context,imageView,url,imageConfig);
    }

    @Override
    public void imageLoader(Context context, ImageView imageView, String url, IImageLoadListener iImageLoadListener) {
        checkNull();
        realImageLoader.imageLoader(context,imageView,url,iImageLoadListener);
    }

    @Override
    public void imageLoader(Context context, ImageView imageView, String url, ImageConfig imageConfig, IImageLoadListener iImageLoadListener) {
        checkNull();
        realImageLoader.imageLoader(context,imageView,url,imageConfig,iImageLoadListener);
    }

    @Override
    public void loadBitmap(Context context, String url, IImageLoadListener iImageLoadListener) {
        checkNull();
        realImageLoader.loadBitmap(context,url,iImageLoadListener);
    }

    private void checkNull(){
        if (realImageLoader==null){
            throw new NullPointerException("没有调用init");
        }
    }

}
