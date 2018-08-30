package com.example.weizhenbin.wangebug.modules.code.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.modules.code.entity.SystemTreeDataBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/30.
 */

public class CodeSystemTreeFlowAdapter extends TagAdapter<SystemTreeDataBean.DataBean.ChildrenBean> {

    public CodeSystemTreeFlowAdapter(List<SystemTreeDataBean.DataBean.ChildrenBean> datas) {
        super(datas);
    }

    @Override
    public View getView(final FlowLayout parent, int position, SystemTreeDataBean.DataBean.ChildrenBean childrenBean) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.system_tree_list_item_tag_layout,parent,false);
        TextView tvTagName=view.findViewById(R.id.tv_tag_name);
        tvTagName.setText(childrenBean.getName());
        return view;
    }
}
