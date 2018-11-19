package com.example.weizhenbin.wangebug.modules.todo.uis

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.DataResultAdapter
import com.example.weizhenbin.wangebug.modules.todo.adapters.TodoListAdapter
import com.example.weizhenbin.wangebug.modules.todo.controllers.TodoController
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean
import java.util.*

/**
 * Created by weizhenbin on 18/9/9.
 */

class TodoListFragment : BaseFragment() {

    private var todoStatus: Int = 0
    private var srlRefresh: SwipeRefreshLayout? = null
    private var rvDataList: RecyclerView? = null
    private var listAdapter: TodoListAdapter? = null
    private val beanList = ArrayList<TBTodoBean>()
    private var page = 1
    private val pageCount = 15

    override val contentViewLayoutId: Int
        get() = R.layout.fm_todo_list

    override var pageTitle: String = ""
        get() = when (todoStatus) {
            0 -> "未完成"
            1 -> "已完成"
            else -> "全部"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            todoStatus = savedInstanceState.getInt("todoStatus")
        }
    }

    override fun initFragment(view: View?) {
        initViews(view!!)
        initData()
        initEvent()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("todoStatus", todoStatus)
        super.onSaveInstanceState(outState)
    }

    private fun initEvent() {
        srlRefresh!!.setOnRefreshListener {
            page = 1
            getData()
        }
        listAdapter!!.setOnLoadMoreListener({ getData() }, rvDataList)
        listAdapter!!.setAction {
            page = 1
            getData()
        }
    }

    private fun getData() {
        TodoController.asynGetTodoList(todoStatus, page, pageCount, object : DataResultAdapter<List<TBTodoBean>>() {

            override fun onSuccess(t: List<TBTodoBean>?) {
                super.onSuccess(t)
                srlRefresh!!.isRefreshing = false
                if (t != null) {
                    if (page == 1) {
                        beanList.clear()
                    }
                    beanList.addAll(t)
                    if (page == 1) {
                        listAdapter!!.setNewData(beanList)
                    } else {
                        listAdapter!!.notifyDataSetChanged()
                    }
                    if (t.isEmpty()) {
                        if (page == 1) {
                            listAdapter!!.emptyData(false)
                        } else {
                            listAdapter!!.loadMoreComplete()
                        }
                    } else if (t.size < pageCount) {
                        listAdapter!!.loadMoreEnd()
                    } else {
                        listAdapter!!.loadMoreComplete()
                        page++
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
                listAdapter!!.loadMoreComplete()
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

    private fun initData() {
        if (context == null) {
            return
        }
        listAdapter = TodoListAdapter(context as Context, beanList, todoStatus)
        rvDataList!!.adapter = listAdapter
        rvDataList!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        val drawable = resources.getDrawable(R.drawable.divider_line)
        itemDecoration.setDrawable(drawable)
        rvDataList!!.addItemDecoration(itemDecoration)
        listAdapter!!.bindToRecyclerView(rvDataList)
        listAdapter!!.disableLoadMoreIfNotFullPage()
    }

    private fun setTodoStatus(todoStatus: Int) {
        this.todoStatus = todoStatus
    }

    override fun loadData() {
        if (beanList.isEmpty()) {
            getData()
        }
    }


    fun addTodo(tbTodoBean: TBTodoBean) {
        if (listAdapter == null) {
            return
        }
        if (isContain(tbTodoBean)) {
            return
        }
        beanList.add(0, tbTodoBean)
        listAdapter!!.notifyItemInserted(0)
    }

    fun delTodo(tbTodoBean: TBTodoBean) {
        if (listAdapter == null) {
            return
        }
        if (!isContain(tbTodoBean)) {
            return
        }
        val size = beanList.size
        for (i in 0 until size) {
            if (tbTodoBean.uuid == beanList[i].uuid) {
                beanList.removeAt(i)
                listAdapter!!.notifyItemRemoved(i)
                break
            }
        }
        if (beanList.isEmpty()) {
            listAdapter!!.emptyData(false)
        }
    }

    fun updateTodo(tbTodoBean: TBTodoBean) {
        if (listAdapter == null) {
            return
        }
        if (!isContain(tbTodoBean)) {
            return
        }
        val size = beanList.size
        for (i in 0 until size) {
            if (tbTodoBean.uuid == beanList[i].uuid) {
                beanList.removeAt(i)
                beanList.add(i, tbTodoBean)
                listAdapter!!.notifyItemChanged(i)
                break
            }
        }
    }


    private fun isContain(tbTodoBean: TBTodoBean?): Boolean {

        if (tbTodoBean == null) {
            return false
        }

        val size = beanList.size

        for (i in 0 until size) {
            if (tbTodoBean.uuid == beanList[i].uuid) {
                return true
            }
        }

        return false
    }

    companion object {

        fun getFragment(todoStatus: Int): TodoListFragment {
            val todoListFragment = TodoListFragment()
            todoListFragment.setTodoStatus(todoStatus)
            return todoListFragment
        }
    }


}
