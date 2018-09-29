package com.example.weizhenbin.wangebug.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.views.emptyview.EmptyView;
import com.example.weizhenbin.wangebug.views.emptyview.IEmptyViewProxy;

import java.util.List;

/**
 * Created by weizhenbin on 2018/9/27.
 */

public abstract class BaseSimpleAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T,K> implements IEmptyViewProxy{
    private EmptyView emptyView;

    public BaseSimpleAdapter( @Nullable Context context,@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        mContext=context;
        emptyView=new EmptyView(context);
        setEmptyView(emptyView);
    }

    public BaseSimpleAdapter( @Nullable Context context,@Nullable List<T> data) {
       this(context,0,data);
    }

    public BaseSimpleAdapter( @Nullable Context context,@LayoutRes int layoutResId) {
       this(context,layoutResId,null);
    }


    public void setEmptyText(String text){
        if (emptyView!=null){
            emptyView.setEmptyText(text);
        }
    }

    public void setActionText(String text){
        if (emptyView!=null){
            emptyView.setActionText(text);
        }
    }

    public void setAction(View.OnClickListener clickListener){
        if (emptyView!=null){
            emptyView.setAction(clickListener);
        }

    }
    public void setEventVisibility(int visibility){
        if (emptyView!=null) {
            emptyView.setEventVisibility(visibility);
        }
    }

    public void loading(){
        loading(false);
    }

    @Override
    public void loading(boolean isShowProgressBar) {
        if (emptyView!=null){
            emptyView.loading(isShowProgressBar);
        }
    }

    public void emptyData(){
       emptyData(true);
    }


    @Override
    public void emptyData(boolean showAction) {
        if (emptyView!=null){
            emptyView.emptyData(showAction);
        }
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        if (emptyView!=null){
            emptyView.setProgressBarVisibility(visibility);
        }
    }
}
