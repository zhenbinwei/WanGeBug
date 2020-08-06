package com.example.weizhenbin.wangebug.modules.code.uis

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.DataResultAdapter
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeArticleListAdapter
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean
import java.util.*

/**
 * Created by weizhenbin on 2018/8/30.
 */

class CodeSystemTreeChildrenFragment : BaseFragment() {

    lateinit var srlRefresh: SwipeRefreshLayout
    lateinit var rvDataList: RecyclerView
    lateinit var listAdapter: CodeArticleListAdapter
     var page = 0
     var datasBeen: MutableList<ArticleListDataBean.DataBean.DatasBean> = ArrayList()
     var cid: Int = 0

  /*  override var pageTitle: String? = ""
        set(value) {
            super.pageTitle = value
        }*/


    override val contentViewLayoutId: Int
        get() = R.layout.fm_system_tree_children

    override fun initFragment(view: View?) {
        initViews(view!!)
        initData()
        initEvent()
    }

    private fun getData() {
        CodeController.getArticleListData(page, cid.toString() + "", object : DataResultAdapter<ArticleListDataBean>() {
            override fun onStart() {
                super.onStart()
                if (page == 0) {
                    srlRefresh.isRefreshing = true
                }
            }

            override fun onSuccess(t: ArticleListDataBean?) {
                super.onSuccess(t)
                srlRefresh.isRefreshing = false
                if (t != null && t.errorCode == 0) {
                    if (t.data != null && t.data!!.datas != null) {
                        if (page == 0) {
                            datasBeen.clear()
                        }
                        datasBeen.addAll(t.data!!.datas!!)
                        if (page == 0) {
                            listAdapter.setNewData(datasBeen)
                        } else {
                            listAdapter.notifyDataSetChanged()
                        }
                        if (t.data!!.over) {
                            listAdapter.loadMoreEnd()
                        } else {
                            listAdapter.loadMoreComplete()
                            page++
                        }
                    } else {
                        listAdapter.loadMoreEnd()
                    }
                }
            }
        })
    }


    private fun setName(name: String) {
        this.pageTitle = name
    }

    private fun initEvent() {
        srlRefresh.setOnRefreshListener {
            page = 0
            getData()
        }
        listAdapter.setOnLoadMoreListener({ getData() }, rvDataList)
    }

    private fun initData() {
        if (context == null) {
            return
        }
        listAdapter = CodeArticleListAdapter(context!!, datasBeen)
        rvDataList.adapter = listAdapter
        rvDataList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        val drawable = resources.getDrawable(R.drawable.divider_line)
        itemDecoration.setDrawable(drawable)
        rvDataList.addItemDecoration(itemDecoration)
        listAdapter.bindToRecyclerView(rvDataList)
        listAdapter.disableLoadMoreIfNotFullPage()
    }

    private fun initViews(view: View) {
        srlRefresh = view.findViewById(R.id.srl_refresh)
        rvDataList = view.findViewById(R.id.rv_data_list)
    }

    override fun loadData() {
        if (datasBeen.isEmpty()) {
            getData()
        }
    }

    companion object {


        fun getFragment(cid: Int, name: String): CodeSystemTreeChildrenFragment {
            val fragment = CodeSystemTreeChildrenFragment()
            fragment.setName(name)
            fragment.cid=cid
            return fragment
        }
    }

}
