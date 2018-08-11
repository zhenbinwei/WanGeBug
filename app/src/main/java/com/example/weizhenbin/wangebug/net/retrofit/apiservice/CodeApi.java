package com.example.weizhenbin.wangebug.net.retrofit.apiservice;

import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListData;
import com.example.weizhenbin.wangebug.modules.code.entity.WanAndroidBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by weizhenbin on 2018/8/9.
 */

public interface CodeApi {

    String BASE_URL="http://www.wanandroid.com/";

    /**
     * 首页文章列表
     * */
    @GET("article/list/{page}/json")
    Observable<WanAndroidBean<ArticleListData>> getArticleList(@Path("page") String page);


    /**
     *首页banner
     * */
    @GET("banner/json")
    Observable<String> getBanner();


    /**
     *常用网站
     * */
    @GET("friend/json")
    Observable<String> getFriend();

    /**
     *搜索热词
     * */
    @GET("hotkey/json")
    Observable<String> getHotkey();


    /**
     *体系数据
     * */
    @GET("tree/json")
    Observable<String> getTree();

    /**
     * 知识体系下的文章
     * */
    @GET("article/list/{page}/json?cid={cid}")
    Observable<String> getArticleList(@Path("page") String page,@Path("cid") String cid);

    /**
     *导航数据
     * */
    @GET("navi/json")
    Observable<String> getNavi();

    /**
     *项目分类
     * */
    @GET("project/tree/json")
    Observable<String> getProjectTree();

    /**
     * 项目列表数据
     * */
    @GET("project/list/{page}/json?cid={cid}")
    Observable<String> getProjectList(@Path("page") String page,@Path("cid") String cid);


}
