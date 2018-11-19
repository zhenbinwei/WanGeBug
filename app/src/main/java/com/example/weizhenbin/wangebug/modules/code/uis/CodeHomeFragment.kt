package com.example.weizhenbin.wangebug.modules.code.uis

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.DataResultAdapter
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeArticleListAdapter
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeHomeBannerAdapter
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean
import com.example.weizhenbin.wangebug.modules.code.entity.BannerDataBean
import com.example.weizhenbin.wangebug.views.autoscrolllayout.AutoScrollerLayout
import java.util.*

/**
 * Created by weizhenbin on 2018/8/24.
 */

class CodeHomeFragment : BaseFragment() {

    lateinit var srlRefresh: SwipeRefreshLayout
    lateinit var rvDataList: RecyclerView
    lateinit var listAdapter: CodeArticleListAdapter
    internal var page = 0
    lateinit var bannerHeader: View
    lateinit var autoScrollerLayout: AutoScrollerLayout
     var datasBeen: MutableList<ArticleListDataBean.DataBean.DatasBean> = ArrayList()


    override val contentViewLayoutId: Int
        get() = R.layout.fm_code_home

    override var pageTitle: String?
        get() = "首页"
        set(value) {
            super.pageTitle = value
        }

    override fun initFragment(view: View?) {
        initViews(view!!)
        initData()
        initEvent()
    }

    private fun getData() {
        CodeController.getArticleListData(page, object : DataResultAdapter<ArticleListDataBean>() {
            override fun onSuccess(articleListDataBean: ArticleListDataBean?) {
                super.onSuccess(articleListDataBean)
                srlRefresh.isRefreshing = false
                if (articleListDataBean != null && articleListDataBean.errorCode == 0) {
                    if (articleListDataBean.data != null && articleListDataBean.data?.datas != null) {
                        if (page == 0) {
                            datasBeen.clear()
                        }
                        datasBeen.addAll(articleListDataBean.data?.datas!!)
                        if (page == 0) {
                            listAdapter.setNewData(datasBeen)
                        } else {
                            listAdapter.notifyDataSetChanged()
                        }
                        if (articleListDataBean.data!!.isOver) {
                            listAdapter.loadMoreEnd()
                        } else {
                            listAdapter.loadMoreComplete()
                            page++
                        }
                    } else {
                        listAdapter.loadMoreEnd()
                    }
                } else {
                    if (page == 0) {
                        listAdapter.emptyData()
                    } else {
                        listAdapter.loadMoreFail()
                    }
                }
            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                srlRefresh.isRefreshing = false
                if (page == 0) {
                    listAdapter.emptyData()
                } else {
                    listAdapter.loadMoreFail()
                }
            }
        })
    }

    private fun getBannerData() {
        CodeController.getBannerData(object : DataResultAdapter<BannerDataBean>() {

            override fun onSuccess(bannerDataBean: BannerDataBean?) {
                super.onSuccess(bannerDataBean)
                if (bannerDataBean != null && bannerDataBean.errorCode == 0) {
                    if (bannerDataBean.data != null && !bannerDataBean.data!!.isEmpty()) {
                        autoScrollerLayout.setPagerAdapter(CodeHomeBannerAdapter(context, bannerDataBean.data))
                    } else {
                        listAdapter.removeHeaderView(bannerHeader)
                    }
                }
            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                listAdapter.removeHeaderView(bannerHeader)
            }
        })
    }

    private fun initEvent() {
        srlRefresh.setOnRefreshListener {
            page = 0
            getData()
        }
        listAdapter.setOnLoadMoreListener({ getData() }, rvDataList)
        listAdapter.setAction {
            listAdapter.loading()
            page = 0
            getData()
        }
    }

    private fun initData() {
        if (context == null) {
            return
        }
        listAdapter = CodeArticleListAdapter(context, datasBeen)
        rvDataList.adapter = listAdapter
        rvDataList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        val drawable = resources.getDrawable(R.drawable.divider_line)
        itemDecoration.setDrawable(drawable)
        rvDataList.addItemDecoration(itemDecoration)
        listAdapter.bindToRecyclerView(rvDataList)
        listAdapter.disableLoadMoreIfNotFullPage()
        listAdapter.addHeaderView(bannerHeader)
    }

    private fun initViews(view: View) {
        srlRefresh = view.findViewById(R.id.srl_refresh)
        rvDataList = view.findViewById(R.id.rv_data_list)
        bannerHeader = LayoutInflater.from(context).inflate(R.layout.code_home_banner_header, null)
        autoScrollerLayout = bannerHeader.findViewById(R.id.asl_banner)
    }


    override fun loadData() {
        if (datasBeen.isEmpty()) {
            getData()
        }
        getBannerData()
    }

    companion object {

        val fragment: CodeHomeFragment
            get() = CodeHomeFragment()
    }


}
