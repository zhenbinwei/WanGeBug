package com.example.weizhenbin.wangebug.modules.recreation.uis

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.ViewPageAdapter
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler
import com.example.weizhenbin.wangebug.views.TitleBar
import java.util.*

/**
 * Created by weizhenbin on 2018/8/6.
 */

class RecreationFragment : BaseFragment() {
    lateinit var vpJoke: ViewPager
    private lateinit var tlJokeType: TabLayout
     var pageAdapter: ViewPageAdapter? = null
     var fragments: MutableList<BaseFragment> = ArrayList()
    lateinit var tbTitle: TitleBar
    override val contentViewLayoutId: Int
        get() = R.layout.fm_recreation

    override fun initFragment(view: View?) {
        initViews(view!!)
        initEvent()
        if (fragments.isEmpty()) {
            addFragments()
        }
        setData()
    }

    override fun loadData() {

    }

    private fun addFragments() {
        fragments.add(YiYuanPicFragment())
    }

    private fun setData() {
        pageAdapter = ViewPageAdapter(childFragmentManager, fragments)
        vpJoke.adapter = pageAdapter
    }


    private fun initViews(view: View) {
        vpJoke = view.findViewById(R.id.vp_joke)
        tlJokeType = view.findViewById(R.id.tl_joke_type)
        tbTitle = view.findViewById(R.id.tb_title)
    }

    private fun initEvent() {
        tbTitle.setLeftOnClickListener {
            if (activity is IOpenMenuHandler) {
                (activity as IOpenMenuHandler).openMenu()
            }
        }
    }
}
