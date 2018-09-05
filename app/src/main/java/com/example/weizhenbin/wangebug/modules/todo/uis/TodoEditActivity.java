package com.example.weizhenbin.wangebug.modules.todo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.modules.todo.entity.TodoBean;
import com.example.weizhenbin.wangebug.tools.DialogTool;
import com.example.weizhenbin.wangebug.views.TitleBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.weizhenbin.wangebug.R.id.tb_title;

/**
 * Created by weizhenbin on 18/9/2.
 */

public class TodoEditActivity extends BaseActivity {
    private Calendar cal;
    private int year,month,day;

    private TitleBar tbTitle;
    private TextView tvTime;
    private EditText edTitle;
    private EditText edContent;
    private TextView tvLoc;

    private TodoBean todoBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_edit);
        initViews();
        initEvent();
        initData();
    }

    private void initData() {
        todoBean=new TodoBean();
        cal=Calendar.getInstance();
        Date date=cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
       tvTime.setText(df.format(date));
    }

    private void initEvent() {
        tbTitle.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                DialogTool.showAlertDialog(TodoEditActivity.this, "测试", "测试内容");

            }
        });
        tbTitle.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              DialogTool.showTimeDialog(TodoEditActivity.this);
            }
        });
    }

    private void initViews() {
        tbTitle = (TitleBar) findViewById(tb_title);
        tvTime = (TextView) findViewById(R.id.tv_time);
        edTitle = (EditText) findViewById(R.id.ed_title);
        edContent = (EditText) findViewById(R.id.ed_content);
        tvLoc = (TextView) findViewById(R.id.tv_loc);
    }


    public static void startActivity(Context context){
        context.startActivity(new Intent(context,TodoEditActivity.class));
    }

}

