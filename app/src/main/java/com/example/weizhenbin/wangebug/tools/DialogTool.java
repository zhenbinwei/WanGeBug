package com.example.weizhenbin.wangebug.tools;

import android.content.Context;

import com.example.weizhenbin.wangebug.views.CustomDialog;

/**
 * Created by weizhenbin on 2018/9/4.
 */

public class DialogTool {

    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr, CustomDialog.OnClickListener onPositiveClickListener, String negativeButtonStr, CustomDialog.OnClickListener onNegativeClickListener,boolean cancelable){

        CustomDialog customDialog=new CustomDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setPositiveButton(positiveButtonStr, onPositiveClickListener)
                .setNegativeButton(negativeButtonStr, onNegativeClickListener)
                .create();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setCancelable(cancelable);
        customDialog.show();
    }
    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr, CustomDialog.OnClickListener onPositiveClickListener, String negativeButtonStr, CustomDialog.OnClickListener onNegativeClickListener){
        showAlertDialog( context,  title,  content,  positiveButtonStr,  onPositiveClickListener,  negativeButtonStr,  onNegativeClickListener,true);
    }
    /**
     * 用于提示 确认操作
     * */
    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr,boolean cancelable){
       showAlertDialog(context,title,content,positiveButtonStr,null,null,null,cancelable);
    }

    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr){
        showAlertDialog( context,  title,  content,  positiveButtonStr,true);
    }
    /**
     * 用于提示 确认操作
     * */
    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr,CustomDialog.OnClickListener onPositiveClickListener,boolean cancelable){
        showAlertDialog(context,title,content,positiveButtonStr,onPositiveClickListener,null,null,cancelable);
    }
    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr,CustomDialog.OnClickListener onPositiveClickListener){
        showAlertDialog( context,  title,  content,  positiveButtonStr, onPositiveClickListener,true);
    }
    /**
     * 用于提示 无操作
     * */
    public static void showAlertDialog(Context context, String title, String content,boolean cancelable){
        showAlertDialog(context,title,content,null,null,null,null,cancelable);
    }
    public static void showAlertDialog(Context context, String title, String content){
        showAlertDialog( context,  title,  content,true);
    }
    /**
     * 用于提示 无操作
     * */
    public static void showAlertDialog(Context context, String content,boolean cancelable){
        showAlertDialog(context,null,content,null,null,null,null, cancelable);
    }
    public static void showAlertDialog(Context context, String content){
        showAlertDialog( context,content,true);
    }



    public static void showListAlertDialog(Context context,String title,String[] items,CustomDialog.OnClickListener onClickListener){
        CustomDialog customDialog=new CustomDialog.Builder(context).setItems(items,onClickListener).create();
        customDialog.show();

    }
    public static void showListAlertDialog(Context context,String[] items,CustomDialog.OnClickListener onClickListener){
        showListAlertDialog(context,null,items,onClickListener);
    }

}
