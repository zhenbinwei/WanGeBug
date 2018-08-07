package com.example.weizhenbin.wangebug.net.interfaces;

/**
 * Created by weizhenbin on 2018/8/7.
 */

public abstract class IResult<T> {
   public abstract  void onSuccess(T t);

    public abstract void onFail(Exception e);
}
