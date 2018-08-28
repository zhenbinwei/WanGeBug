package com.example.weizhenbin.wangebug.image;

/**
 * Created by weizhenbin on 18/8/26.
 */

public class DefImageConfig {

    public static ImageConfig smallImage(){
        return new ImageConfig.Builder().setHeight(480).setHeight(480).setGif(false).build();
    }
    public static ImageConfig smallImageLong(){
        return new ImageConfig.Builder().setHeight(640).setHeight(480).setGif(false).build();
    }
}
