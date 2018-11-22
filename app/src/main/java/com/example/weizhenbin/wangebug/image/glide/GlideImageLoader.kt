package com.example.weizhenbin.wangebug.image.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.weizhenbin.wangebug.image.IImageLoadListener
import com.example.weizhenbin.wangebug.image.IImageLoader
import com.example.weizhenbin.wangebug.image.ImageConfig

/**
 * Created by weizhenbin on 2018/8/15.
 */

class GlideImageLoader : IImageLoader {


    override fun imageLoader(context: Context, imageView: ImageView, url: String) {
        this.imageLoader(context, imageView, url, null, null)
    }

    override fun imageLoader(context: Context, imageView: ImageView, url: String, imageConfig: ImageConfig?) {
        this.imageLoader(context, imageView, url, imageConfig, null)
    }

    override fun imageLoader(context: Context, imageView: ImageView, url: String, iImageLoadListener: IImageLoadListener?) {
        this.imageLoader(context, imageView, url, null, iImageLoadListener)
    }

    override fun imageLoader(context: Context, imageView: ImageView, url: String, imageConfig: ImageConfig?, iImageLoadListener: IImageLoadListener?) {
        var imageConfig = imageConfig
        if (imageConfig == null) {
            imageConfig = ImageConfig.Builder().build()
        }
        val requestManager = Glide.with(context)
        val requestOptions = RequestOptions()
                .error(imageConfig.errorDrawable)
                .error(imageConfig.errorIconResId)
                .placeholder(imageConfig.defDrawable)
                .placeholder(imageConfig.defIconResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        if (imageConfig.isGif) {
            val gifRequestBuilder = requestManager.asGif()
            if (iImageLoadListener != null) {
                iImageLoadListener.onLoadStart(url, imageConfig.defDrawable)
                val finalImageConfig = imageConfig
                gifRequestBuilder.load(url).apply(requestOptions).listener(object : RequestListener<com.bumptech.glide.load.resource.gif.GifDrawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<com.bumptech.glide.load.resource.gif.GifDrawable>, isFirstResource: Boolean): Boolean {
                        iImageLoadListener.onLoadError(url, finalImageConfig.defDrawable)
                        return false
                    }

                    override fun onResourceReady(resource: com.bumptech.glide.load.resource.gif.GifDrawable, model: Any, target: Target<com.bumptech.glide.load.resource.gif.GifDrawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        iImageLoadListener.onLoadSuccess(resource.firstFrame, url)
                        return false
                    }
                }).into(imageView)
            } else {
                gifRequestBuilder.load(url).apply(requestOptions).into(imageView)
            }

        } else {
            val bitmapRequestBuilder = requestManager.asBitmap()

            if (iImageLoadListener != null) {
                iImageLoadListener.onLoadStart(url, imageConfig.defDrawable)
                val finalImageConfig = imageConfig
                bitmapRequestBuilder.load(url).apply(requestOptions).listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                        iImageLoadListener.onLoadError(url, finalImageConfig.defDrawable)
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap, model: Any?, target: Target<Bitmap>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        iImageLoadListener.onLoadSuccess(resource, url)
                        return false
                    }
                }).into(imageView)
            } else {
                bitmapRequestBuilder.load(url).apply(requestOptions).transition(withCrossFade()).into(imageView)
            }


        }
    }


    override fun loadBitmap(context: Context, url: String, iImageLoadListener: IImageLoadListener?) {
        Glide.with(context).asBitmap().load(url).into(object : BaseTarget<Bitmap>() {

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>) {
                iImageLoadListener?.onLoadSuccess(resource, url)
            }

            override fun getSize(cb: SizeReadyCallback) {
                cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            }

            override fun removeCallback(cb: SizeReadyCallback) {

            }

            override fun onStart() {
                super.onStart()
                iImageLoadListener?.onLoadStart(url, null)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                iImageLoadListener?.onLoadError(url, null)
            }
        })
    }
}
