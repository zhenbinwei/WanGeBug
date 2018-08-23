package com.example.weizhenbin.wangebug.image;


import android.graphics.drawable.Drawable;

import com.example.weizhenbin.wangebug.tools.PhoneTool;

/**
 * Created by weizhenbin on 2018/8/15.
 */

public class ImageConfig {

    private int width= PhoneTool.getScreenWidth();

    private int height=PhoneTool.getScreenHeight();

    private Drawable defDrawable; //默认图
    private Drawable errorDrawable; //加载失败时错误图
    private int defIconResId;
    private int errorIconResId;


    private boolean isGif;


    private ImageConfig(Builder builder){
         this.width=builder.width;
         this.height=builder.height;
         this.defDrawable=builder.defDrawable;
         this.defIconResId=builder.defIconResId;
        this.errorDrawable=builder.errorDrawable;
        this.errorIconResId=builder.errorIconResId;
        this.isGif=builder.isGif;
    }

    public static class Builder{
        private int width= PhoneTool.getScreenWidth();

        private int height=PhoneTool.getScreenHeight();

        private Drawable defDrawable; //默认图
        private Drawable errorDrawable; //加载失败时错误图
        private int defIconResId;
        private int errorIconResId;

        private boolean isGif;
        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setDefDrawable(Drawable defDrawable) {
            this.defDrawable = defDrawable;
            return this;
        }

        public Builder setErrorDrawable(Drawable errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        public Builder setDefIconResId(int defIconResId) {
            this.defIconResId = defIconResId;
            return this;
        }

        public Builder setErrorIconResId(int errorIconResId) {
            this.errorIconResId = errorIconResId;
            return this;
        }

        public Builder setGif(boolean gif) {
            isGif = gif;
            return this;
        }

        public ImageConfig build(){
            return new ImageConfig(this);
        }
    }



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Drawable getDefDrawable() {
        return defDrawable;
    }

    public Drawable getErrorDrawable() {
        return errorDrawable;
    }

    public int getDefIconResId() {
        return defIconResId;
    }

    public int getErrorIconResId() {
        return errorIconResId;
    }

    public boolean isGif() {
        return isGif;
    }
}
