package com.example.weizhenbin.wangebug.modules.todo.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoDetailActivity;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool;

/**
 * Created by weizhenbin on 2018/9/26.
 */

public class AlarmReceiver extends BroadcastReceiver{
   public final static String ACTION_KEY="com.example.weizhenbin.wangebug.modules.todo.alarm.action";


   public final static String KEY_NUMBER="number";
   public final static String KEY_ID ="id";
   public final static String KEY_TITLE ="title";
   public final static String KEY_CONTENT ="content";



    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_KEY.equals(intent.getAction())){
            Log.d("AlarmReceiver", "闹钟响了");
            showNotificationIcon(context,intent.getIntExtra(KEY_NUMBER,0),intent.getStringExtra(KEY_ID),intent.getStringExtra(KEY_TITLE),intent.getStringExtra(KEY_CONTENT));
        }

    }

    public static void showNotificationIcon(Context context,int number,String key,String title,String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"aa");
        Intent intent = new Intent(context, TodoDetailActivity.class);//将要跳转的界面
        intent.putExtra("uuid",key);
        builder.setAutoCancel(true);//点击后消失
        if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND,false)){
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
        }
        builder.setTicker("todo");
        if (!TextUtils.isEmpty(title)){
            builder.setContentTitle(title);
        }
        if (!TextUtils.isEmpty(content)) {
            builder.setContentText(content);//通知内容
        }
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        PendingIntent intentPend = PendingIntent.getActivity(context, number, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(intentPend);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager!=null) {
            manager.notify(number, builder.build());
        }
    }

}
