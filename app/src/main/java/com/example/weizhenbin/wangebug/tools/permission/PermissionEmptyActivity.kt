package com.example.weizhenbin.wangebug.tools.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.support.v4.app.ActivityCompat
import com.example.weizhenbin.wangebug.base.BaseActivity

/**
 * Created by weizhenbin on 2018/9/10.
 *
 * 权限列表
 * @see Manifest.permission
 */

class PermissionEmptyActivity : BaseActivity() {

    private var permissions: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * 用一个空的 透明的 大小看不见的 activity来处理一些需要在activity里回调的方法 达到调用权限申请 不局限于activity
         */
        val layoutParams = window.attributes
        layoutParams.height = 1
        layoutParams.width = 1
        window.attributes = layoutParams

        val type = intent.getIntExtra("TYPE", 0)
        if (type == TYPE_PERMISSION) {
            permissions = intent.getStringArrayExtra("permissions")

            if (permissions == null || permissions!!.size <= 0) {
                finish()
            } else {
                requestPermission()
            }
        } else if (type == TYPE_FLOATING_WINDOW) {
            requestFloatingWindow()
        }
    }

    private fun requestPermission() {
        //ActivityCompat.checkSelfPermission 加上ActivityCompat 不需要区分版本
        ActivityCompat.requestPermissions(this, permissions!!, 1)
    }


    private fun requestFloatingWindow() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startActivityForResult(Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")), PERMISSION_REQUEST_CODE)
        } else {
            if (PermissionResultManager.getManager().getiFloatingWindowPermissionGrantResult() != null) {
                PermissionResultManager.getManager()
                        .getiFloatingWindowPermissionGrantResult()
                        ?.onGrantResult(true)
            }
            finish()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (PermissionResultManager.getManager().getiPermissionGrantResult() != null) {
                PermissionResultManager.getManager().getiPermissionGrantResult()?.onGrantResult(permissions, grantResults)
            }
        }
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (PermissionResultManager.getManager().getiFloatingWindowPermissionGrantResult() != null) {
                PermissionResultManager.getManager()
                        .getiFloatingWindowPermissionGrantResult()
                        ?.onGrantResult(PermissionTool.checkWindowPermission(this@PermissionEmptyActivity))
            }
            finish()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 10000

        private const val TYPE_PERMISSION = 0//一般权限
        private const val TYPE_FLOATING_WINDOW = 1//特殊权限 悬浮窗权限

        fun requestPermission(context: Context, permissions: Array<String>) {
            val intent = Intent(context, PermissionEmptyActivity::class.java)
            intent.putExtra("permissions", permissions)
            intent.putExtra("TYPE", TYPE_PERMISSION)
            context.startActivity(intent)
        }

        fun requestFloatingWindowPermission(context: Context) {
            val intent = Intent(context, PermissionEmptyActivity::class.java)
            intent.putExtra("TYPE", TYPE_FLOATING_WINDOW)
            context.startActivity(intent)
        }
    }
}
