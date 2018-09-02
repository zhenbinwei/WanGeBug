package com.example.weizhenbin.wangebug.modules.todo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;

/**
 * Created by weizhenbin on 18/9/2.
 */

public class TodoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
    }


    public static void startActivity(Context context){
        context.startActivity(new Intent(context,TodoActivity.class));
    }

}

