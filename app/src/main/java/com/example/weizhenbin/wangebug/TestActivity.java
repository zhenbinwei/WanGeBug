package com.example.weizhenbin.wangebug;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.modules.todo.alarm.AlarmReceiver;
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoDetailActivity;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool;
import com.example.weizhenbin.wangebug.views.CustomDialog;
import com.example.weizhenbin.wangebug.views.MyTextView;
import com.example.weizhenbin.wangebug.views.floatingwindow.FloatingView;
import com.example.weizhenbin.wangebug.views.floatingwindow.FloatingWindow;

/**
 * Created by weizhenbin on 2018/9/11.
 */

public class TestActivity extends BaseActivity {

    FloatingWindow floatingWindow;
    FloatingView fv;
    //RemindBarLayout remindBar;
    MyTextView myTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        floatingWindow=new FloatingWindow();
        fv=findViewById(R.id.fv);
        floatingWindow.addRealContentView(View.inflate(TestActivity.this,R.layout.floating_window_todo_edit_view,null));
      /*  remindBar=new RemindBarLayout(fv);
        remindBar.setMarginBottom(200);*/
      myTextView=findViewById(R.id.tv_tags);



        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //fv.zoomIn(fv.getWidth()/2,fv.getHeight()/2,50,null);
              //  Log.d("TestActivity", "CAMERA:" + PermissionTool.checkPermission(TestActivity.this, Manifest.permission.CAMERA));


               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PermissionTool.setOverlayPermission(TestActivity.this);
                }*/

               /* PermissionTool.with(TestActivity.this).setiPermissionGrantResult(new IPermissionGrantResult() {
                    @Override
                    public void onGrantResult(@NonNull String[] permissions, @NonNull int[] grantResults) {

                    }
                }).requestPermissions(new String[]{Manifest.permission.CAMERA});*/



              //  floatingWindow.addFloatingWindow();

              //  Toast.makeText(TestActivity.this,"测试点击穿透",Toast.LENGTH_LONG).show();
              //  TodoFloatingWindowManager.getManager().showFloatingWindow();
              /*  remindBar.setActionText("测试");
                remindBar.setMsgText("测试内容");
                remindBar.setActionClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TestActivity", "点击了");
                    }
                });
                remindBar.show();*/

             // RemindBarLayout.make(v,"测试", RemindBarLayout.LENGTH_LONG).show();
               /* RemindBar.make(v,"测试", RemindBar.LENGTH_LONG).setAction("测试1", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();*/

             /*   String s1="测试 测试 测试内容 测试 测试内容 我是测试内容 内容 测试 测试 ";
                StringBuilder stringBuilder=new StringBuilder();

                String[] strings=getStrings(s1.toString());

                int lineWidth=0;
                //多加一个字的宽度 避开恰好到了text自动换行 变成了双换行\
                for (int i = 0; i < strings.length; i++) {
                    String s=strings[i];
                    if ((lineWidth+myTextView.getPaint().measureText(s+"字"))>myTextView.getWidth()){
                        lineWidth= 0;
                        myTextView.append("\n");
                    }
                    MyClickableSpan span=new MyClickableSpan(i,s,0,s.length());
                    SpannableString spannableString=new SpannableString(s);
                    spannableString.setSpan(span,span.startIndex,span.endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    myTextView.append(spannableString);
                    myTextView.append(" ");
                    lineWidth+=myTextView.getPaint().measureText(s+" ");
                }
                myTextView.setMovementMethod(LinkMovementMethod.getInstance());*/


              //  UUID uuid=UUID.randomUUID();
               // Log.d("TestActivity", "uuid:" + uuid.toString().replaceAll("-", ""));
               /* DialogTool.showListAlertDialog(TestActivity.this, new String[]{"删除","编辑为完成"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });*/

              // setAlarmTime(TestActivity.this,System.currentTimeMillis()+15*1000);
              //  notification();
               // CrashReport.testJavaCrash();

              /*  DialogTool.showAlertDialog(TestActivity.this, "测试", "测试", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });*/
              //  new CustomDialog(TestActivity.this).show();
             /*   new CustomDialog.Builder(TestActivity.this).setItems(new String[]{"测试1", "测试2"}, new CustomDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();*/
           /*  new CustomDialog.Builder(TestActivity.this).setMessage("测试").setPositiveBtnStr("确定", new CustomDialog.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                 }
             }).create().show();*/
           new android.app.AlertDialog.Builder(TestActivity.this).setMessage("测试").create().show();
            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // floatingWindow.removeFloatingWindow();
              //  fv.zoomOut(null);
               // TodoFloatingWindowManager.getManager().hideFloatingWindow();
               // remindBar.remove();
               // Box<Note>   notesBox = ((App) getApplication()).getBoxStore().boxFor(Note.class);
               // notesBox.
                new CustomDialog.Builder(TestActivity.this).setItems(new String[]{"测试1", "测试2"}, new CustomDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();

            }
        });
    }
    private String[] getStrings(String text){
        return  text.split(" ");
    }


    private void setAlarmTime(Context context, long triggerAtMillis) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AlarmReceiver.ACTION_KEY);
        intent.setClass(this, AlarmReceiver.class);
        intent.putExtra("key","测试");
        PendingIntent sender = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
        //int interval = 60 * 1000;
        //am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        am.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender);
    }


    private void notification(){
        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND,false)){
                channel = new NotificationChannel("1",
                        "todo", NotificationManager.IMPORTANCE_HIGH);
            }else {
                channel = new NotificationChannel("1",
                        "todo", NotificationManager.IMPORTANCE_LOW);
            }
            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN); //小红点颜色
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            if (manager!=null) {
                manager.createNotificationChannel(channel);
            }
        }
        NotificationCompat.Builder builder ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder= new NotificationCompat.Builder(this,"1");
        }else {
            builder= new NotificationCompat.Builder(this);
        }

        Intent intent = new Intent(this, TodoDetailActivity.class);//将要跳转的界面
        builder.setAutoCancel(true);//点击后消失
        if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND,false)){
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);//设置通知铃声
        }else {
            builder.setSound(null);
        }
        builder.setTicker("todo");
        builder.setContentTitle("测试");
        builder.setContentText("测试");//通知内容
        builder.setSmallIcon(R.mipmap.logo_t);
        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        PendingIntent intentPend = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(intentPend);

        if (manager!=null) {
            manager.notify(0, builder.build());
        }
    }


    private class MyClickableSpan extends ClickableSpan {
        int position;

        String str;

        int startIndex;

        int endIndex;

        public MyClickableSpan(int position, String str, int startIndex, int endIndex) {
            this.position = position;
            this.str = str;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(Color.BLACK);
            Log.d("MyClickableSpan", "ds:" + ds);
        }

        @Override
        public void onClick(View widget) {
            Log.d("MyClickableSpan", "点击");
        }
    }

}
