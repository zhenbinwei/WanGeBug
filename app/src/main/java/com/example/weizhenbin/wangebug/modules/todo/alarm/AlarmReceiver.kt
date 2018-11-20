package com.example.weizhenbin.wangebug.modules.todo.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import android.text.TextUtils
import android.util.Log

import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoDetailActivity
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool

/**
 * Created by weizhenbin on 2018/9/26.
 */

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        if (ACTION_KEY == intent.action) {
            Log.d("AlarmReceiver", "闹钟响了")
            showNotificationIcon(context, intent.getIntExtra(KEY_NUMBER, 0), intent.getStringExtra(KEY_ID), intent.getStringExtra(KEY_TITLE), intent.getStringExtra(KEY_CONTENT))
        }

    }

    companion object {
      const  val ACTION_KEY = "com.example.weizhenbin.wangebug.modules.todo.alarm.action"


      const  val KEY_NUMBER = "number"
      const  val KEY_ID = "id"
      const  val KEY_TITLE = "title"
     const   val KEY_CONTENT = "content"

        fun showNotificationIcon(context: Context, number: Int, key: String, title: String, content: String) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel: NotificationChannel
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND, false)) {
                    channel = NotificationChannel("1",
                            "todo", NotificationManager.IMPORTANCE_HIGH)
                } else {
                    channel = NotificationChannel("1",
                            "todo", NotificationManager.IMPORTANCE_LOW)
                }
                channel.enableLights(true) //是否在桌面icon右上角展示小红点
                channel.lightColor = Color.GREEN //小红点颜色
                channel.setShowBadge(true) //是否在久按桌面图标时显示此渠道的通知
                manager.createNotificationChannel(channel)
            }
            val builder: NotificationCompat.Builder=if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                NotificationCompat.Builder(context, "1")
            }else{
                NotificationCompat.Builder(context)
            }
            val intent = Intent(context, TodoDetailActivity::class.java)//将要跳转的界面
            intent.putExtra("uuid", key)
            builder.setAutoCancel(true)//点击后消失
            if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND, false)) {
                builder.setDefaults(NotificationCompat.DEFAULT_SOUND)//设置通知铃声
            }
            builder.setTicker("todo")
            if (!TextUtils.isEmpty(title)) {
                builder.setContentTitle(title)
            }
            if (!TextUtils.isEmpty(content)) {
                builder.setContentText(content)//通知内容
            }
            builder.setSmallIcon(R.mipmap.logo_t)
            //利用PendingIntent来包装我们的intent对象,使其延迟跳转
            val intentPend = PendingIntent.getActivity(context, number, intent, PendingIntent.FLAG_CANCEL_CURRENT)
            builder.setContentIntent(intentPend)

            manager.notify(number, builder.build())
        }
    }

}
