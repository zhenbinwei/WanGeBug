package com.example.weizhenbin.wangebug.modules.todo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
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
        timeStringBuilder=new StringBuilder();
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
             /* DialogTool.showTimeDialog(TodoEditActivity.this, new ITimePickerResult() {
                  @Override
                  public void onTimeResult(int hourOfDay, int minute) {
                      Log.d("TodoEditActivity", "hourOfDay:" + hourOfDay +"  minute:"+minute);
                  }
              });*/
            }
        });
    }

    private void initViews() {
        tbTitle = findViewById(tb_title);
        tvRemindTime =findViewById(R.id.tv_remind_time);
        edTitle =  findViewById(R.id.ed_title);
        edContent =findViewById(R.id.ed_content);
        tvLoc =  findViewById(R.id.tv_loc);
        ivDelRemindTime=findViewById(R.id.iv_del_remind_time);
        ivDelRemindTime.setOnClickListener(this);
        tvRemindTime.setOnClickListener(this);
    }


    public static void startActivity(Context context){
        context.startActivity(new Intent(context,TodoEditActivity.class));
    }

    @Override
    public void onClick(View v) {
        if (v==ivDelRemindTime){
            tvRemindTime.setText(getString(R.string.hint_time_string));
            todoBean.setTodoRemind(false);
            todoBean.setTodoRemindTime(0);
            todoBean.setTodoRemindTimeStr("");
            timeStringBuilder.delete(0,timeStringBuilder.length());
            ivDelRemindTime.setVisibility(View.GONE);
        }else if (v==tvRemindTime){
            DateTool.showDateDialog(TodoEditActivity.this, new IDatePickerResult() {
                @Override
                public void onDateResult(int year, int monthOfYear, int dayOfMonth) {
                    timeStringBuilder.append(year).append("-").append(monthOfYear<10?"0"+monthOfYear:monthOfYear).append("-").append(dayOfMonth<10?"0"+dayOfMonth:dayOfMonth);
                    tvRemindTime.setText(timeStringBuilder.toString());
                    ivDelRemindTime.setVisibility(View.VISIBLE);
                    todoBean.setTodoRemindTimeStr(timeStringBuilder.toString());
                    todoBean.setTodoRemindTime(DateTool.getStringToDate(timeStringBuilder.toString(),"yyyy-MM-dd"));
                    DateTool.showTimeDialog(TodoEditActivity.this, new ITimePickerResult() {
                        @Override
                        public void onTimeResult(int hourOfDay, int minute) {
                            timeStringBuilder.append("   ").append(hourOfDay<10?"0"+hourOfDay:hourOfDay).append(":").append(minute<10?"0"+minute:minute);
                            tvRemindTime.setText(timeStringBuilder.toString());
                            todoBean.setTodoRemindTimeStr(timeStringBuilder.toString());
                            todoBean.setTodoRemindTime(DateTool.getStringToDate(timeStringBuilder.toString(),"yyyy-MM-dd HH:mm"));
                        }
                    });
                }
            });
        }
    }
}

