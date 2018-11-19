package com.example.weizhenbin.wangebug.modules.code.controllers

import com.example.weizhenbin.wangebug.base.App
import com.example.weizhenbin.wangebug.base.DataResult
import com.example.weizhenbin.wangebug.modules.code.entity.*
import com.example.weizhenbin.wangebug.net.retrofit.HttpHelper
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.CodeApi
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by weizhenbin on 2018/8/24.
 */

object CodeController {

    private fun getSearchHistoryKeysByTop10():List<TBSearchHistoryKeyBean>{
        val tbSearchHistoryKeyBeanBox = App.app.boxStore.boxFor(TBSearchHistoryKeyBean::class.java)
        return tbSearchHistoryKeyBeanBox.query().orderDesc(TBSearchHistoryKeyBean_.id).build().find()
    }


    fun getArticleListData(page: Int, cid: String?, dataResult: DataResult<ArticleListDataBean>?) {
        dataResult?.onStart()
        HttpHelper.getHttpHelper().getApi(CodeApi::class.java).getArticleList(page.toString() + "", cid).compose(HttpHelper.setThread()).subscribe(object : Observer<ArticleListDataBean> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                dataResult?.onError(e)
            }

            override fun onNext(s: ArticleListDataBean) {
                dataResult?.onSuccess(s)
            }
        })
    }

    fun getArticleListData(page: Int, dataResult: DataResult<ArticleListDataBean>) {
        getArticleListData(page, null, dataResult)
    }

    fun getBannerData(dataResult: DataResult<BannerDataBean>?) {
        dataResult?.onStart()
        HttpHelper.getHttpHelper().getApi(CodeApi::class.java).banner.compose(HttpHelper.setThread()).subscribe(object : Observer<BannerDataBean> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                dataResult?.onError(e)
            }

            override fun onNext(s: BannerDataBean) {
                dataResult?.onSuccess(s)
            }
        })
    }


    fun getProjectTreeData(dataResult: DataResult<ProjectTreeDataBean>?) {
        dataResult?.onStart()
        HttpHelper.getHttpHelper().getApi(CodeApi::class.java).projectTree.compose(HttpHelper.setThread()).subscribe(object : Observer<ProjectTreeDataBean> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                dataResult?.onError(e)
            }

            override fun onNext(s: ProjectTreeDataBean) {
                dataResult?.onSuccess(s)
            }
        })
    }

    fun getProjectListData(page: Int, cid: String, dataResult: DataResult<ProjectListDataBean>?) {
        dataResult?.onStart()
        HttpHelper.getHttpHelper().getApi(CodeApi::class.java).getProjectList(page.toString() + "", cid).compose(HttpHelper.setThread()).subscribe(object : Observer<ProjectListDataBean> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                dataResult?.onError(e)
            }

            override fun onNext(s: ProjectListDataBean) {
                dataResult?.onSuccess(s)
            }
        })
    }


    fun getSystemTreeData(dataResult: DataResult<SystemTreeDataBean>?) {
        dataResult?.onStart()
        HttpHelper.getHttpHelper().getApi(CodeApi::class.java).systemTree.compose(HttpHelper.setThread()).subscribe(object : Observer<SystemTreeDataBean> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                dataResult?.onError(e)
            }

            override fun onNext(s: SystemTreeDataBean) {
                dataResult?.onSuccess(s)
            }
        })
    }

    fun getSearchData(page: Int, key: String, dataResult: DataResult<ArticleListDataBean>?) {
        dataResult?.onStart()
        HttpHelper.getHttpHelper().getApi(CodeApi::class.java).getSearchData(page.toString() + "", key).compose(HttpHelper.setThread()).subscribe(object : Observer<ArticleListDataBean> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                dataResult?.onError(e)
            }

            override fun onNext(s: ArticleListDataBean) {
                dataResult?.onSuccess(s)
            }
        })
    }

    fun getSearchHistoryData(dataResult: DataResult<List<TBSearchHistoryKeyBean>>?) {
        dataResult?.onStart()
        Observable.create(Observable.OnSubscribe<List<TBSearchHistoryKeyBean>> { subscriber ->
            /*  List<TBTodoBean> beanList= TodoController.getTodoList(todoStatus,page,pageCount);
                subscriber.onNext(beanList);*/
            val keyBeans = getSearchHistoryKeysByTop10()
            subscriber.onNext(keyBeans)
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<List<TBSearchHistoryKeyBean>> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        dataResult?.onError(e)
                    }

                    override fun onNext(keyBeans: List<TBSearchHistoryKeyBean>) {
                        dataResult?.onSuccess(keyBeans)
                    }
                })
    }

    fun getSearchHotKeyData(dataResult: DataResult<SearchHotKeyDataBean>?) {
        dataResult?.onStart()
        HttpHelper.getHttpHelper().getApi(CodeApi::class.java).searchHotKeyData.compose(HttpHelper.setThread()).subscribe(object : Observer<SearchHotKeyDataBean> {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                dataResult?.onError(e)
            }

            override fun onNext(s: SearchHotKeyDataBean) {
                dataResult?.onSuccess(s)
            }
        })
    }


    /**
     * 保存单个
     */
    fun saveSearchHistoryKey(key: String) {
        val tbSearchHistoryKeyBeanBox = App.app.boxStore.boxFor(TBSearchHistoryKeyBean::class.java)
        tbSearchHistoryKeyBeanBox.query().equal(TBSearchHistoryKeyBean_.key, key).build().remove()
        tbSearchHistoryKeyBeanBox.put(TBSearchHistoryKeyBean(key))
    }

    /**
     * 删除单个
     */
    fun removeSearchHistoryKey(id: Long) {
        val tbSearchHistoryKeyBeanBox = App.app.boxStore.boxFor(TBSearchHistoryKeyBean::class.java)
        tbSearchHistoryKeyBeanBox.remove(id)

    }

    /**
     * 删除单个
     */
    fun removeSearchHistoryKey(key: String) {
        val tbSearchHistoryKeyBeanBox = App.app.boxStore.boxFor(TBSearchHistoryKeyBean::class.java)
        tbSearchHistoryKeyBeanBox.query().equal(TBSearchHistoryKeyBean_.key, key).build().remove()

    }

    /**清除 */
    fun cleanSearchHistoryKey() {
        val tbSearchHistoryKeyBeanBox = App.app.boxStore.boxFor(TBSearchHistoryKeyBean::class.java)
        tbSearchHistoryKeyBeanBox.removeAll()
    }
}
