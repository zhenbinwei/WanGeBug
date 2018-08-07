package com.example.weizhenbin.wangebug.net.impl;

import com.example.weizhenbin.wangebug.net.interfaces.IResult;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by weizhenbin on 18/8/7.
 */

public abstract class HttpResult<T> implements IResult {

    @Override
    public void onSuccess(String result) {

        Class<?> classType = getClassType(this);

        Gson gson=new Gson();

        T t= (T) gson.fromJson(result,classType);
        onSuccess(t);

    }

    private Class<?> getClassType(HttpResult<T> tHttpResult) {
        Type type=tHttpResult.getClass().getGenericSuperclass();
        Type[] types= ((ParameterizedType)type).getActualTypeArguments();

        return (Class<?>) types[0];
    }

    public abstract void onSuccess(T t);
}
