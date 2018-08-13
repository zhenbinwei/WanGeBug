package com.example.weizhenbin.wangebug.modules.recreation.adapters;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.modules.recreation.entity.JokeContentlistBaseBean;

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/13.
 */

public class JokeListAdapter extends BaseMultiItemQuickAdapter<JokeContentlistBaseBean,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public JokeListAdapter(List data) {
        super(data);
    }


    @Override
    protected void convert(BaseViewHolder helper, JokeContentlistBaseBean item) {

    }
}
