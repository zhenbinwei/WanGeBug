package com.example.weizhenbin.wangebug.views.emptyview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;

/**
 * Created by weizhenbin on 2018/9/27.
 */

public class EmptyView extends LinearLayout implements IEmptyViewProxy{

    private TextView tvEmpty;
    private TextView tvEvent;
    private com.example.weizhenbin.wangebug.views.LoadingView lvLoading;


    public EmptyView(Context context) {
        this(context,null);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.empty_layout,this);
        setBackgroundColor(getResources().getColor(R.color.colorWhite));
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        setClickable(true);
        tvEmpty = findViewById(R.id.tv_empty);
        tvEvent =  findViewById(R.id.tv_event);
        lvLoading=findViewById(R.id.lv_loading);
    }

    public void setEmptyText(String text){
        if (tvEmpty!=null){
            tvEmpty.setText(text);
        }
    }

    public void setActionText(String text){
        if (tvEvent!=null){
            tvEvent.setText(text);
        }
    }

    public void setAction(View.OnClickListener clickListener){
        if (tvEvent==null){
            return;
        }
        if (clickListener==null){
            tvEvent.setVisibility(View.GONE);
        }else {
            tvEvent.setVisibility(View.INVISIBLE);
        }
        tvEvent.setOnClickListener(clickListener);
    }
    public void setEventVisibility(int visibility){
        if (tvEvent!=null) {
            tvEvent.setVisibility(visibility);
        }
    }

    public void loading(){
        loading(false);
    }

    @Override
    public void loading(boolean isShowProgressBar) {
        setEmptyText(getContext().getString(R.string.loading_string));
        setEventVisibility(View.INVISIBLE);
        setProgressBarVisibility(isShowProgressBar?View.VISIBLE:View.GONE);
    }

    public void emptyData(){
       emptyData(true);
    }

    @Override
    public void emptyData(boolean showAction) {
        setEmptyText(getContext().getString(R.string.no_data_string));
        setEventVisibility(showAction?View.VISIBLE:View.INVISIBLE);
        setProgressBarVisibility(View.INVISIBLE);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        lvLoading.setVisibility(visibility);
    }


}
