package com.example.weizhenbin.wangebug.modules.code.uis

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.DataResultAdapter
import com.example.weizhenbin.wangebug.base.ViewPageAdapter
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController
import com.example.weizhenbin.wangebug.modules.code.entity.ProjectTreeDataBean
import com.example.weizhenbin.wangebug.views.emptyview.EmptyView
import java.util.*

/**
 * Created by weizhenbin on 2018/8/24.
 */

class CodeProjectFragment : BaseFragment() {

    lateinit var vpProject: ViewPager
    lateinit var tlProjectType: TabLayout
    internal var pageAdapter: ViewPageAdapter? = null
    internal var fragments: MutableList<BaseFragment> = ArrayList()
    internal var dataBeanList: List<ProjectTreeDataBean.DataBean>? = null
    lateinit var evEmpty: EmptyView

    override val contentViewLayoutId: Int
        get() = R.layout.fm_code_project


    override var pageTitle: String
        get() = "首页"
        set(value: String) {
            super.pageTitle = value
        }

    override fun initFragment(view: View?) {
        initViews(view!!)
        initEvent()
        setData()
    }


    override fun loadData() {
        if (dataBeanList == null || dataBeanList!!.isEmpty()) {
            getProjectTreeData()
        }
    }

    private fun getProjectTreeData() {
        CodeController.getProjectTreeData(object : DataResultAdapter<ProjectTreeDataBean>() {
            override fun onStart() {
                super.onStart()
                evEmpty.visibility = View.VISIBLE
                evEmpty.loading(true)
            }

            override fun onSuccess(projectTreeDataBean: ProjectTreeDataBean?) {
                super.onSuccess(projectTreeDataBean)
                if (projectTreeDataBean != null && projectTreeDataBean.errorCode == 0) {
                    evEmpty.visibility = View.GONE
                    if (projectTreeDataBean.data != null && projectTreeDataBean.data != null) {
                        dataBeanList = projectTreeDataBean.data
                        val size = dataBeanList!!.size
                        for (i in 0 until size) {
                            val bean = dataBeanList!![i]
                            fragments.add(ProjectListFragment.getFragment(bean.id.toString() + "", bean.name))
                        }
                        pageAdapter!!.notifyDataSetChanged()
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
        vpProject.adapter = pageAdapter
    }

    private fun initEvent() {
        evEmpty.setAction { getProjectTreeData() }
    }

    private fun initViews(view: View) {
        vpProject = view.findViewById(R.id.vp_project)
        tlProjectType = view.findViewById(R.id.tl_project_type)
        evEmpty = view.findViewById(R.id.ev_empty)
        evEmpty.setProgressBarVisibility(View.VISIBLE)
    }

    companion object {

        val fragment: CodeProjectFragment
            get() = CodeProjectFragment()
    }
}
