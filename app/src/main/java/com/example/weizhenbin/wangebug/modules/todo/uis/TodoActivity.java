package com.example.weizhenbin.wangebug.modules.todo.uis;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;

import java.util.Calendar;

/**
 * Created by weizhenbin on 18/9/2.
 */

public class TodoActivity extends BaseActivity {
    private Calendar cal;
    private int year,month,day;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     DatePickerDialog datePickerDialog=new DatePickerDialog(TodoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    }
                });*/
                DatePickerDialog pickerDialog=new DatePickerDialog(TodoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    }
                },year,month,day);
                pickerDialog.show();
            }
        });
    }


    public static void startActivity(Context context){
        context.startActivity(new Intent(context,TodoActivity.class));
    }

}

