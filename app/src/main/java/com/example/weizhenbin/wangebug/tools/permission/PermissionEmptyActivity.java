package com.example.weizhenbin.wangebug.tools.permission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.WindowManager;

import com.example.weizhenbin.wangebug.base.BaseActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION;

/**
 * Created by weizhenbin on 2018/9/10.
 *
 * 权限列表
 * @see Manifest.permission
 */

public class PermissionEmptyActivity extends BaseActivity {

    private String[] permissions ;
    private static final int PERMISSION_REQUEST_CODE=10000;

    private static final int TYPE_PERMISSION=0;//一般权限
    private static final int TYPE_FLOATTINGWINDOW=1;//特殊权限 悬浮窗权限

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /**
        * 用一个空的 透明的 大小看不见的 activity来处理一些需要在activity里回调的方法 达到调用权限申请 不局限于activity
        * */
        WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
        layoutParams.height=1;
        layoutParams.width=1;
        getWindow().setAttributes(layoutParams);

        int type=getIntent().getIntExtra("TYPE",0);
        if (type==TYPE_PERMISSION) {
            permissions = getIntent().getStringArrayExtra("permissions");

            if (permissions == null || permissions.length <= 0) {
                finish();
            } else {
                requestPermission();
            }
        }else if (type==TYPE_FLOATTINGWINDOW){
            requestFloattingWindow();
        }
    }

    public static void requestPermission(Context context, String[] permissions){
        Intent intent=new Intent(context,PermissionEmptyActivity.class);
        intent.putExtra("permissions",permissions);
        intent.putExtra("TYPE",TYPE_PERMISSION);
        context.startActivity(intent);
    }

    public static void requestFloattingWindowPermission(Context context){
        Intent intent=new Intent(context,PermissionEmptyActivity.class);
        intent.putExtra("TYPE",TYPE_FLOATTINGWINDOW);
        context.startActivity(intent);
    }

    private void requestPermission(){
        //ActivityCompat.checkSelfPermission 加上ActivityCompat 不需要区分版本
        ActivityCompat.requestPermissions(this,permissions,1);
    }


    private void requestFloattingWindow(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startActivityForResult(new Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())),PERMISSION_REQUEST_CODE);
        }else {
            if (PermissionResultManager.getManager().getiFloattingWindowPermissionGrantResult()!=null){
                PermissionResultManager.getManager()
                        .getiFloattingWindowPermissionGrantResult()
                        .onGrantResult(true);
            }
            finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if(PermissionResultManager.getManager().getiPermissionGrantResult()!=null){
                PermissionResultManager.getManager().getiPermissionGrantResult().onGrantResult(permissions,grantResults);
            }
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PERMISSION_REQUEST_CODE){
                if (PermissionResultManager.getManager().getiFloattingWindowPermissionGrantResult()!=null){
                    PermissionResultManager.getManager()
                            .getiFloattingWindowPermissionGrantResult()
                            .onGrantResult(PermissionTool.checkWindowPermission(PermissionEmptyActivity.this));
                }
        }
    }
}
