package com.example.weizhenbin.wangebug.net.retrofit.apiservice

import com.example.weizhenbin.wangebug.modules.code.entity.*
import retrofit2.http.*
import rx.Observable

/**
 * Created by weizhenbin on 2018/8/9.
 */

interface CodeApi {


    /**
     * 首页banner
     */
    @get:GET("banner/json")
    val banner: Observable<BannerDataBean>


    /**
     * 常用网站
     */
    @get:GET("friend/json")
    val friend: Observable<String>

    /**
     * 搜索热词
     */
    @get:GET("hotkey/json")
    val hotkey: Observable<String>


    /**
     * 体系数据
     */
    @get:GET("tree/json")
    val systemTree: Observable<SystemTreeDataBean>

    /**
     * 导航数据
     */
    @get:GET("navi/json")
    val navi: Observable<String>

    /**
     * 项目分类
     */
    @get:GET("project/tree/json")
    val projectTree: Observable<ProjectTreeDataBean>


    /**
     * 搜索热词
     */
    @get:GET("hotkey/json")
    val searchHotKeyData: Observable<SearchHotKeyDataBean>

    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: String): Observable<ArticleListDataBean>

    /**
     * 文章列表
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: String, @Query("cid") cid: String?): Observable<ArticleListDataBean>

    /**
     * 项目列表数据
     *
     */
    @GET("project/list/{page}/json?")
    fun getProjectList(@Path("page") page: String, @Query("cid") cid: String): Observable<ProjectListDataBean>

    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    fun getSearchData(@Path("page") page: String, @Field("k") key: String): Observable<ArticleListDataBean>

    companion object {

        val BASE_URL = "http://www.wanandroid.com/"
    }
}
