package com.example.weizhenbin.wangebug.modules.code.uis

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
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeProjectListAdapter
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController
import com.example.weizhenbin.wangebug.modules.code.entity.ProjectListDataBean
import java.util.*

/**
 * Created by weizhenbin on 2018/8/28.
 */

class ProjectListFragment : BaseFragment() {

    private var srlRefresh: SwipeRefreshLayout? = null
    private var rvDataList: RecyclerView? = null
    private var listAdapter: CodeProjectListAdapter? = null
    internal var page = 0

    internal var contentListBeen: MutableList<ProjectListDataBean.DataBean.DatasBean> = ArrayList()

    private var id: String? = null
    override var pageTitle: String? =""
         set(value) {
            super.pageTitle = value
            field=value
        }

    override val contentViewLayoutId: Int
        get() = R.layout.fm_project_list


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {
            id = it.getString("id")
            pageTitle = it.getString("name")
        }
    }

    override fun initFragment(view: View?) {
        initViews(view!!)
        initData()
        initEvent()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("id", id)
        outState.putString("name", this.pageTitle)
        super.onSaveInstanceState(outState)
    }

    private fun setId(id: String) {
        this.id = id
    }

    private fun setName(name: String?) {
        this.pageTitle = name
    }


    private fun initData() {
        if (context == null) {
            return
        }
        listAdapter = CodeProjectListAdapter(context!!, contentListBeen)
        rvDataList!!.adapter = listAdapter
        rvDataList!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        val drawable = resources.getDrawable(R.drawable.divider_line)
        itemDecoration.setDrawable(drawable)
        rvDataList!!.addItemDecoration(itemDecoration)
        listAdapter!!.bindToRecyclerView(rvDataList)
        listAdapter!!.disableLoadMoreIfNotFullPage()
    }

    private fun initEvent() {
        srlRefresh!!.setOnRefreshListener {
            page = 0
            getData()
        }
        listAdapter!!.setOnLoadMoreListener({ getData() }, rvDataList)
        listAdapter!!.setAction (View.OnClickListener {  page = 0
            getData()  })
    }

    private fun getData() {
        if (TextUtils.isEmpty(id)) {
            return
        }
        CodeController.getProjectListData(page, id!!, object : DataResultAdapter<ProjectListDataBean>() {

            override fun onSuccess(projectListDataBean: ProjectListDataBean?) {
                super.onSuccess(projectListDataBean)
                srlRefresh!!.isRefreshing = false
                if (projectListDataBean != null && projectListDataBean.errorCode == 0) {
                    if (projectListDataBean.data != null && projectListDataBean.data!!.datas != null) {
                        if (page == 0) {
                            contentListBeen.clear()
                        }
                        contentListBeen.addAll(projectListDataBean.data!!.datas!!)
                        if (page == 0) {
                            listAdapter!!.setNewData(contentListBeen)
                        } else {
                            listAdapter!!.notifyDataSetChanged()
                        }
                        if (projectListDataBean.data!!.isOver) {
                            listAdapter!!.loadMoreEnd()
                        } else {
                            listAdapter!!.loadMoreComplete()
                            page++
                        }
                    } else {
                        listAdapter!!.loadMoreEnd()
                    }
                } else {
                    if (page == 0) {
                        listAdapter!!.emptyData()
                    } else {
                        listAdapter!!.loadMoreFail()
                    }
                }
            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                srlRefresh!!.isRefreshing = false
                if (page == 0) {
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

        fun getFragment(id: String, name: String?): ProjectListFragment {
            val projectListFragment = ProjectListFragment()
            projectListFragment.setId(id)
            projectListFragment.setName(name)
            return projectListFragment
        }
    }
}
