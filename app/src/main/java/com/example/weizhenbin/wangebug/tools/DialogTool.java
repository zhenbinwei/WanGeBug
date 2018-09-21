package com.example.weizhenbin.wangebug.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by weizhenbin on 2018/9/4.
 */

public class DialogTool {

    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr, DialogInterface.OnClickListener onPositiveClickListener, String negativeButtonStr, DialogInterface.OnClickListener onNegativeClickListener,boolean cancelable){
        Dialog alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(positiveButtonStr, onPositiveClickListener)
                .setNegativeButton(negativeButtonStr, onNegativeClickListener)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(cancelable);
        alertDialog.show();
    }
    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr, DialogInterface.OnClickListener onPositiveClickListener, String negativeButtonStr, DialogInterface.OnClickListener onNegativeClickListener){
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
    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr,DialogInterface.OnClickListener onPositiveClickListener,boolean cancelable){
        showAlertDialog(context,title,content,positiveButtonStr,onPositiveClickListener,null,null,cancelable);
    }
    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr,DialogInterface.OnClickListener onPositiveClickListener){
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



    public static void showListAlertDialog(Context context,String title,String[] items,DialogInterface.OnClickListener onClickListener){
        Dialog alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setTitle(title)
                .setItems(items, onClickListener)
                .create();
        alertDialog.show();
    }
    public static void showListAlertDialog(Context context,String[] items,DialogInterface.OnClickListener onClickListener){
        showListAlertDialog(context,null,items,onClickListener);
    }

}
