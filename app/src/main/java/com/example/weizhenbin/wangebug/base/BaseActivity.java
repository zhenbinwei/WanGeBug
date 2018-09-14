package com.example.weizhenbin.wangebug.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by weizhenbin on 2018/8/2.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
          /*  if( OSTool.isMIUI()) {
                StatusTool.setStatusBarDarkMode(true, this);
            }else if(OSTool.isFlyme()){
                StatusTool.setStatusBarDarkIcon(this,true);
            }else if(OSTool.isOPPO()){
                StatusTool.setOPPOLightStatusBarIcon(getWindow(),true);
            }*/
        }
    }
}
