package com.example.weizhenbin.wangebug.tools;

import java.util.UUID;

/**
 * Created by weizhenbin on 2018/9/21.
 */

public class UUIDTool {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
