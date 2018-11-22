package com.example.weizhenbin.wangebug.tools.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat

/**
 * Created by weizhenbin on 2018/9/10.
 * 权限申请工具
 *
 * @see Manifest.permission
 */

class PermissionTool private constructor(private val context: Context) {
    private var iPermissionGrantResult: IPermissionGrantResult? = null
    private var iFloatingWindowPermissionGrantResult: IFloattingWindowPermissionGrantResult? = null


    fun setiPermissionGrantResult(iPermissionGrantResult: IPermissionGrantResult): PermissionTool {
        this.iPermissionGrantResult = iPermissionGrantResult
        return this
    }

    fun setiFloatingWindowPermissionGrantResult(iFloatingWindowPermissionGrantResult: IFloattingWindowPermissionGrantResult): PermissionTool {
        this.iFloatingWindowPermissionGrantResult = iFloatingWindowPermissionGrantResult
        return this
    }

    /**
     * @see Manifest.permission
     *
     */
    fun requestPermissions(permissions: Array<String>) {
        PermissionResultManager.getManager().addPermissionGrantListener(null)
        PermissionResultManager.getManager().addPermissionGrantListener(iPermissionGrantResult)
        if (checkSelfAllPermission(permissions)) {
            if (PermissionResultManager.getManager().getiPermissionGrantResult() != null) {
                val grantResults = IntArray(permissions.size)
                for (i in grantResults.indices) {
                    grantResults[i] = 1
                }
                PermissionResultManager.getManager().getiPermissionGrantResult()?.onGrantResult(permissions, grantResults)
            }
        } else {
            PermissionEmptyActivity.requestPermission(context, permissions)
        }
    }


    /**是否有允许弹窗的
     * shouldShowRequestPermissionRationale
     */
    private fun shouldShowRequestPermissionRationale(activitie: Activity, permissions: Array<String>?): Boolean {
        var result = false

        if (permissions == null) {
            return true
        }
        for (i in permissions.indices) {
            val permission = permissions[i]
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, permission) && ActivityCompat.shouldShowRequestPermissionRationale(activitie, permission)) {
                result = true
            }
        }
        return result
    }


    /**
     * 是否都授权了
     */
    private fun checkSelfAllPermission(permissions: Array<String>?): Boolean {
        var selfPermission = true
        if (permissions == null) {
            return true
        }
        for (i in permissions.indices) {

            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, permissions[i])) {
                selfPermission = false
            }
        }
        return selfPermission
    }


    fun requestFloatingWindowPermission() {
        PermissionResultManager.getManager().addFloatingWindowPermissionListener(null)
        PermissionResultManager.getManager().addFloatingWindowPermissionListener(iFloatingWindowPermissionGrantResult)
        PermissionEmptyActivity.requestFloatingWindowPermission(context)
    }

    companion object {


        fun with(context: Context): PermissionTool {
            return PermissionTool(context)
        }


        fun checkPermission(context: Context, permission: String): Boolean {

            /**
             * ActivityCompat.checkSelfPermission 加上ActivityCompat 不需要区分版本
             */
            return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context, permission)

        }


        /**
         * 申请特殊权限 悬浮窗
         */
        fun checkWindowPermission(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Settings.canDrawOverlays(context)
            } else {
                true
            }
        }


        /**
         * 引导用户去开启悬浮窗权限
         * requestPermission(new Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.getPackageName())));
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        fun setOverlayPermission(activity: Activity?, requestCode: Int) {
            if (activity == null) {
                return
            }
            activity.startActivityForResult(Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.packageName)), requestCode)
        }
    }

}
