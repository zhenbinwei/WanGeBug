package com.example.weizhenbin.wangebug.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler;
import com.example.weizhenbin.wangebug.eventbus.EventCode;
import com.example.weizhenbin.wangebug.eventbus.MessageEvent;
import com.example.weizhenbin.wangebug.modules.collect.controllers.CollectController;
import com.example.weizhenbin.wangebug.modules.collect.entity.TBCollectBean;
import com.example.weizhenbin.wangebug.tools.PhoneTool;
import com.example.weizhenbin.wangebug.tools.UUIDTool;
import com.example.weizhenbin.wangebug.views.ListPopupWindow;
import com.example.weizhenbin.wangebug.views.TitleBar;
import com.example.weizhenbin.wangebug.views.WebViewLayout;

/**
 * Created by weizhenbin on 2018/8/24.
 */

public class WebActivity extends BaseActivity {
    WebViewLayout wbLayout;
    String url;
    TitleBar titleBar;
    String[] items;
    String mTitle;
    UrlTypeEnum urlTypeEnum;
    boolean isCollect=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_web);
        initViews();
        initEvent();
        initData();
    }

    private void initData() {
        items=new String[]{getString(R.string.collect_string),getString(R.string.share_string),getString(R.string.browser_open_string)};
        url=getIntent().getStringExtra("Url");
        mTitle=getIntent().getStringExtra("title");
        urlTypeEnum= (UrlTypeEnum) getIntent().getSerializableExtra("UrlType");
        wbLayout.setUrl(url);
        titleBar.setTitle(mTitle);
        isCollect=CollectController.isExistByTitle(mTitle);
    }

    private void initViews(){
        wbLayout=findViewById(R.id.wb_layout);
        titleBar=findViewById(R.id.tb_title);
    }
    private void initEvent(){
        wbLayout.addEventCallBack(new WebViewLayout.IEventCallBack() {
            @Override
            public void onReceivedTitle(String title) {
                if (TextUtils.isEmpty(mTitle)) {
                    titleBar.setTitle(title);
                    mTitle=title;
                }
            }
        });
        titleBar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollect){
                    items[0]=getString(R.string.cancel_collect_string);
                }else {
                    items[0]=getString(R.string.collect_string);
                }
                new ListPopupWindow(WebActivity.this,items).setItemListener(new ListPopupWindow.IItemListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position){
                            case 0:
                                if (isCollect){
                                    CollectController.removeCollectByTitle(mTitle);
                                    isCollect=false;
                                    EventBusHandler.post(new MessageEvent(EventCode.CANCEL_COLLECT_CODE,mTitle));
                                }else {
                                    TBCollectBean tbCollectBean = new TBCollectBean();
                                    tbCollectBean.setTitle(mTitle);
                                    tbCollectBean.setUrl(url);
                                    tbCollectBean.setKey(UUIDTool.getUUID());
                                    tbCollectBean.setCollectTime(System.currentTimeMillis());
                                    tbCollectBean.setType(urlTypeEnum.getTypeValue());
                                    CollectController.addCollect(tbCollectBean);
                                    isCollect=true;
                                    EventBusHandler.post(new MessageEvent(EventCode.ADD_COLLECT_CODE,tbCollectBean));
                                }
                                break;
                            case 1:
                                break;
                            case 2:
                                Uri uri = Uri.parse(url);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                                break;
                        }


                    }
                }).showAsDropDown(v,-PhoneTool.dip2px(8),-PhoneTool.dip2px(8), Gravity.END);
            }
        });
    }

    public static void startActivity(Context context,String url,String title,UrlTypeEnum urlTypeEnum){
        if (TextUtils.isEmpty(url)){
            return;
        }
        Intent intent=new Intent(context,WebActivity.class);
        intent.putExtra("Url",url);
        intent.putExtra("UrlType",urlTypeEnum);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (event.getAction()==KeyEvent.KEYCODE_BACK){
            return wbLayout.back();
        }
        return super.onKeyUp(keyCode, event);
    }
}
