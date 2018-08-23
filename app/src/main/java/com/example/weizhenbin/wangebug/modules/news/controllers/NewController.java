package com.example.weizhenbin.wangebug.modules.news.controllers;

import com.example.weizhenbin.wangebug.base.DataResult;
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean;
import com.example.weizhenbin.wangebug.net.retrofit.HttpHelper;
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.NewsApi;

import java.util.HashMap;

import rx.Observer;

/**
 * Created by weizhenbin on 2018/8/22.
 */

public class NewController {
    public static void getNewsListData(int page,String channelId,final DataResult<YiYuanNewsBean> dataResult){
        if (dataResult!=null){
            dataResult.onStart();
        }
        HashMap<String,String> hashMap=new HashMap();
        hashMap.put("channelId", channelId);
        hashMap.put("channelName","");
        hashMap.put("maxResult","20");
        hashMap.put("needAllList","0");
        hashMap.put("needContent","0");
        hashMap.put("needHtml","1");
        hashMap.put("page",page+"");
        HttpHelper.getHttpHelper().getApi(NewsApi.class).getNewsList(hashMap).
                compose(HttpHelper.<YiYuanNewsBean>setThread()).
                subscribe(new Observer<YiYuanNewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dataResult!=null){
                            dataResult.onError(e);
                        }
                    }

                    @Override
                    public void onNext(YiYuanNewsBean s) {
                        if (dataResult!=null){
                            dataResult.onSuccess(s);
                        }
                    }
                });
    }




}
