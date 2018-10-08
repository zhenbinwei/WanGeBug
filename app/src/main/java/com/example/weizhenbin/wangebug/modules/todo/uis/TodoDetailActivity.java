package com.example.weizhenbin.wangebug.modules.todo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.modules.todo.controllers.TodoController;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean;
import com.example.weizhenbin.wangebug.views.TitleBar;

/**
 * Created by weizhenbin on 2018/9/21.
 */

public class TodoDetailActivity extends BaseActivity {
    private TitleBar tbTitle;
    private TextView tvDoneTime;
    private TextView tvTitle;
    private TextView tvContent;


    private TBTodoBean tbTodoBean;



    private void initViews() {
        tbTitle = findViewById(R.id.tb_title);
        tvDoneTime =  findViewById(R.id.tv_done_time);
        tvTitle = findViewById(R.id.tv_title);
        tvContent =  findViewById(R.id.tv_content);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_done);
        initViews();
        Intent intent=getIntent();
        if (intent.hasExtra("uuid")){
            tbTodoBean= TodoController.getTodoBean(intent.getStringExtra("uuid"));
        }
        initData();
        initEvent();
    }

    private void initEvent() {
        tbTitle.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        if (tbTodoBean!=null){
            tvTitle.setText(tbTodoBean.getTodoTitle());
            tvContent.setText(tbTodoBean.getTodoContent());
            tvDoneTime.setText(tbTodoBean.getTodoDoneTimeStr());
        }
    }


    public static void startActivity(Context context, String uuid){
        Intent intent=new Intent(context,TodoDetailActivity.class);
        intent.putExtra("uuid",uuid);
        context.startActivity(intent);
    }
}
