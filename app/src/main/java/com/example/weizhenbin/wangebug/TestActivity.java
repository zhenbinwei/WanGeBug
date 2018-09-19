package com.example.weizhenbin.wangebug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.views.floatingwindow.FloatingView;
import com.example.weizhenbin.wangebug.views.floatingwindow.FloatingWindow;
import com.example.weizhenbin.wangebug.views.remindbar.RemindBar;

/**
 * Created by weizhenbin on 2018/9/11.
 */

public class TestActivity extends BaseActivity {

    FloatingWindow floatingWindow;
    FloatingView fv;
    //RemindBarLayout remindBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        floatingWindow=new FloatingWindow();
        fv=findViewById(R.id.fv);
        floatingWindow.addRealContentView(View.inflate(TestActivity.this,R.layout.floating_window_todo_edit_view,null));
      /*  remindBar=new RemindBarLayout(fv);
        remindBar.setMarginBottom(200);*/
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
                RemindBar.make(v,"测试", RemindBar.LENGTH_LONG).setAction("测试1", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();


            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // floatingWindow.removeFloatingWindow();
              //  fv.zoomOut(null);
               // TodoFloatingWindowManager.getManager().hideFloatingWindow();
               // remindBar.remove();
            }
        });
    }
}
