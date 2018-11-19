package com.example.weizhenbin.wangebug.modules.code.adapters

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.modules.code.entity.SystemTreeDataBean
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

/**
 * Created by weizhenbin on 2018/8/30.
 */

class CodeSystemTreeFlowAdapter(datas: List<SystemTreeDataBean.DataBean.ChildrenBean>) : TagAdapter<SystemTreeDataBean.DataBean.ChildrenBean>(datas) {

    override fun getView(parent: FlowLayout, position: Int, childrenBean: SystemTreeDataBean.DataBean.ChildrenBean): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.system_tree_list_item_tag_layout, parent, false)
        val tvTagName = view.findViewById<TextView>(R.id.tv_tag_name)
        tvTagName.text = childrenBean.name
        return view
    }
}
