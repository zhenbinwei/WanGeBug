package com.example.weizhenbin.wangebug.modules.recreation.uis

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.DataResultAdapter
import com.example.weizhenbin.wangebug.modules.recreation.adapters.YiYuanDataListAdapter
import com.example.weizhenbin.wangebug.modules.recreation.controllers.JokeController
import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanPicBean
import java.util.*

/**
 * Created by weizhenbin on 2018/8/17.
 */

class YiYuanPicFragment : BaseFragment() {

    lateinit var srlRefresh: SwipeRefreshLayout
    lateinit var rvDataList: RecyclerView
    lateinit var listAdapter: YiYuanDataListAdapter
    internal var page = 1

    internal var contentListBeen: MutableList<YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> = ArrayList()


    override val contentViewLayoutId: Int
        get() = R.layout.fm_yiyuan_pic

    override var pageTitle: String?
        get() = "图片"
        set(value: String?) {
            super.pageTitle = value
        }

    override fun initFragment(view: View?) {
        initViews(view!!)
        initData()
        initEvent()
    }

    private fun initData() {
        listAdapter = YiYuanDataListAdapter(context!!, R.layout.yiyuan_pic_item_layout, contentListBeen)
        rvDataList.adapter = listAdapter
        rvDataList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listAdapter.bindToRecyclerView(rvDataList)
        listAdapter.disableLoadMoreIfNotFullPage()
    }


    private fun initEvent() {
        srlRefresh.setOnRefreshListener {
            page = 1
            getData()
        }
        listAdapter.setOnLoadMoreListener({ getData() }, rvDataList)
        listAdapter.setAction {
            page = 1
            getData()
        }
    }

    private fun getData() {
        JokeController.getYiYuanPicData(page, object : DataResultAdapter<YiYuanPicBean>() {

            override fun onSuccess(yiYuanPicBean: YiYuanPicBean?) {
                super.onSuccess(yiYuanPicBean)
                srlRefresh.isRefreshing = false
                if (yiYuanPicBean != null && yiYuanPicBean.showapi_res_code == 0) {
                    if (yiYuanPicBean.showapi_res_body != null && yiYuanPicBean.showapi_res_body!!.pagebean != null) {
                        if (page == 1) {
                            contentListBeen.clear()
                        }
                        contentListBeen.addAll(yiYuanPicBean.showapi_res_body!!.pagebean?.contentlist!!)
                        if (page == 1) {
                            listAdapter.setNewData(contentListBeen)
                        } else {
                            listAdapter.notifyDataSetChanged()
                        }
                        if (yiYuanPicBean.showapi_res_body!!.pagebean?.isLastPage!!) {
                            listAdapter.loadMoreEnd()
                        } else {
                            listAdapter.loadMoreComplete()
                            page++
                        }
                    } else {
                        listAdapter.loadMoreEnd()
                    }
                } else {
                    if (page == 1) {
                        listAdapter.emptyData()
                    } else {
                        listAdapter.loadMoreFail()
                    }
                }
            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                srlRefresh.isRefreshing = false
                if (page == 1) {
                    listAdapter.emptyData()
                } else {
                    listAdapter.loadMoreFail()
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
}
