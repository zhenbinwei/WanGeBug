package com.example.weizhenbin.wangebug.modules.collect.uis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseActivity
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler
import com.example.weizhenbin.wangebug.eventbus.EventCode
import com.example.weizhenbin.wangebug.eventbus.MessageEvent
import com.example.weizhenbin.wangebug.modules.collect.adapters.CollectListAdapter
import com.example.weizhenbin.wangebug.modules.collect.controllers.CollectController
import com.example.weizhenbin.wangebug.modules.collect.entity.TBCollectBean
import com.example.weizhenbin.wangebug.views.TitleBar
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by weizhenbin on 2018/10/10.
 */
class CollectListActivity : BaseActivity() {
    private var srlRefresh: SwipeRefreshLayout? = null
    private var rvDataList: RecyclerView? = null
    private var listAdapter: CollectListAdapter? = null
    private val beanList = ArrayList<TBCollectBean>()
    private var page = 1
    private val pageCount = 15
    private var tbTitle: TitleBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)
        initViews()
        initData()
        initEvent()
        getData()
        EventBusHandler.register(this)
    }

    private fun initEvent() {
        srlRefresh!!.setOnRefreshListener {
            page = 1
            getData()
        }
        listAdapter!!.setOnLoadMoreListener({ getData() }, rvDataList)
        listAdapter!!.setAction(View.OnClickListener {
            page = 1
            getData()
        })
        tbTitle!!.setLeftOnClickListener { finish() }
    }

    private fun getData() {
        Observable.create(Observable.OnSubscribe<List<TBCollectBean>> { subscriber ->
            val beanList = CollectController.getCollectList(page, pageCount)
            subscriber.onNext(beanList)
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<List<TBCollectBean>> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        srlRefresh!!.isRefreshing = false
                        if (page == 1) {
                            listAdapter?.emptyData()
                        } else {
                            listAdapter?.loadMoreFail()
                        }
                    }

                    override fun onNext(collectBeans: List<TBCollectBean>?) {
                        srlRefresh!!.isRefreshing = false
                        if (collectBeans != null) {
                            if (page == 1) {
                                beanList.clear()
                            }
                            beanList.addAll(collectBeans)
                            if (page == 1) {
                                listAdapter!!.setNewData(beanList)
                            } else {
                                listAdapter!!.notifyDataSetChanged()
                            }
                            if (collectBeans.isEmpty()) {
                                if (page == 1) {
                                    listAdapter?.emptyData(false)
                                } else {
                                    listAdapter?.loadMoreComplete()
                                }
                            } else if (collectBeans.size < pageCount) {
                                listAdapter?.loadMoreEnd()
                            } else {
                                listAdapter?.loadMoreComplete()
                                page++
                            }
                        } else {
                            if (page == 1) {
                                listAdapter?.emptyData()
                            } else {
                                listAdapter?.loadMoreFail()
                            }
                        }
                    }
                })
    }

    private fun initData() {

        listAdapter = CollectListAdapter(this, beanList)
        rvDataList!!.adapter = listAdapter
        rvDataList!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        val drawable = resources.getDrawable(R.drawable.divider_line)
        itemDecoration.setDrawable(drawable)
        rvDataList!!.addItemDecoration(itemDecoration)
        listAdapter!!.bindToRecyclerView(rvDataList)
        listAdapter!!.disableLoadMoreIfNotFullPage()
    }

    private fun initViews() {
        srlRefresh = findViewById(R.id.srl_refresh)
        rvDataList = findViewById(R.id.rv_data_list)
        tbTitle = findViewById(R.id.tb_title)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when (event.code) {
             EventCode.ADD_COLLECT_CODE -> if (event.msg is TBCollectBean) {
                addCollect(event.msg as TBCollectBean)
            }
             EventCode.CANCEL_COLLECT_CODE -> removeCollect(event.msg as String)
        }
    }


    private fun addCollect(bean: TBCollectBean) {
        beanList.add(0, bean)
        listAdapter!!.notifyItemInserted(0)
    }

    private fun removeCollect(title: String) {
        for (i in beanList.indices) {
            val bean = beanList[i]
            if (bean.title == title) {
                beanList.removeAt(i)
                listAdapter!!.notifyItemRemoved(i)
            }
        }
        if (beanList.isEmpty()) {
            listAdapter!!.emptyData(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusHandler.unregister(this)
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, CollectListActivity::class.java))
        }
    }
}
