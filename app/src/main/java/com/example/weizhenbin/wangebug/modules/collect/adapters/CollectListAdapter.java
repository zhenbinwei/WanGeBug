package com.example.weizhenbin.wangebug.modules.collect.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Html;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter;
import com.example.weizhenbin.wangebug.modules.collect.entity.TBCollectBean;

import java.util.List;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

/**
 * Created by weizhenbin on 2018/10/10.
 */
public class CollectListAdapter extends BaseSimpleAdapter<TBCollectBean,BaseViewHolder> {
    public CollectListAdapter(@Nullable Context context, @Nullable List<TBCollectBean> data) {
        super(context, R.layout.collect_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TBCollectBean item) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle(),FROM_HTML_MODE_COMPACT));
            helper.setText(R.id.tv_desc, Html.fromHtml(item.getUrl(),FROM_HTML_MODE_COMPACT));
        }else {
            helper.setText(R.id.tv_title,Html.fromHtml(item.getTitle()));
            helper.setText(R.id.tv_desc, Html.fromHtml(item.getUrl()));
        }
        helper.addOnClickListener(R.id.ll_item);
    }
}
