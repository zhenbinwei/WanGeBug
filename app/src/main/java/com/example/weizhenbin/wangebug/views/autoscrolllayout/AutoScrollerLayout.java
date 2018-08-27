package com.example.weizhenbin.wangebug.views.autoscrolllayout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.tools.PhoneTool;

/**
 * Created by weizhenbin on 2018/8/27.
 * 自动轮播布局
 */

public class AutoScrollerLayout extends RelativeLayout {

    private AutoScrollerViewPager asvpViewpage;
    private LinearLayout llIndicator;
    private AutoScrollerAdapter pageAadpter;
    public AutoScrollerLayout(Context context) {
        this(context,null);
    }

    public AutoScrollerLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.auto_scroller_layout,this);
        asvpViewpage = findViewById(R.id.asvp_viewpage);
        llIndicator =  findViewById(R.id.ll_indicator);
        initEvent();
    }

    private void initEvent() {
        asvpViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (pageAadpter!=null){
                    if (pageAadpter.getReadCount()>0) {
                        int newPosition = position % pageAadpter.getReadCount();
                        selectIndex(newPosition);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void setPagerAdapter(AutoScrollerAdapter pageAadpter){
        if (pageAadpter!=null) {
            this.pageAadpter=pageAadpter;
            asvpViewpage.setAdapter(pageAadpter);
            if (pageAadpter.getReadCount()>1){
                asvpViewpage.setAutoScrollAble(true);
            }else {
                asvpViewpage.setAutoScrollAble(false);
            }
            addIndicator();
        }
    }

    private void addIndicator() {
        llIndicator.removeAllViews();
        int dotCount=pageAadpter.getReadCount();
        for (int i = 0; i < dotCount; i++) {
            ImageView dotImageView=new ImageView(getContext());
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(PhoneTool.dip2px(getContext(),8),PhoneTool.dip2px(getContext(),8));
            params.rightMargin=PhoneTool.dip2px(getContext(),4);
            dotImageView.setBackgroundResource(R.drawable.indicator_dot);
            llIndicator.addView(dotImageView,params);
        }
    }
    private void selectIndex(int index){
        if (index>=0&&index<llIndicator.getChildCount()){
            int childCount=llIndicator.getChildCount();
            for (int i = 0; i <childCount ; i++) {
                View view= llIndicator.getChildAt(i);
                if (index==i){
                    view.setSelected(true);
                }else {
                    view.setSelected(false);
                }
            }
        }
    }
}
