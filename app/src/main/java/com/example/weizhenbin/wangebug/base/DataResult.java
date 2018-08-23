package com.example.weizhenbin.wangebug.base;

/**
 * Created by weizhenbin on 2018/8/22.
 */

public interface DataResult<T>{

    void onStart();

    void onError(Throwable throwable);

    void onSuccess(T t);

}
