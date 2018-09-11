package com.example.weizhenbin.wangebug.tools.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

import static android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION;

/**
 * Created by weizhenbin on 2018/9/10.
 * 权限申请工具
 *
 * @see  Manifest.permission
 *
 */

public class PermissionTool {

    private Context context;
    private IPermissionGrantResult iPermissionGrantResult;
    private PermissionTool(Context context){
         this.context=context;
    }


    public static PermissionTool with(Context context){
        return new PermissionTool(context);
    }


    public PermissionTool setiPermissionGrantResult(IPermissionGrantResult iPermissionGrantResult){
        this.iPermissionGrantResult=iPermissionGrantResult;
        return this;
    }

  /**
   * @see  Manifest.permission
   * */
    public void requestPermissions(String[] permissions){
        PermissionResultManager.getManager().addPermissionGrantListener(null);
        PermissionResultManager.getManager().addPermissionGrantListener(iPermissionGrantResult);
        if (checkSelfAllPermission(permissions)){
            if(PermissionResultManager.getManager().getiPermissionGrantResult()!=null){
                int[] grantResults=new int[permissions.length];
                for (int i = 0; i < grantResults.length; i++) {
                    grantResults[i]=1;
                }
                PermissionResultManager.getManager().getiPermissionGrantResult().onGrantResult(permissions,grantResults);
            }
        }else {
            PermissionEmptyActivity.startActivity(context, permissions);
        }
    }


    /**是否有允许弹窗的
     * shouldShowRequestPermissionRationale
     */
    private boolean shouldShowRequestPermissionRationale(Activity activitie,String[] permissions){
        boolean result=false;

        if (permissions==null){
            return true;
        }
        for (int i = 0; i < permissions.length; i++) {
            String permission=permissions[i];
            if (PackageManager.PERMISSION_GRANTED!=ActivityCompat.checkSelfPermission(context,permission)
                    &&ActivityCompat.shouldShowRequestPermissionRationale(activitie,permission)){
                result=true;
            }
        }
        return result;
    }


    public static boolean checkPermission(Context context,String permission){

        /**
         * ActivityCompat.checkSelfPermission 加上ActivityCompat 不需要区分版本
         * */
      return  PackageManager.PERMISSION_GRANTED==ActivityCompat.checkSelfPermission(context,permission);

    }


    /**
     * 是否都授权了
     * */
    private boolean checkSelfAllPermission(String[] permissions){
        boolean selfPermission=true;
        if (permissions==null){
            return true;
        }
        for (int i = 0; i < permissions.length; i++) {

            if (PackageManager.PERMISSION_GRANTED!=ActivityCompat.checkSelfPermission(context,permissions[i])){
                selfPermission=false;
            }
        }
        return selfPermission;
    }


    /**
     * 申请特殊权限 悬浮窗
     * */
    public static boolean checkWindowPermission(Context context){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
           return Settings.canDrawOverlays(context);
        }else {
            return true;
        }
    }


    /**
     * 引导用户去开启悬浮窗权限
     * startActivity(new Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + this.getPackageName())));
     * */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setOverlayPermission(Context context){
        context.startActivity(new Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName())));
    }

}
