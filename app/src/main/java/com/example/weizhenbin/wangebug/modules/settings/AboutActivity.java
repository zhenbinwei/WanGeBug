package com.example.weizhenbin.wangebug.modules.settings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.views.TitleBar;

/**
 * Created by weizhenbin on 2018/10/17.
 */
public class AboutActivity extends BaseActivity {
    TextView tvVersion;
    TitleBar tbTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initViews();
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
        String name = getVersionName();
        tvVersion.setText(getString(R.string.version_string,name));
    }

    @Nullable
    private String getVersionName() {
        PackageManager manager = getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    private void initViews() {
        tvVersion=findViewById(R.id.tv_version);
        tbTitle=findViewById(R.id.tb_title);
    }

    public static void startActivity (Context context){
        if(context==null){
        return;
        }
        Intent intent = new Intent(context,AboutActivity.class);
        context.startActivity(intent);
 }





}
