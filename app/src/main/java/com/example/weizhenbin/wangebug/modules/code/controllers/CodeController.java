package com.example.weizhenbin.wangebug.modules.code.controllers;

import com.example.weizhenbin.wangebug.base.DataResult;
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean;
import com.example.weizhenbin.wangebug.modules.code.entity.BannerDataBean;
import com.example.weizhenbin.wangebug.net.retrofit.HttpHelper;
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.CodeApi;

import rx.Observer;

/**
 * Created by weizhenbin on 2018/8/24.
 */

public class CodeController {

    public static void getArticleListData(int page,final DataResult<ArticleListDataBean> dataResult){
        if (dataResult!=null){
            dataResult.onStart();
        }
        HttpHelper.getHttpHelper().getApi(CodeApi.class).getArticleList(page+"").
                compose(HttpHelper.<ArticleListDataBean>setThread()).
                subscribe(new Observer<ArticleListDataBean>() {
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
                    public void onNext(ArticleListDataBean s) {
                        if (dataResult!=null){
                            dataResult.onSuccess(s);
                        }
                    }
                });
    }

    public static void getBannerData(final DataResult<BannerDataBean> dataResult){
        if (dataResult!=null){
            dataResult.onStart();
        }
        HttpHelper.getHttpHelper().getApi(CodeApi.class).getBanner().
                compose(HttpHelper.<BannerDataBean>setThread()).
                subscribe(new Observer<BannerDataBean>() {
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
                    public void onNext(BannerDataBean s) {
                        if (dataResult!=null){
                            dataResult.onSuccess(s);
                        }
                    }
                });
    }
}
