package com.example.weizhenbin.wangebug.modules.code.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.modules.code.entity.SearchHotKeyDataBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/30.
 */

public class CodeSearchHotKeyFlowAdapter extends TagAdapter<SearchHotKeyDataBean.DataBean> {

    public CodeSearchHotKeyFlowAdapter(List<SearchHotKeyDataBean.DataBean> datas) {
        super(datas);
    }

    @Override
    public View getView(FlowLayout parent, int position, SearchHotKeyDataBean.DataBean dataBean) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_hot_key_list_item_tag_layout,parent,false);
        TextView tvTagName=view.findViewById(R.id.tv_tag_name);
        tvTagName.setText(dataBean.getName());
        return view;
    }

}
