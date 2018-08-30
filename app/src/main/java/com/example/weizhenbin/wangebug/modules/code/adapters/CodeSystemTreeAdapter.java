package com.example.weizhenbin.wangebug.modules.code.adapters;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.modules.code.entity.SystemTreeDataBean;
import com.example.weizhenbin.wangebug.modules.code.uis.CodeSystemTreeActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/30.
 */

public class CodeSystemTreeAdapter extends BaseQuickAdapter<SystemTreeDataBean.DataBean,BaseViewHolder> {


    public CodeSystemTreeAdapter(@Nullable List<SystemTreeDataBean.DataBean> data) {
        super(R.layout.system_tree_list_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SystemTreeDataBean.DataBean item) {
        helper.setText(R.id.tv_tree_name,item.getName());
        TagFlowLayout tagFlowLayout=helper.getView(R.id.tfl_tree_datas);
        tagFlowLayout.setAdapter(new CodeSystemTreeFlowAdapter(item.getChildren()));
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                CodeSystemTreeActivity.startActivity(parent.getContext(),item,position);
                return true;
            }
        });
    }
}
