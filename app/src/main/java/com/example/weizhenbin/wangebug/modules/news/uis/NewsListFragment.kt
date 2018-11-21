package com.example.weizhenbin.wangebug.modules.news.uis

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.DataResultAdapter
import com.example.weizhenbin.wangebug.modules.news.adapters.NewsListAdapter
import com.example.weizhenbin.wangebug.modules.news.controllers.NewController
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean
import java.util.*

/**
 * Created by weizhenbin on 2018/8/23.
 */

class NewsListFragment : BaseFragment() {

    private var srlRefresh: SwipeRefreshLayout? = null
    private var rvDataList: RecyclerView? = null
    private var listAdapter: NewsListAdapter? = null
    internal var page = 1

    internal var contentListBeen: MutableList<YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> = ArrayList()

    private var channelId: String? = null
    private var channelName: String? = null


    override val contentViewLayoutId: Int
        get() = R.layout.fm_news_list

    override var pageTitle: String?
        get() = if (TextUtils.isEmpty(channelName)) {
            "全部"
        } else channelName
        set(value) {
            super.pageTitle = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {
            channelId = it.getString("channelId")
            channelName = it.getString("channelName")
        }

    }

    override fun initFragment(view: View?) {
        initViews(view!!)
        initData()
        initEvent()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("channelId", channelId)
        outState.putString("channelName", channelName)
        super.onSaveInstanceState(outState)
    }

    private fun setChannelId(channelId: String?) {
        this.channelId = channelId
    }

    private fun setChannelName(channelName: String?) {
        this.channelName = channelName
    }


    private fun initData() {
        if (context == null) {
            return
        }
        listAdapter = NewsListAdapter(context!!, contentListBeen)
        rvDataList!!.adapter = listAdapter
        rvDataList!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        val drawable = resources.getDrawable(R.drawable.divider_line)
        itemDecoration.setDrawable(drawable)
        rvDataList!!.addItemDecoration(itemDecoration)
        listAdapter!!.bindToRecyclerView(rvDataList)
        listAdapter!!.disableLoadMoreIfNotFullPage()
    }

    protected fun initEvent() {
        srlRefresh!!.setOnRefreshListener {
            page = 1
            getData()
        }
        listAdapter!!.setOnLoadMoreListener({ getData() }, rvDataList)

        listAdapter!!.setAction(View.OnClickListener {
            page = 1
            getData()
        })
    }

    private fun getData() {
        if (TextUtils.isEmpty(channelId) && TextUtils.isEmpty(channelName)) {
            return
        }
        NewController.getNewsListData(page, channelId!!, object : DataResultAdapter<YiYuanNewsBean>() {
            override fun onSuccess(yiYuanNewsBean: YiYuanNewsBean?) {
                super.onSuccess(yiYuanNewsBean)
                srlRefresh!!.isRefreshing = false
                if (yiYuanNewsBean != null && yiYuanNewsBean.showapi_res_code == 0) {
                    if (yiYuanNewsBean.showapi_res_body != null && yiYuanNewsBean.showapi_res_body!!.pagebean != null) {
                        if (page == 1) {
                            contentListBeen.clear()
                        }
                        contentListBeen.addAll(yiYuanNewsBean.showapi_res_body!!.pagebean!!.contentlist!!)
                        if (page == 1) {
                            listAdapter!!.setNewData(contentListBeen)
                        } else {
                            listAdapter!!.notifyDataSetChanged()
                        }
                        if (yiYuanNewsBean.showapi_res_body!!.pagebean!!.isLastPage) {
                            listAdapter!!.loadMoreEnd()
                        } else {
                            listAdapter!!.loadMoreComplete()
                            page++
                        }
                    } else {
                        listAdapter!!.loadMoreEnd()
                    }
                } else {
                    if (page == 1) {
                        listAdapter!!.emptyData()
                    } else {
                        listAdapter!!.loadMoreFail()
                    }
                }
            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                srlRefresh!!.isRefreshing = false
                if (page == 1) {
                    listAdapter!!.emptyData()
                } else {
                    listAdapter!!.loadMoreFail()
                }
            }
        })
    }

    private fun initViews(view: View) {
        srlRefresh = view.findViewById(R.id.srl_refresh)
        rvDataList = view.findViewById(R.id.rv_data_list)
    }

    override fun loadData() {
        if (contentListBeen.isEmpty()) {
            getData()
        }
    }

    companion object {

        fun getFragment(channelId: String?, channelName: String?): NewsListFragment {
            val newsListFragment = NewsListFragment()
            newsListFragment.setChannelId(channelId)
            newsListFragment.setChannelName(channelName)
            return newsListFragment
        }
    }
}
