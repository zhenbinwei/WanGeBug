package com.example.weizhenbin.wangebug.modules.code.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter;
import com.example.weizhenbin.wangebug.modules.code.entity.TBSearchHistoryKeyBean;

import java.util.List;

/**
 * Created by weizhenbin on 2018/10/31.
 */
public class CodeSearchHistoryAdapter extends BaseSimpleAdapter<TBSearchHistoryKeyBean,BaseViewHolder> {

    IDataCallback iDataCallback;
    public CodeSearchHistoryAdapter(@Nullable Context context, @Nullable final List<TBSearchHistoryKeyBean> data) {
        super(context, R.layout.search_history_list_item, data);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                      if (iDataCallback!=null){
                          iDataCallback.onClickItem(position);
                      }
            }
        });
    }

    public void setiDataCallback(IDataCallback iDataCallback) {
        this.iDataCallback = iDataCallback;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TBSearchHistoryKeyBean item) {
         helper.setText(R.id.tv_history,item.getKey());
         helper.addOnClickListener(R.id.ll_item);
         helper.getView(R.id.iv_del).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (iDataCallback!=null){
                     iDataCallback.onDelItem(helper.getLayoutPosition());
                 }
             }
         });
    }


    public interface IDataCallback{
        void onClickItem(int position);
        void onDelItem(int position);
    }
}
