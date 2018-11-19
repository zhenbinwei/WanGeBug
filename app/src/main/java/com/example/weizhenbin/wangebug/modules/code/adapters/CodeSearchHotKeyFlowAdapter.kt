package com.example.weizhenbin.wangebug.modules.code.adapters

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.modules.code.entity.SearchHotKeyDataBean
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import java.util.*

/**
 * Created by weizhenbin on 2018/8/30.
 */

class CodeSearchHotKeyFlowAdapter(datas: List<SearchHotKeyDataBean.DataBean>?) : TagAdapter<SearchHotKeyDataBean.DataBean>(datas) {

    private val colors = intArrayOf(-0x2dbcfb, -0x5000c0, -0xbd6c21, -0x2bdf77)

    private val colorRandom = Random()

    override fun getView(parent: FlowLayout, position: Int, dataBean: SearchHotKeyDataBean.DataBean): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_hot_key_list_item_tag_layout, parent, false)
        val tvTagName = view.findViewById<TextView>(R.id.tv_tag_name)
        tvTagName.text = dataBean.name
        /* int index=colorRandom.nextInt(4);
        tvTagName.setTextColor(colors[index]);*/
        return view
    }

}
