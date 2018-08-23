package com.example.weizhenbin.wangebug.image.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.weizhenbin.wangebug.image.IImageLoadListener;
import com.example.weizhenbin.wangebug.image.IImageLoader;
import com.example.weizhenbin.wangebug.image.ImageConfig;

import static com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade;

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
    public void imageLoader(Context context, ImageView imageView, final String url, ImageConfig imageConfig, final IImageLoadListener iImageLoadListener) {
        if (imageConfig==null){
            imageConfig=new ImageConfig.Builder().build();
        }
        RequestManager requestManager= Glide.with(context);
        RequestBuilder requestBuilder;
        if (imageConfig.isGif()){
            requestBuilder= requestManager.asGif();
        }else {
            requestBuilder=requestManager.asBitmap();
        }
        requestBuilder=requestBuilder.load(url);
        RequestOptions requestOptions=new RequestOptions()
                .error(imageConfig.getErrorDrawable())
                .error(imageConfig.getErrorIconResId())
                .placeholder(imageConfig.getDefDrawable())
                .placeholder(imageConfig.getDefIconResId())
                .override(imageConfig.getWidth(),imageConfig.getHeight())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        requestBuilder=requestBuilder.apply(requestOptions);
        requestBuilder = setListener(url, imageConfig, iImageLoadListener, requestBuilder);
        if (!imageConfig.isGif()){
            requestBuilder=requestBuilder.transition(withCrossFade());
        }
        requestBuilder.into(imageView);
    }

    private RequestBuilder setListener(final String url, ImageConfig imageConfig, final IImageLoadListener iImageLoadListener, RequestBuilder requestBuilder) {
        if (iImageLoadListener!=null){
            iImageLoadListener.onLoadStart(url,imageConfig.getDefDrawable());
        }
        final ImageConfig finalImageConfig = imageConfig;
        requestBuilder=requestBuilder.listener(new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                if (iImageLoadListener!=null){
                    iImageLoadListener.onLoadError(url, finalImageConfig.getDefDrawable());
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                if (resource instanceof Bitmap){
                if (iImageLoadListener!=null){
                    iImageLoadListener.onLoadSuccess((Bitmap) resource,url);
                }
                }
                return false;
            }
        });
        return requestBuilder;
    }

    @Override
    public void loadBitmap(Context context, final String url, final IImageLoadListener iImageLoadListener) {
        Glide.with(context).asBitmap().load(url).into(new BaseTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                if (iImageLoadListener!=null){
                    iImageLoadListener.onLoadSuccess(resource,url);
                }
            }

            @Override
            public void getSize(SizeReadyCallback cb) {
                cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            }

            @Override
            public void removeCallback(SizeReadyCallback cb) {

            }

            @Override
            public void onStart() {
                super.onStart();
                if (iImageLoadListener!=null){
                    iImageLoadListener.onLoadStart(url,null);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                if (iImageLoadListener!=null){
                    iImageLoadListener.onLoadError(url,null);
                }
            }
        });
    }
}
