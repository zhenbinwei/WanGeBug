package com.example.weizhenbin.wangebug;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.tools.permission.PermissionTool;
import com.example.weizhenbin.wangebug.views.floatingwindow.FloatingView;
import com.example.weizhenbin.wangebug.views.floatingwindow.FloatingWindow;

/**
 * Created by weizhenbin on 2018/9/11.
 */

public class TestActivity extends BaseActivity {

    FloatingWindow floatingWindow;
    FloatingView fv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        floatingWindow=new FloatingWindow();
        fv=findViewById(R.id.fv);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //fv.zoomIn(fv.getWidth()/2,fv.getHeight()/2,50,null);
                Log.d("TestActivity", "CAMERA:" + PermissionTool.checkPermission(TestActivity.this, Manifest.permission.CAMERA));


               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PermissionTool.setOverlayPermission(TestActivity.this);
                }*/

               /* PermissionTool.with(TestActivity.this).setiPermissionGrantResult(new IPermissionGrantResult() {
                    @Override
                    public void onGrantResult(@NonNull String[] permissions, @NonNull int[] grantResults) {

                    }
                }).requestPermissions(new String[]{Manifest.permission.CAMERA});*/



                floatingWindow.addFloatingWindow();

              //  Toast.makeText(TestActivity.this,"测试点击穿透",Toast.LENGTH_LONG).show();

            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingWindow.removeFloatingWindow();
              //  fv.zoomOut(null);
            }
        });
    }
}
