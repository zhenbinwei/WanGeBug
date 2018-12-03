package com.example.weizhenbin.wangebug

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.weizhenbin.floatingview.FloatingWindow
import com.example.weizhenbin.wangebug.base.BaseActivity
import com.example.weizhenbin.wangebug.image.ImageLoadListenerAdapter
import com.example.weizhenbin.wangebug.image.ImageLoader
import com.example.weizhenbin.wangebug.modules.todo.alarm.AlarmReceiver
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool

/**
 * Created by weizhenbin on 2018/9/11.
 */

class TestActivity : BaseActivity() {

    lateinit var floatingWindow: FloatingWindow
    //RemindBarLayout remindBar;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        floatingWindow = FloatingWindow(this)
        floatingWindow.addRealContentView(View.inflate(this@TestActivity, R.layout.floating_window_todo_edit_view, null))
        /*  remindBar=new RemindBarLayout(fv);
        remindBar.setMarginBottom(200);*/



        findViewById<View>(R.id.bt).setOnClickListener {
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
            android.app.AlertDialog.Builder(this@TestActivity).setMessage("测试").create().show()
        }

        findViewById<View>(R.id.bt2).setOnClickListener {
            // floatingWindow.removeFloatingWindow();
            //  fv.zoomOut(null);
            // TodoFloatingWindowManager.getManager().hideFloatingWindow();
            // remindBar.remove();
            // Box<Note>   notesBox = ((App) getApplication()).getBoxStore().boxFor(Note.class);
            // notesBox.
            /*    new CustomDialog.Builder(TestActivity.this).setItems(new String[]{"测试1", "测试2"}, new CustomDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();*/
            notification()
        }
    }

    private fun getStrings(text: String): Array<String> {
        return text.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }


    private fun setAlarmTime(context: Context, triggerAtMillis: Long) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(AlarmReceiver.ACTION_KEY)
        intent.setClass(this, AlarmReceiver::class.java)
        intent.putExtra("key", "测试")
        val sender = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        //闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
        //int interval = 60 * 1000;
        //am.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        am.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, sender)
    }


    private fun notification() {
        val manager : NotificationManager= this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel: NotificationChannel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND, false)) {
                NotificationChannel("1",
                        "todo", NotificationManager.IMPORTANCE_HIGH)
            } else {
                NotificationChannel("1",
                        "todo", NotificationManager.IMPORTANCE_LOW)
            }
            channel.enableLights(true) //是否在桌面icon右上角展示小红点
            channel.lightColor = Color.GREEN //小红点颜色
            channel.setShowBadge(true) //是否在久按桌面图标时显示此渠道的通知
            manager.createNotificationChannel(channel)
        }
        val builder = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationCompat.Builder(this, "1")
        } else {
            NotificationCompat.Builder(this)
        }

        val intent = Intent(this, TestActivity::class.java)//将要跳转的界面
        builder.setAutoCancel(true)//点击后消失
        if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND, false)) {
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND)//设置通知铃声
        } else {
            builder.setSound(null)
        }
        builder.setTicker("todo")
        builder.setContentTitle("测试")
        builder.setContentText("测试")//通知内容
        builder.setSmallIcon(R.mipmap.logo_t)
        //  builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        val intentPend = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        builder.setContentIntent(intentPend)

        manager.notify(0, builder.build())

        val url = "http://s03.alpha.lmbang.com/M00/01/AF/wKgBA1e1bpaAUv8NAACR-Ryp7hI556.jpg!c160x160"
        ImageLoader.getImageLoader().loadBitmap(this, url, object : ImageLoadListenerAdapter() {
            override fun onLoadSuccess(bitmap: Bitmap, url: String) {
                super.onLoadSuccess(bitmap, url)
                Log.d("TestActivity", "bitmap:$bitmap")
                Toast.makeText(this@TestActivity, "图片下载成功", Toast.LENGTH_LONG).show()
                builder.setLargeIcon(bitmap)
                manager.notify(0, builder.build())
            }
        })
    }


    private inner class MyClickableSpan(internal var position: Int, internal var str: String, internal var startIndex: Int, internal var endIndex: Int) : ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.color = Color.BLACK
            Log.d("MyClickableSpan", "ds:$ds")
        }

        override fun onClick(widget: View) {
            Log.d("MyClickableSpan", "点击")
        }
    }

}
