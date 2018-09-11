package com.example.weizhenbin.wangebug.tools.permission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.WindowManager;

import com.example.weizhenbin.wangebug.base.BaseActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

/**
 * Created by weizhenbin on 2018/9/10.
 *
 * 权限列表
 * @see Manifest.permission
 */

public class PermissionEmptyActivity extends BaseActivity {

    private String[] permissions ;

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

        permissions=getIntent().getStringArrayExtra("permissions");

        if (permissions==null||permissions.length<=0){
            finish();
        }else {
            requestPermission();
        }
    }

    public static void startActivity(Context context,String[] permissions){
        Intent intent=new Intent(context,PermissionEmptyActivity.class);
        intent.putExtra("permissions",permissions);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private void requestPermission(){
        //ActivityCompat.checkSelfPermission 加上ActivityCompat 不需要区分版本
        ActivityCompat.requestPermissions(this,permissions,1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            Log.d("PermissionEmptyActivity", "permissions:" + permissions[0]);
            Log.d("PermissionEmptyActivity", "grantResults:" + grantResults[0]);
            if(PermissionResultManager.getManager().getiPermissionGrantResult()!=null){
                PermissionResultManager.getManager().getiPermissionGrantResult().onGrantResult(permissions,grantResults);
            }
        }
        finish();
    }


}
