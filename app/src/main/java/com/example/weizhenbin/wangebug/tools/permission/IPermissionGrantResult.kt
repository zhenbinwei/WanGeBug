package com.example.weizhenbin.wangebug.tools.permission

/**
 * Created by weizhenbin on 2018/9/11.
 */

interface IPermissionGrantResult {

    fun onGrantResult(permissions: Array<String>, grantResults: IntArray)

}
