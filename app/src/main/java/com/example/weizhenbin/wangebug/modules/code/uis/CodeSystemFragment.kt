package com.example.weizhenbin.wangebug.modules.code.uis

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.DataResultAdapter
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeSystemTreeAdapter
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController
import com.example.weizhenbin.wangebug.modules.code.entity.SystemTreeDataBean
import java.util.*

/**
 * Created by weizhenbin on 2018/8/24.
 */

class CodeSystemFragment : BaseFragment() {


    lateinit var srlRefresh: SwipeRefreshLayout
    lateinit var rvDataList: RecyclerView
    lateinit var listAdapter: CodeSystemTreeAdapter
    internal var dataBeen: MutableList<SystemTreeDataBean.DataBean> = ArrayList()


    override val contentViewLayoutId: Int
        get() = R.layout.fm_code_system

    override var pageTitle: String?
        get() = "体系"
        set(value) {
            super.pageTitle = value
        }

    override fun initFragment(view: View?) {
        initViews(view!!)
        initData()
        initEvent()
    }

    private fun getData() {
        CodeController.getSystemTreeData(object : DataResultAdapter<SystemTreeDataBean>() {
            override fun onSuccess(systemTreeDataBean: SystemTreeDataBean?) {
                super.onSuccess(systemTreeDataBean)
                srlRefresh.isRefreshing = false
                if (systemTreeDataBean != null && systemTreeDataBean.errorCode == 0) {
                    if (systemTreeDataBean.data?.isEmpty() != null) {
                        dataBeen.clear()
                        dataBeen.addAll(systemTreeDataBean.data!!)
                        listAdapter.notifyDataSetChanged()
                    }
                } else {
                    listAdapter.emptyData()
                }
            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                srlRefresh.isRefreshing = false
                listAdapter.emptyData()
            }
        })
    }

    private fun initEvent() {
        srlRefresh.setOnRefreshListener { getData() }
        listAdapter.setAction { getData() }
    }

    private fun initData() {
        if (context == null) {
            return
        }
        listAdapter = CodeSystemTreeAdapter(context, dataBeen)
        rvDataList.adapter = listAdapter
        rvDataList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        val drawable = resources.getDrawable(R.drawable.divider_line)
        itemDecoration.setDrawable(drawable)
        rvDataList.addItemDecoration(itemDecoration)
    }

    private fun initViews(view: View) {
        srlRefresh = view.findViewById(R.id.srl_refresh)
        rvDataList = view.findViewById(R.id.rv_data_list)
    }


    override fun loadData() {
        if (dataBeen.isEmpty()) {
            getData()
        }
    }

    companion object {

        val fragment: CodeSystemFragment
            get() = CodeSystemFragment()
    }


}
