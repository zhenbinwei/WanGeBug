package com.example.weizhenbin.wangebug.modules.code.controllers;

import com.example.weizhenbin.wangebug.base.App;
import com.example.weizhenbin.wangebug.base.DataResult;
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean;
import com.example.weizhenbin.wangebug.modules.code.entity.BannerDataBean;
import com.example.weizhenbin.wangebug.modules.code.entity.ProjectListDataBean;
import com.example.weizhenbin.wangebug.modules.code.entity.ProjectTreeDataBean;
import com.example.weizhenbin.wangebug.modules.code.entity.SearchHotKeyDataBean;
import com.example.weizhenbin.wangebug.modules.code.entity.SystemTreeDataBean;
import com.example.weizhenbin.wangebug.modules.code.entity.TBSearchHistoryKeyBean;
import com.example.weizhenbin.wangebug.modules.code.entity.TBSearchHistoryKeyBean_;
import com.example.weizhenbin.wangebug.net.retrofit.HttpHelper;
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.CodeApi;

import java.util.List;

import io.objectbox.Box;
import rx.Observer;

/**
 * Created by weizhenbin on 2018/8/24.
 */

public class CodeController {

    public static void getArticleListData(int page, String cid, final DataResult<ArticleListDataBean> dataResult) {
        if (dataResult != null) {
            dataResult.onStart();
        }
        HttpHelper.getHttpHelper().getApi(CodeApi.class).getArticleList(page + "", cid).
                compose(HttpHelper.<ArticleListDataBean>setThread()).
                subscribe(new Observer<ArticleListDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dataResult != null) {
                            dataResult.onError(e);
                        }
                    }

                    @Override
                    public void onNext(ArticleListDataBean s) {
                        if (dataResult != null) {
                            dataResult.onSuccess(s);
                        }
                    }
                });
    }

    public static void getArticleListData(int page, final DataResult<ArticleListDataBean> dataResult) {
        getArticleListData(page, null, dataResult);
    }

    public static void getBannerData(final DataResult<BannerDataBean> dataResult) {
        if (dataResult != null) {
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
                        if (dataResult != null) {
                            dataResult.onError(e);
                        }
                    }

                    @Override
                    public void onNext(BannerDataBean s) {
                        if (dataResult != null) {
                            dataResult.onSuccess(s);
                        }
                    }
                });
    }


    public static void getProjectTreeData(final DataResult<ProjectTreeDataBean> dataResult) {
        if (dataResult != null) {
            dataResult.onStart();
        }
        HttpHelper.getHttpHelper().getApi(CodeApi.class).getProjectTree().
                compose(HttpHelper.<ProjectTreeDataBean>setThread()).
                subscribe(new Observer<ProjectTreeDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dataResult != null) {
                            dataResult.onError(e);
                        }
                    }

                    @Override
                    public void onNext(ProjectTreeDataBean s) {
                        if (dataResult != null) {
                            dataResult.onSuccess(s);
                        }
                    }
                });
    }

    public static void getProjectListData(int page, String cid, final DataResult<ProjectListDataBean> dataResult) {
        if (dataResult != null) {
            dataResult.onStart();
        }
        HttpHelper.getHttpHelper().getApi(CodeApi.class).getProjectList(page + "", cid).
                compose(HttpHelper.<ProjectListDataBean>setThread()).
                subscribe(new Observer<ProjectListDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dataResult != null) {
                            dataResult.onError(e);
                        }
                    }

                    @Override
                    public void onNext(ProjectListDataBean s) {
                        if (dataResult != null) {
                            dataResult.onSuccess(s);
                        }
                    }
                });
    }


    public static void getSystemTreeData(final DataResult<SystemTreeDataBean> dataResult) {
        if (dataResult != null) {
            dataResult.onStart();
        }
        HttpHelper.getHttpHelper().getApi(CodeApi.class).getSystemTree().
                compose(HttpHelper.<SystemTreeDataBean>setThread()).
                subscribe(new Observer<SystemTreeDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dataResult != null) {
                            dataResult.onError(e);
                        }
                    }

                    @Override
                    public void onNext(SystemTreeDataBean s) {
                        if (dataResult != null) {
                            dataResult.onSuccess(s);
                        }
                    }
                });
    }

    public static void getSearchData(int page, String key, final DataResult<ArticleListDataBean> dataResult) {
        if (dataResult != null) {
            dataResult.onStart();
        }
        HttpHelper.getHttpHelper().getApi(CodeApi.class).getSearchData(page + "", key).
                compose(HttpHelper.<ArticleListDataBean>setThread()).
                subscribe(new Observer<ArticleListDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dataResult != null) {
                            dataResult.onError(e);
                        }
                    }

                    @Override
                    public void onNext(ArticleListDataBean s) {
                        if (dataResult != null) {
                            dataResult.onSuccess(s);
                        }
                    }
                });
    }

    public static void getSearchHotKeyData(final DataResult<SearchHotKeyDataBean> dataResult) {
        if (dataResult != null) {
            dataResult.onStart();
        }
        HttpHelper.getHttpHelper().getApi(CodeApi.class).getSearchHotKeyData().
                compose(HttpHelper.<SearchHotKeyDataBean>setThread()).
                subscribe(new Observer<SearchHotKeyDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (dataResult != null) {
                            dataResult.onError(e);
                        }
                    }

                    @Override
                    public void onNext(SearchHotKeyDataBean s) {
                        if (dataResult != null) {
                            dataResult.onSuccess(s);
                        }
                    }
                });
    }


    /**
     * 保存单个
     * */
    public static void saveSearchHistoryKey(String key){
        Box<TBSearchHistoryKeyBean> tbSearchHistoryKeyBeanBox = App.app.getBoxStore().boxFor(TBSearchHistoryKeyBean.class);
        TBSearchHistoryKeyBean tbSearchHistoryKeyBean= tbSearchHistoryKeyBeanBox.query().equal(TBSearchHistoryKeyBean_.key,key).build().findFirst();
        if (tbSearchHistoryKeyBean!=null){
            tbSearchHistoryKeyBean.setKey(key);
            tbSearchHistoryKeyBeanBox.put(tbSearchHistoryKeyBean);
        }else {
            tbSearchHistoryKeyBeanBox.put(new TBSearchHistoryKeyBean(key));
        }
    }

    /**
     * 删除单个
     * */
    public static void removeSearchHistoryKey(long id){
        Box<TBSearchHistoryKeyBean> tbSearchHistoryKeyBeanBox = App.app.getBoxStore().boxFor(TBSearchHistoryKeyBean.class);
        tbSearchHistoryKeyBeanBox.remove(id);

    }
    /**清除*/
    public static void cleanSearchHistoryKey(){
        Box<TBSearchHistoryKeyBean> tbSearchHistoryKeyBeanBox = App.app.getBoxStore().boxFor(TBSearchHistoryKeyBean.class);
        tbSearchHistoryKeyBeanBox.removeAll();
    }
    public static List<TBSearchHistoryKeyBean> getSearchHistoryKeysByTop10(){
        Box<TBSearchHistoryKeyBean> tbSearchHistoryKeyBeanBox = App.app.getBoxStore().boxFor(TBSearchHistoryKeyBean.class);
       return tbSearchHistoryKeyBeanBox.query().orderDesc(TBSearchHistoryKeyBean_.id).build().find(0,10);
    }
}
