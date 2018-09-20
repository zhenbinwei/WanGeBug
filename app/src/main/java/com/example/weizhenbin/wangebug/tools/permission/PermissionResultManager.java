package com.example.weizhenbin.wangebug.tools.permission;

/**
 * Created by weizhenbin on 2018/9/11.
 */

public class PermissionResultManager {

    private static PermissionResultManager manager;


    private IPermissionGrantResult iPermissionGrantResult;

    private IFloattingWindowPermissionGrantResult iFloattingWindowPermissionGrantResult;

    private PermissionResultManager(){

    }


    public void addFloattingWindowPermissionListener(IFloattingWindowPermissionGrantResult iFloattingWindowPermissionGrantResult){
        this.iFloattingWindowPermissionGrantResult=iFloattingWindowPermissionGrantResult;
    }
    public void addPermissionGrantListener(IPermissionGrantResult iPermissionGrantResult){
        this.iPermissionGrantResult=iPermissionGrantResult;
    }


    public IFloattingWindowPermissionGrantResult getiFloatingWindowPermissionGrantResult() {
        return iFloattingWindowPermissionGrantResult;
    }

    public IPermissionGrantResult getiPermissionGrantResult() {
        return iPermissionGrantResult;
    }

    public static PermissionResultManager getManager(){

        if (manager==null){
            synchronized (PermissionResultManager.class){
                if (manager==null){
                    manager=new PermissionResultManager();
                }
            }
        }
        return manager;
    }


}
