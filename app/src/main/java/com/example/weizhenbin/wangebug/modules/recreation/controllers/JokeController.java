package com.example.weizhenbin.wangebug.modules.recreation.controllers;

import com.example.weizhenbin.wangebug.base.DataResult;
import com.example.weizhenbin.wangebug.modules.recreation.entity.PicJokeBean;
import com.example.weizhenbin.wangebug.modules.recreation.entity.TextJokeBean;
import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanPicBean;
import com.example.weizhenbin.wangebug.net.retrofit.HttpHelper;
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.RecreationApi;
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.YiYuanApi;
import com.example.weizhenbin.wangebug.net.retrofit.entity.AliBean;

import java.util.HashMap;

import rx.Observer;

/**
 * Created by weizhenbin on 2018/8/13.
 */

public class JokeController {




    /**获取文本笑话
     * */
    public static void getTextJoke(int page,final DataResult<AliBean<TextJokeBean>> dataResult){
        if (dataResult!=null){
            dataResult.onStart();
        }
        HttpHelper.getHttpHelper()
                .getApi(RecreationApi.class)
                .getTextJoke("20",""+page,"")
                .compose(HttpHelper.<AliBean<TextJokeBean>>setThread())
                .subscribe(new Observer<AliBean<TextJokeBean>>() {
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
                    public void onNext(AliBean<TextJokeBean> textJokeBeanAliBean) {
                        if (dataResult!=null){
                            dataResult.onSuccess(textJokeBeanAliBean);
                        }
                    }
                });
    }

    /**获取文本笑话
     * */
    public static void getPicJoke(int page,final DataResult<AliBean<PicJokeBean>> dataResult){
        if (dataResult!=null){
            dataResult.onStart();
        }
        HttpHelper.getHttpHelper()
                .getApi(RecreationApi.class)
                .getPicJoke("20",""+page,"")
                .compose(HttpHelper.<AliBean<PicJokeBean>>setThread())
                .subscribe(new Observer<AliBean<PicJokeBean>>() {
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
                    public void onNext(AliBean<PicJokeBean> textJokeBeanAliBean) {
                        if (dataResult!=null){
                            dataResult.onSuccess(textJokeBeanAliBean);
                        }
                    }
                });
    }

    public static void getYiYuanPicData(int page,final DataResult<YiYuanPicBean> dataResult){
        if (dataResult!=null){
            dataResult.onStart();
        }
        HashMap<String,String> hashMap=new HashMap();
        hashMap.put("showapi_appid", YiYuanApi.clienId);
        hashMap.put("showapi_sign",YiYuanApi.sign);
        hashMap.put("type","10");
        hashMap.put("page",page+"");
        HttpHelper.getHttpHelper().getApi(YiYuanApi.class).getData(hashMap).
                compose(HttpHelper.<YiYuanPicBean>setThread()).
                subscribe(new Observer<YiYuanPicBean>() {
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
                    public void onNext(YiYuanPicBean s) {
                        if (dataResult!=null){
                            dataResult.onSuccess(s);
                        }
                    }
                });
    }



}
