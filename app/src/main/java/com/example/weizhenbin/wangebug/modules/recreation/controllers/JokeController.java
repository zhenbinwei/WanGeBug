package com.example.weizhenbin.wangebug.modules.recreation.controllers;

import com.example.weizhenbin.wangebug.modules.recreation.entity.TextJokeBean;
import com.example.weizhenbin.wangebug.net.retrofit.HttpHelper;
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.RecreationApi;
import com.example.weizhenbin.wangebug.net.retrofit.entity.AliBean;

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










    public interface DataResult<T>{

        void onStart();

        void onError(Throwable throwable);

        void onSuccess(T t);

    }

    public static class DataResultAdapter<T> implements JokeController.DataResult<T>{


        @Override
        public void onStart() {

        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onSuccess(T t) {

        }
    }

}
