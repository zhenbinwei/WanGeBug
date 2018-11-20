package com.example.weizhenbin.wangebug.net.retrofit.entity

/**
 * Created by weizhenbin on 18/8/12.
 */

class AliBean<T> {

    var showapi_res_error: String? = null

    var showapi_res_id: String? = null

    var showapi_res_code: Int = 0

    var showapi_res_body: T? = null

    override fun toString(): String {
        return "AliBean{" +
                "showapi_res_error='" + showapi_res_error + '\''.toString() +
                ", showapi_res_id='" + showapi_res_id + '\''.toString() +
                ", showapi_res_code=" + showapi_res_code +
                ", showapi_res_body=" + showapi_res_body +
                '}'.toString()
    }
}
