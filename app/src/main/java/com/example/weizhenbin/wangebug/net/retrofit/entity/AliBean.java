package com.example.weizhenbin.wangebug.net.retrofit.entity;

/**
 * Created by weizhenbin on 18/8/12.
 */

public class AliBean<T> {

    public String showapi_res_error;

    public String showapi_res_id;

    public int showapi_res_code;

    public T showapi_res_body;

    @Override
    public String toString() {
        return "AliBean{" +
                "showapi_res_error='" + showapi_res_error + '\'' +
                ", showapi_res_id='" + showapi_res_id + '\'' +
                ", showapi_res_code=" + showapi_res_code +
                ", showapi_res_body=" + showapi_res_body +
                '}';
    }
}
