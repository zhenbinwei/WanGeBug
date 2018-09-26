package com.example.weizhenbin.wangebug.modules.todo.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.TestActivity;

/**
 * Created by weizhenbin on 2018/9/26.
 */

public class AlarmReceiver extends BroadcastReceiver{
   public final static String action="com.example.weizhenbin.wangebug.modules.todo.alarm.action";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (action.equals(intent.getAction())){

            Log.d("AlarmReceiver", "闹钟响了");
            Log.d("AlarmReceiver", intent.getStringExtra("key"));
          /*  NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,"aa");
            mBuilder.setContentTitle("測试标题")//设置通知栏标题
                    .setContentText("測试内容");//设置通知栏显示内容
                 //   .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
//	.setNumber(number) //设置通知集合的数量
                   // .setTicker("測试通知来啦") //通知首次出如今通知栏，带上升动画效果的
                    //.setWhen(System.currentTimeMillis())//通知产生的时间。会在通知信息里显示，通常是系统获取到的时间
                    //.setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//	.setAutoCancel(true)//设置这个标志当用户单击面板就能够让通知将自己主动取消
                    //.setOngoing(false)//ture。设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极參与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                  //  .setDefaults(Notification.DEFAULT_VIBRATE);//向通知加入声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，能够组合
                    //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 加入声音 // requires VIBRATE permission
               //     .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON
            mNotificationManager.notify(1,mBuilder.build());*/
            showNotifictionIcon(context);
        }

    }

    public static void showNotifictionIcon(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"aa");
        Intent intent = new Intent(context, TestActivity.class);//将要跳转的界面
        builder.setAutoCancel(true);//点击后消失
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
        builder.setTicker("你好");
        builder.setContentText("今天天气真好");//通知内容
        builder.setContentTitle("天气");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        PendingIntent intentPend = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(intentPend);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}
