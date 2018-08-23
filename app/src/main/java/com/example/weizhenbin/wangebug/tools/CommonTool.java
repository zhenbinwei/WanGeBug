package com.example.weizhenbin.wangebug.tools;

import android.text.TextUtils;

/**
 * Created by weizhenbin on 2018/8/22.
 */

public class CommonTool {
    public static boolean isGif(String url){
        if (TextUtils.isEmpty(url)){
            return false;
        }
        return url.toLowerCase().contains(".gif".toLowerCase());
    }
}
