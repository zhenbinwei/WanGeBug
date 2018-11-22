package com.example.weizhenbin.wangebug.tools.permission

/**
 * Created by weizhenbin on 2018/9/11.
 */

class PermissionResultManager private constructor() {


    private var iPermissionGrantResult: IPermissionGrantResult? = null

    private var iFloatingWindowPermissionGrantResult: IFloattingWindowPermissionGrantResult? = null


    fun addFloatingWindowPermissionListener(iFloatingWindowPermissionGrantResult: IFloattingWindowPermissionGrantResult?) {
        this.iFloatingWindowPermissionGrantResult = iFloatingWindowPermissionGrantResult
    }

    fun addPermissionGrantListener(iPermissionGrantResult: IPermissionGrantResult?) {
        this.iPermissionGrantResult = iPermissionGrantResult
    }


    fun getiFloatingWindowPermissionGrantResult(): IFloattingWindowPermissionGrantResult? {
        return iFloatingWindowPermissionGrantResult
    }

    fun getiPermissionGrantResult(): IPermissionGrantResult? {
        return iPermissionGrantResult
    }




    private object SingletonHolder{
        val holder=PermissionResultManager()
    }



    companion object {

         fun getManager()=SingletonHolder.holder
    }


}
