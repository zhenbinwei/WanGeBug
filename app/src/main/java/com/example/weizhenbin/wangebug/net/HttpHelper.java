package com.example.weizhenbin.wangebug.net;

import com.example.weizhenbin.wangebug.net.interfaces.IHttpProcessor;
import com.example.weizhenbin.wangebug.net.interfaces.IResult;

import java.util.HashMap;

/**
 * Created by weizhenbin on 18/8/7.
 */

public class HttpHelper implements IHttpProcessor{

    private static IHttpProcessor mIHttpProcessor;

    private static HttpHelper httpHelper;

    private HttpHelper(){

    }

    public static HttpHelper getHttpHelper(){
        if(httpHelper==null){
            httpHelper=new HttpHelper();
        }
        return httpHelper;
    }

    public static void init(IHttpProcessor iHttpProcessor){
        mIHttpProcessor=iHttpProcessor;

    }

    @Override
    public void get(String url, HashMap<String, String> param, IResult iResult) {
        mIHttpProcessor.get(url,param,iResult);
    }

    @Override
    public void post(String url, HashMap<String, String> param, IResult iResult) {
        mIHttpProcessor.post(url,param,iResult);
    }
}
