package com.example.weizhenbin.wangebug.modules.todo.controllers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import com.example.weizhenbin.wangebug.base.App;
import com.example.weizhenbin.wangebug.modules.todo.alarm.AlarmReceiver;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBAlarmMapBean;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBAlarmMapBean_;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean_;

import java.util.List;

import io.objectbox.Box;

/**
 * Created by weizhenbin on 18/9/8.
 */

public class TodoController {



    /***
     * 存储todo
     * */
    public static long saveTodo(TBTodoBean tbTodoBean){
        Box<TBTodoBean> todoBeanBox = App.app.getBoxStore().boxFor(TBTodoBean.class);
       return todoBeanBox.put(tbTodoBean);

    }


    /**
     * 获取todo列表
     * */
    public static List<TBTodoBean> getTodoList(int todoStatus,int page){
     return  getTodoList(todoStatus,page,20);
    }

    /**
     * 获取todo列表
     * */
    public static List<TBTodoBean> getTodoList(int todoStatus,int page,int pageCount){
        Box<TBTodoBean> todoBeanBox = App.app.getBoxStore().boxFor(TBTodoBean.class);
        if (todoStatus==-1){
            return todoBeanBox.query().orderDesc(TBTodoBean_.id).build().find((page-1)*pageCount,pageCount);
        }else {
            return todoBeanBox.query().orderDesc(TBTodoBean_.id).equal(TBTodoBean_.todoStatus, todoStatus).build().find();
        }
    }

    public static TBTodoBean getTodoBean(String uuid){
        Box<TBTodoBean> todoBeanBox = App.app.getBoxStore().boxFor(TBTodoBean.class);
       return todoBeanBox.query().equal(TBTodoBean_.uuid,uuid).build().findFirst();
    }


    /**
     * 修改todo
     * */
    public static long updateTodoByUuid(TBTodoBean todoBean,String uuid){
        Box<TBTodoBean> todoBeanBox = App.app.getBoxStore().boxFor(TBTodoBean.class);
        TBTodoBean tbTodoBean= todoBeanBox.query().equal(TBTodoBean_.uuid,uuid).build().findFirst();
        if (tbTodoBean!=null) {
            tbTodoBean.update(todoBean);
          return   todoBeanBox.put(tbTodoBean);
        }
        return -1;
    }





    /**
     * 删除todo
     * */
     public static long delTodo(String uuid){
         Box<TBTodoBean> todoBeanBox = App.app.getBoxStore().boxFor(TBTodoBean.class);
        return todoBeanBox.query().equal(TBTodoBean_.uuid,uuid).build().remove();
     }


     /**
      * 获取闹钟个数
      * */
     public static long getRemindCount(){
         Box<TBTodoBean> todoBeanBox = App.app.getBoxStore().boxFor(TBTodoBean.class);
         return todoBeanBox.query().equal(TBTodoBean_.isTodoRemind,1).build().count();
     }



     /**
      * 设置闹钟
      * */
     public static void setAlarm(Context context,TBTodoBean  tbTodoBean){
         AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
         Intent intent = new Intent(AlarmReceiver.ACTION_KEY);
         intent.setClass(context, AlarmReceiver.class);
         intent.putExtra(AlarmReceiver.KEY_ID,tbTodoBean.getUuid());
         intent.putExtra(AlarmReceiver.KEY_TITLE,tbTodoBean.getTodoTitle());
         intent.putExtra(AlarmReceiver.KEY_CONTENT,tbTodoBean.getTodoContent());
         int number=isExistAlarmMap(tbTodoBean.getUuid());
         if (number==0){
             number = (int) (getLastId() + 1);
         }
         addAlarmMap(new TBAlarmMapBean(number,tbTodoBean.getUuid()));
         intent.putExtra(AlarmReceiver.KEY_NUMBER,number);
         PendingIntent sender = PendingIntent.getBroadcast(
                 context, number, intent, PendingIntent.FLAG_CANCEL_CURRENT);
         //闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
         //int interval = 60 * 1000;
         //am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
         if (am!=null) {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                 am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,tbTodoBean.getTodoRemindTime(), sender);
             }else {
                 am.set(AlarmManager.RTC_WAKEUP, tbTodoBean.getTodoRemindTime(), sender);
             }
         }
     }

     /**
      * 删除闹钟
      * */
     public static void cancelAlarm(Context context, TBTodoBean  tbTodoBean){
         AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
         Intent intent = new Intent(AlarmReceiver.ACTION_KEY);
         intent.setClass(context, AlarmReceiver.class);
         intent.putExtra(AlarmReceiver.KEY_ID,tbTodoBean.getUuid());
         intent.putExtra(AlarmReceiver.KEY_TITLE,tbTodoBean.getTodoTitle());
         intent.putExtra(AlarmReceiver.KEY_CONTENT,tbTodoBean.getTodoContent());

         int number= (int) getIdByKey(tbTodoBean.getUuid());
         intent.putExtra(AlarmReceiver.KEY_NUMBER,number);
         PendingIntent sender = PendingIntent.getBroadcast(
                 context, number, intent, PendingIntent.FLAG_CANCEL_CURRENT);
         //闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
         //int interval = 60 * 1000;
         //am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
         if (am!=null) {
             am.cancel(sender);
         }
     }
    /**
     * 删除闹钟
     * */
    public static void cancelAlarm(Context context, String uuid){

        TBTodoBean tbTodoBean=getTodoBean(uuid);
        if (tbTodoBean==null){
            return;
        }
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AlarmReceiver.ACTION_KEY);
        intent.putExtra(AlarmReceiver.KEY_ID,tbTodoBean.getUuid());
        intent.putExtra(AlarmReceiver.KEY_TITLE,tbTodoBean.getTodoTitle());
        intent.putExtra(AlarmReceiver.KEY_CONTENT,tbTodoBean.getTodoContent());

        int number= (int) getIdByKey(tbTodoBean.getUuid());
        intent.putExtra(AlarmReceiver.KEY_NUMBER,number);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, number, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
        //int interval = 60 * 1000;
        //am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        if (am!=null) {
            am.cancel(sender);
        }
    }

     /**
      * 添加alarm映射
      * */
     public static void addAlarmMap(TBAlarmMapBean tbAlarmMapBean){
         Box<TBAlarmMapBean> tbAlarmMapBeanBox = App.app.getBoxStore().boxFor(TBAlarmMapBean.class);
         if (!TextUtils.isEmpty(tbAlarmMapBean.getKey())){
             TBAlarmMapBean alarmMapBean= tbAlarmMapBeanBox.query().equal(TBAlarmMapBean_.key,tbAlarmMapBean.getKey()).build().findFirst();
             if (alarmMapBean==null) {
                 tbAlarmMapBeanBox.put(tbAlarmMapBean);
             }
         }
     }

     public static int isExistAlarmMap(String uuid){
         Box<TBAlarmMapBean> tbAlarmMapBeanBox = App.app.getBoxStore().boxFor(TBAlarmMapBean.class);
         if (TextUtils.isEmpty(uuid)){
             return 0;
         }
         TBAlarmMapBean alarmMapBean= tbAlarmMapBeanBox.query().equal(TBAlarmMapBean_.key,uuid).build().findFirst();
         if (alarmMapBean==null){
             return 0;
         }
         return alarmMapBean.getNumber();
     }

     /**
      * 获取最后添加的数据 获取最大id
      * */

     public static long getLastId(){
         Box<TBAlarmMapBean> tbAlarmMapBeanBox = App.app.getBoxStore().boxFor(TBAlarmMapBean.class);
         TBAlarmMapBean alarmMapBean=tbAlarmMapBeanBox.query().orderDesc(TBAlarmMapBean_.id).build().findFirst();
         if (alarmMapBean!=null){
             return alarmMapBean.getId();
         }else {
             return 0;
         }
     }

     public static long getIdByKey(String key){
         Box<TBAlarmMapBean> tbAlarmMapBeanBox = App.app.getBoxStore().boxFor(TBAlarmMapBean.class);

         TBAlarmMapBean alarmMapBean= tbAlarmMapBeanBox.query().equal(TBAlarmMapBean_.key,key).build().findFirst();
         if (alarmMapBean!=null){
             return alarmMapBean.getId();
         }else {
             return 0;
         }

     }

}
