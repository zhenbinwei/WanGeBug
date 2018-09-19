package com.example.weizhenbin.wangebug.modules.todo.uis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.modules.todo.controllers.TodoController;
import com.example.weizhenbin.wangebug.modules.todo.entity.TodoBean;
import com.example.weizhenbin.wangebug.tools.DateTool;
import com.example.weizhenbin.wangebug.tools.DialogTool;
import com.example.weizhenbin.wangebug.tools.interfaces.IDatePickerResult;
import com.example.weizhenbin.wangebug.tools.interfaces.ITimePickerResult;
import com.example.weizhenbin.wangebug.views.TitleBar;

import static com.example.weizhenbin.wangebug.R.id.tb_title;

/**
 * Created by weizhenbin on 18/9/2.
 */

public class TodoEditActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar tbTitle;
    private TextView tvRemindTime;
    private EditText edTitle;
    private EditText edContent;
    private TextView tvLoc;
    private ImageView ivDelRemindTime;
    private TodoBean todoBean;

    private StringBuilder timeStringBuilder;
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
        todoBean.setTodoStatus(0);
        timeStringBuilder=new StringBuilder();
    }

    private void initEvent() {
        ivDelRemindTime.setOnClickListener(this);
        tvRemindTime.setOnClickListener(this);
        tbTitle.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        tbTitle.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTodo();
                finish();
            }
        });
        edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                  todoBean.setTodoContent(s.toString());
            }
        });
        edTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                todoBean.setTodoTitle(s.toString());
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (!todoBean.isEmpty()){
            DialogTool.showAlertDialog(TodoEditActivity.this, null, getString(R.string.save_todo_remind_string), getString(R.string.save_string), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveTodo();
                    finish();
                }
            }, getString(R.string.cancel_string), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }else {
            super.onBackPressed();
        }
    }

    private void saveTodo() {
        long todoCreateTime=System.currentTimeMillis();
        todoBean.setTodoCreateTime(todoCreateTime);
        todoBean.setTodoCreateTimeStr(DateTool.getDateToString(todoCreateTime,"yyyy-MM-dd HH:mm"));
        TodoController.saveTodo(todoBean);
    }

    private void initViews() {
        tbTitle = findViewById(tb_title);
        tvRemindTime =findViewById(R.id.tv_remind_time);
        edTitle =  findViewById(R.id.ed_title);
        edContent =findViewById(R.id.ed_content);
        tvLoc =  findViewById(R.id.tv_loc);
        ivDelRemindTime=findViewById(R.id.iv_del_remind_time);
    }


    public static void startActivity(Context context){
        context.startActivity(new Intent(context,TodoEditActivity.class));
    }

    @Override
    public void onClick(View v) {
        if (v==ivDelRemindTime){
            tvRemindTime.setText(getString(R.string.hint_time_string));
            todoBean.setIsTodoRemind(null);
            todoBean.setTodoRemindTime(null);
            todoBean.setTodoRemindTimeStr(null);
            todoBean.setTodoRemindDate(null);
            todoBean.setTodoRemindDateStr(null);
            timeStringBuilder.delete(0,timeStringBuilder.length());
            ivDelRemindTime.setVisibility(View.GONE);
        }else if (v==tvRemindTime){
            DateTool.showDateDialog(TodoEditActivity.this, new IDatePickerResult() {
                @Override
                public void onDateResult(int year, int monthOfYear, int dayOfMonth) {
                    todoBean.setIsTodoRemind(1);
                    timeStringBuilder.delete(0,timeStringBuilder.length());
                    StringBuffer dateBuffer=new StringBuffer();
                    dateBuffer.append(year).append("-").append(monthOfYear<10?"0"+monthOfYear:monthOfYear).append("-").append(dayOfMonth<10?"0"+dayOfMonth:dayOfMonth);
                    timeStringBuilder.append(dateBuffer.toString());
                    tvRemindTime.setText(timeStringBuilder.toString());
                    ivDelRemindTime.setVisibility(View.VISIBLE);
                    todoBean.setTodoRemindDateStr(dateBuffer.toString());
                    todoBean.setTodoRemindDate(DateTool.getStringToDate(dateBuffer.toString(),"yyyy-MM-dd"));
                    todoBean.setTodoRemindTimeStr(dateBuffer.toString());
                    todoBean.setTodoRemindTime(DateTool.getStringToDate(dateBuffer.toString(),"yyyy-MM-dd"));
                    DateTool.showTimeDialog(TodoEditActivity.this, new ITimePickerResult() {
                        @Override
                        public void onTimeResult(int hourOfDay, int minute) {
                            StringBuffer timeBuffer=new StringBuffer();
                            timeBuffer.append(hourOfDay<10?"0"+hourOfDay:hourOfDay).append(":").append(minute<10?"0"+minute:minute);
                            timeStringBuilder.append("   ").append(timeBuffer.toString());
                            tvRemindTime.setText(timeStringBuilder.toString());
                            todoBean.setTodoRemindTimeStr(timeBuffer.toString());
                            todoBean.setTodoRemindTime(DateTool.getStringToDate(timeStringBuilder.toString(),"yyyy-MM-dd HH:mm"));
                        }
                    });
                }
            });
        }
    }
}

