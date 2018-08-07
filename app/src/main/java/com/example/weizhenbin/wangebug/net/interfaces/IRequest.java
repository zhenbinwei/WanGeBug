package com.example.weizhenbin.wangebug.net.interfaces;

import java.util.HashMap;

/**
 * Created by weizhenbin on 2018/8/7.
 */

public interface IRequest<T> {

    void get(String url,HashMap<String,String> param,IResult<T> iResult);

    void post(String url,HashMap<String,String> param,IResult<T> iResult);

}
