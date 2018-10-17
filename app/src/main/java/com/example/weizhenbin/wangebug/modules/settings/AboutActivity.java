package com.example.weizhenbin.wangebug.modules.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;

/**
 * Created by weizhenbin on 2018/10/17.
 */
public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

    }

 public static void startActivity (Context context){
        if(context==null){
        return;
        }
        Intent intent = new Intent(context,AboutActivity.class);
        context.startActivity(intent);
 }





}
