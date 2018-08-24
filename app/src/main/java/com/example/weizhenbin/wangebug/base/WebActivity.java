package com.example.weizhenbin.wangebug.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.views.TitleBar;
import com.example.weizhenbin.wangebug.views.WebViewLayout;

/**
 * Created by weizhenbin on 2018/8/24.
 */

public class WebActivity extends BaseActivity {
    WebViewLayout wbLayout;
    String url;
    TitleBar titleBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        url=getIntent().getStringExtra("Url");
        wbLayout=findViewById(R.id.wb_layout);
        titleBar=findViewById(R.id.tb_title);
        wbLayout.addEventCallBack(new WebViewLayout.IEventCallBack() {
            @Override
            public void onReceivedTitle(String title) {
                titleBar.setTitle(title);
            }
        });
        wbLayout.setUrl(url);
    }


    public static void startActivity(Context context,String url){
        if (TextUtils.isEmpty(url)){
            return;
        }
        Intent intent=new Intent(context,WebActivity.class);
        intent.putExtra("Url",url);
        context.startActivity(intent);
    }

}
