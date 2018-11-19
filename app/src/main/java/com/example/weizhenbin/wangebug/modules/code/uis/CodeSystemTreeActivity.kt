package com.example.weizhenbin.wangebug.modules.code.uis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseActivity
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.ViewPageAdapter
import com.example.weizhenbin.wangebug.modules.code.entity.SystemTreeDataBean
import com.example.weizhenbin.wangebug.views.TitleBar
import java.util.*

/**
 * Created by weizhenbin on 2018/8/30.
 */

class CodeSystemTreeActivity : BaseActivity() {

    lateinit var vpSystemTree: ViewPager
    lateinit var tlSystemTreeType: TabLayout
    lateinit var titleBar: TitleBar
     var pageAdapter: ViewPageAdapter? = null
     var fragments: MutableList<BaseFragment> = ArrayList()
     var dataBean: SystemTreeDataBean.DataBean? = null
     var index: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_tree)
        initViews()
        initEvent()
        setViews()
        setData()
    }

    private fun setData() {
        val intent = intent
        dataBean = intent.getSerializableExtra("dataBean") as SystemTreeDataBean.DataBean
        index = intent.getIntExtra("index", 0)
        if (dataBean != null) {
            if (dataBean!!.children != null) {
                val size = dataBean!!.children!!.size
                for (i in 0 until size) {
                    val bean = dataBean!!.children!![i]
                    fragments.add(CodeSystemTreeChildrenFragment.getFragment(bean.id, bean.name?:""))
                }
                pageAdapter!!.notifyDataSetChanged()
            }
            vpSystemTree.setCurrentItem(index, false)
            titleBar.setTitle(dataBean!!.name)
        }
    }

    private fun setViews() {
        pageAdapter = ViewPageAdapter(supportFragmentManager, fragments)
        vpSystemTree.adapter = pageAdapter
    }

    private fun initEvent() {
        titleBar.setLeftOnClickListener { finish() }
    }

    private fun initViews() {
        vpSystemTree = findViewById(R.id.vp_system_tree)
        tlSystemTreeType = findViewById(R.id.tl_system_tree_type)
        titleBar = findViewById(R.id.tb_title)
    }

    companion object {


        fun startActivity(context: Context, dataBean: SystemTreeDataBean.DataBean, index: Int) {
            val intent = Intent(context, CodeSystemTreeActivity::class.java)
            intent.putExtra("dataBean", dataBean)
            intent.putExtra("index", index)
            context.startActivity(intent)
        }
    }
}
