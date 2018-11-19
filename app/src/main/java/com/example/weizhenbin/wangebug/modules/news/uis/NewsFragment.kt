package com.example.weizhenbin.wangebug.modules.news.uis

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.DataResultAdapter
import com.example.weizhenbin.wangebug.base.ViewPageAdapter
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler
import com.example.weizhenbin.wangebug.modules.news.controllers.NewController
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsChannelBean
import com.example.weizhenbin.wangebug.views.TitleBar
import com.example.weizhenbin.wangebug.views.emptyview.EmptyView
import java.util.*

/**
 * Created by weizhenbin on 2018/8/6.
 */

class NewsFragment : BaseFragment() {
    lateinit var vpNews: ViewPager
    lateinit var tlNewsType: TabLayout
    var pageAdapter: ViewPageAdapter? = null
    internal var fragments: MutableList<BaseFragment> = ArrayList()
    internal var channelListBeen: List<YiYuanNewsChannelBean.ShowapiResBodyBean.ChannelListBean>? = null
    lateinit var tbTitle: TitleBar
    lateinit var evEmpty: EmptyView

    override val contentViewLayoutId: Int
        get() = R.layout.fm_news

    override fun initFragment(view: View?) {
        initViews(view!!)
        initEvent()
        setData()
        if (channelListBeen == null || channelListBeen!!.isEmpty()) {
            getChannelData()
        }
    }

    override fun loadData() {

    }

    private fun getChannelData() {
        NewController.getNewsChannelData(object : DataResultAdapter<YiYuanNewsChannelBean>() {
            override fun onStart() {
                super.onStart()
                evEmpty.visibility = View.VISIBLE
                evEmpty.loading(true)
            }

            override fun onSuccess(yiYuanNewsChannelBean: YiYuanNewsChannelBean?) {
                super.onSuccess(yiYuanNewsChannelBean)
                if (yiYuanNewsChannelBean != null && yiYuanNewsChannelBean.showapi_res_code == 0) {
                    if (yiYuanNewsChannelBean.showapi_res_body != null && yiYuanNewsChannelBean.showapi_res_body!!.channelList != null) {
                        evEmpty.visibility = View.GONE
                        channelListBeen = yiYuanNewsChannelBean.showapi_res_body!!.channelList
                        val size = channelListBeen!!.size
                        fragments.add(NewsListFragment.getFragment("", "全部"))
                        for (i in 0 until size) {
                            val bean = channelListBeen!![i]
                            fragments.add(NewsListFragment.getFragment(bean.channelId, bean.name))
                        }
                        pageAdapter!!.notifyDataSetChanged()
                    } else {
                        evEmpty.visibility = View.VISIBLE
                        evEmpty.emptyData()
                    }
                } else {
                    evEmpty.visibility = View.VISIBLE
                    evEmpty.emptyData()
                }
            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                evEmpty.visibility = View.VISIBLE
                evEmpty.emptyData()
            }
        })
    }

    private fun setData() {
        pageAdapter = ViewPageAdapter(childFragmentManager, fragments)
        vpNews.adapter = pageAdapter
    }

    private fun initViews(view: View) {
        vpNews = view.findViewById(R.id.vp_news)
        tlNewsType = view.findViewById(R.id.tl_news_type)
        tbTitle = view.findViewById(R.id.tb_title)
        evEmpty = view.findViewById(R.id.ev_empty)
        evEmpty.setProgressBarVisibility(View.VISIBLE)
    }

    private fun initEvent() {
        tbTitle.setLeftOnClickListener {
            if (activity is IOpenMenuHandler) {
                (activity as IOpenMenuHandler).openMenu()
            }
        }
        evEmpty.setAction { getChannelData() }
    }

}
