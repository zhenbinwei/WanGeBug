package com.example.weizhenbin.wangebug.tools

import android.content.Context
import android.content.DialogInterface

import com.example.weizhenbin.wangebug.views.CustomDialog

/**
 * Created by weizhenbin on 2018/9/4.
 */

object DialogTool {

    @JvmOverloads
    fun showAlertDialog(context: Context, title: String?=null, content: String, positiveButtonStr: String?=null, onPositiveClickListener:  (( dialog:DialogInterface, which:Int)->Unit)?=null, negativeButtonStr: String?=null, onNegativeClickListener: (( dialog:DialogInterface, which:Int)->Unit)?=null, cancelable: Boolean = true) {

        val customDialog = CustomDialog.Builder(context).setTitle(title)
                .setMessage(content)
                .setPositiveButton(positiveButtonStr!!, onPositiveClickListener)
                .setNegativeButton(negativeButtonStr!!, onNegativeClickListener)
                .create()
        customDialog.setCanceledOnTouchOutside(false)
        customDialog.setCancelable(cancelable)
        customDialog.show()
    }

   /* *//**
     * 用于提示 确认操作
     *//*
    fun showAlertDialog(context: Context, title: String?, content: String, positiveButtonStr: String, cancelable: Boolean = true) {
        showAlertDialog(context, title, content, positiveButtonStr, null, null, null, cancelable)
    }

    *//**
     * 用于提示 确认操作
     *//*
    fun showAlertDialog(context: Context, title: String, content: String, positiveButtonStr: String, onPositiveClickListener: CustomDialog.OnClickListener, cancelable: Boolean = true) {
        showAlertDialog(context, title, content, positiveButtonStr, onPositiveClickListener, null, null, cancelable)
    }

    *//**
     * 用于提示 无操作
     *//*
    fun showAlertDialog(context: Context, title: String, content: String, cancelable: Boolean = true) {
        showAlertDialog(context, title, content, null, null, null, null, cancelable)
    }

    *//**
     * 用于提示 无操作
     *//*
    fun showAlertDialog(context: Context, content: String, cancelable: Boolean = true) {
        showAlertDialog(context, null, content, null, null, null, null, cancelable)
    }*/


    fun showListAlertDialog(context: Context, title: String?, items: Array<String>, onClickListener: CustomDialog.OnClickListener) {
        val customDialog = CustomDialog.Builder(context).setItems(items, onClickListener).create()
        customDialog.show()

    }

    fun showListAlertDialog(context: Context, items: Array<String>, onClickListener: CustomDialog.OnClickListener) {
        showListAlertDialog(context, null, items, onClickListener)
    }

}
