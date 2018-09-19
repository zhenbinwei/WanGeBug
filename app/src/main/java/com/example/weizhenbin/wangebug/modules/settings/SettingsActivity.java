package com.example.weizhenbin.wangebug.modules.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.tools.DialogTool;
import com.example.weizhenbin.wangebug.tools.permission.IFloattingWindowPermissionGrantResult;
import com.example.weizhenbin.wangebug.tools.permission.PermissionTool;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool;
import com.example.weizhenbin.wangebug.views.TitleBar;
import com.example.weizhenbin.wangebug.views.floatingwindow.TodoFloatingWindowManager;


/**
 * Created by weizhenbin on 2018/9/18.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener{


    AppCompatCheckBox cbOpenTodo;
    TitleBar tbTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        initEvent();
        initStatus();
    }

    private void initStatus() {
        if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_FLOATTING_WINDOW,false)){
            cbOpenTodo.setChecked(true);
        }else {
            cbOpenTodo.setChecked(false);
        }
    }

    private void initEvent() {
        cbOpenTodo.setOnClickListener(this);
        tbTitle.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });
    }

    private void initViews() {
        cbOpenTodo=findViewById(R.id.cb_todo_open);
        tbTitle=findViewById(R.id.tb_title);
    }


    public static void startActivity(Context context){
        context.startActivity(new Intent(context,SettingsActivity.class));
    }

    @Override
    public void onClick(View v) {
        if (v==cbOpenTodo){
            if (cbOpenTodo.isChecked()) {
                openFloattingWindow();
            }else {
                closeFloattingWindow();
            }
        }
    }


    private void closeFloattingWindow() {
        cbOpenTodo.setChecked(false);
        PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATTING_WINDOW,false);
        TodoFloatingWindowManager.getManager().hideFloatingWindow();
    }

    private void openFloattingWindow() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //6.0以上必须开启悬浮窗权限
            if (PermissionTool.checkWindowPermission(SettingsActivity.this)){
                 cbOpenTodo.setChecked(true);
                 PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATTING_WINDOW,true);
                 TodoFloatingWindowManager.getManager().showFloatingWindow();
            }else {
                //引导权限申请
                DialogTool.showAlertDialog(SettingsActivity.this, getString(R.string.permission_request_string), getString(R.string.floatting_window_permission_request_describe_string), getString(R.string.open_string), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionTool.with(SettingsActivity.this).setiFloattingWindowPermissionGrantResult(new IFloattingWindowPermissionGrantResult() {
                            @Override
                            public void onGrantResult(boolean isGrant) {
                                if (PermissionTool.checkWindowPermission(SettingsActivity.this)){
                                    cbOpenTodo.setChecked(true);
                                    PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATTING_WINDOW,true);
                                    TodoFloatingWindowManager.getManager().showFloatingWindow();
                                }else {
                                    cbOpenTodo.setChecked(false);
                                    PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATTING_WINDOW,false);
                                    TodoFloatingWindowManager.getManager().hideFloatingWindow();
                                }
                            }
                        }).requestFloattingWindowPermission();
                    }
                }, getString(R.string.cancel_string), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cbOpenTodo.setChecked(false);
                        PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATTING_WINDOW,false);
                        TodoFloatingWindowManager.getManager().hideFloatingWindow();
                    }
                },false);
            }
        }else {
            cbOpenTodo.setChecked(true);
            PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATTING_WINDOW,true);
            TodoFloatingWindowManager.getManager().showFloatingWindow();
        }
    }




}
