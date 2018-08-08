package com.example.weizhenbin.wangebug.net.interfaces;


/**
 * Created by weizhenbin on 2018/8/7.
 */

public interface IResult {
       void onSuccess(String result);

       void onFail(Throwable  e);
}
