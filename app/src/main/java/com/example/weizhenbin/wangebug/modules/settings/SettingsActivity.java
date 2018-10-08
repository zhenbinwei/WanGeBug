package com.example.weizhenbin.wangebug.modules.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler;
import com.example.weizhenbin.wangebug.eventbus.EventCode;
import com.example.weizhenbin.wangebug.eventbus.MessageEvent;
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


    AppCompatCheckBox cbTodoOpen;
    AppCompatCheckBox cbHideTabOpen;
    AppCompatCheckBox cbTodoNotificationSoundOpen;
    TitleBar tbTitle;
    TextView tvCleanCache;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();
        initEvent();
        initStatus();
    }

    private void initStatus() {
        if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW,false)){
            cbTodoOpen.setChecked(true);
        }else {
            cbTodoOpen.setChecked(false);
        }
        if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_HIDE_TAB,true)){
            cbHideTabOpen.setChecked(true);
        }else {
            cbHideTabOpen.setChecked(false);
        }
        if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND,false)){
            cbHideTabOpen.setChecked(true);
        }else {
            cbHideTabOpen.setChecked(false);
        }
    }

    private void initEvent() {
        cbTodoOpen.setOnClickListener(this);
        cbHideTabOpen.setOnClickListener(this);
        tbTitle.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });
        tvCleanCache.setOnClickListener(this);
        cbTodoNotificationSoundOpen.setOnClickListener(this);
    }

    private void initViews() {
        cbTodoOpen =findViewById(R.id.cb_todo_open);
        cbHideTabOpen=findViewById(R.id.cb_hide_tab_open);
        tbTitle=findViewById(R.id.tb_title);
        tvCleanCache=findViewById(R.id.tv_clean_cache);
        cbTodoNotificationSoundOpen=findViewById(R.id.cb_todo_notification_sound_open);
    }


    public static void startActivity(Context context){
        context.startActivity(new Intent(context,SettingsActivity.class));
    }

    @Override
    public void onClick(View v) {
        if (v== cbTodoOpen){
            if (cbTodoOpen.isChecked()) {
                openFloatingWindow();
            }else {
                closeFloatingWindow();
            }
        }else if (v==cbHideTabOpen){
            if (cbHideTabOpen.isChecked()){
                openHideTab();
            }else {
                closeHideTab();
            }
        }else if (v==tvCleanCache){

        }else if (v==cbTodoNotificationSoundOpen){
            if (cbTodoNotificationSoundOpen.isChecked()){
                PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND, true);
            }else {
                PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND, false);
            }
        }
    }

    private void closeHideTab() {
        PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_HIDE_TAB,false);
        cbHideTabOpen.setChecked(false);
        EventBusHandler.post(new MessageEvent(EventCode.HIDE_TAB_CODE,false));
    }

    private void openHideTab() {
        PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_HIDE_TAB,true);
        cbHideTabOpen.setChecked(true);
        EventBusHandler.post(new MessageEvent(EventCode.HIDE_TAB_CODE,true));
    }


    private void closeFloatingWindow() {
        cbTodoOpen.setChecked(false);
        PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW,false);
        TodoFloatingWindowManager.getManager().hideFloatingWindow();
    }

    private void openFloatingWindow() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //6.0以上必须开启悬浮窗权限
            if (PermissionTool.checkWindowPermission(SettingsActivity.this)){
                 cbTodoOpen.setChecked(true);
                 PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW,true);
                 TodoFloatingWindowManager.getManager().showFloatingWindow();
            }else {
                //引导权限申请
                DialogTool.showAlertDialog(SettingsActivity.this, getString(R.string.permission_request_string), getString(R.string.floating_window_permission_request_describe_string), getString(R.string.open_string), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionTool.with(SettingsActivity.this).setiFloattingWindowPermissionGrantResult(new IFloattingWindowPermissionGrantResult() {
                            @Override
                            public void onGrantResult(boolean isGrant) {
                                if (PermissionTool.checkWindowPermission(SettingsActivity.this)){
                                    cbTodoOpen.setChecked(true);
                                    PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW,true);
                                    TodoFloatingWindowManager.getManager().showFloatingWindow();
                                }else {
                                    cbTodoOpen.setChecked(false);
                                    PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW,false);
                                    TodoFloatingWindowManager.getManager().hideFloatingWindow();
                                }
                            }
                        }).requestFloattingWindowPermission();
                    }
                }, getString(R.string.cancel_string), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cbTodoOpen.setChecked(false);
                        PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW,false);
                        TodoFloatingWindowManager.getManager().hideFloatingWindow();
                    }
                },false);
            }
        }else {
            cbTodoOpen.setChecked(true);
            PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW,true);
            TodoFloatingWindowManager.getManager().showFloatingWindow();
        }
    }




}
