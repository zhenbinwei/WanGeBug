package com.example.weizhenbin.wangebug.wxapi;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by weizhenbin on 2018/10/15.
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {


    private IWXAPI iwxapi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi= WXAPIFactory.createWXAPI(this,"",true);
        iwxapi.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

    }
}
