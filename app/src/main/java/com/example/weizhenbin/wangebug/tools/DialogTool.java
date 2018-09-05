package com.example.weizhenbin.wangebug.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;

import com.example.weizhenbin.wangebug.R;

/**
 * Created by weizhenbin on 2018/9/4.
 */

public class DialogTool {

    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr, DialogInterface.OnClickListener onPositiveClickListener, String negativeButtonStr, DialogInterface.OnClickListener onNegativeClickListener){
        Dialog alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(positiveButtonStr, onPositiveClickListener)
                .setNegativeButton(negativeButtonStr, onNegativeClickListener)
                .create();
        alertDialog.show();
    }
    /**
     * 用于提示 确认操作
     * */
    public static void showAlertDialog(Context context, String title, String content, String positiveButtonStr){
       showAlertDialog(context,title,content,positiveButtonStr,null,null,null);
    }
    /**
     * 用于提示 无操作
     * */
    public static void showAlertDialog(Context context, String title, String content){
        showAlertDialog(context,title,content,null,null,null,null);
    }
    /**
     * 用于提示 无操作
     * */
    public static void showAlertDialog(Context context, String content){
        showAlertDialog(context,null,content,null,null,null,null);
    }



    public static void showTimeDialog(Context context){
        View view=View.inflate(context,R.layout.dialog_datepicker_layout,null);
        DatePicker datePicker=view.findViewById(R.id.dp_date);
        Dialog alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                .setView(view)
                .setNegativeButton(R.string.cancel_string, null)
                .setPositiveButton(R.string.confirm_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        alertDialog.show();
    }



}
