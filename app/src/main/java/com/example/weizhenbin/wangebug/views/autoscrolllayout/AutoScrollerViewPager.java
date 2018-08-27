package com.example.weizhenbin.wangebug.views.autoscrolllayout;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by weizhenbin on 2018/8/27.
 * 自动滚懂viewpgae
 */

public class AutoScrollerViewPager extends ViewPager {

    private static final int SCROLLER_KEY=100;

    private Handler handler;

    /**时间间隔 毫秒
     * */
    private int timeIntervalMs=3000;

    private boolean autoScrollAble=true;

    public AutoScrollerViewPager(@NonNull Context context) {
        this(context,null);
    }

    public AutoScrollerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initHandler();
    }

    public void setTimeIntervalMs(int timeIntervalMs) {
        this.timeIntervalMs = timeIntervalMs;
    }

    private static class ViewPageHanlder extends Handler{
        private WeakReference<AutoScrollerViewPager> weakReference;
        private PagerAdapter pagerAdapter;
        private int pageCount;
        private int currentIndex=0;
        private ViewPageHanlder(AutoScrollerViewPager autoScrollerViewPager) {
            this.weakReference = new WeakReference<AutoScrollerViewPager>(autoScrollerViewPager);
            pagerAdapter= weakReference.get().getAdapter();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==SCROLLER_KEY){
                Log.d("ViewPageHanlder", "pagerAdapter:" + pagerAdapter);
                if (pagerAdapter==null) {
                    pagerAdapter=weakReference.get().getAdapter();
                    if (pagerAdapter!=null) {
                        pageCount = pagerAdapter.getCount();
                    }
                }
                currentIndex=weakReference.get().getCurrentItem();
                if (currentIndex<pageCount) {
                    currentIndex++;
                }
                weakReference.get().setCurrentItem(currentIndex);
                sendEmptyMessageDelayed(SCROLLER_KEY,weakReference.get().timeIntervalMs);
                Log.d("ViewPageHanlder", "自动滚动:"+currentIndex+"pageCount:"+pageCount);
            }
        }
    }

    private void initHandler() {
        handler=new ViewPageHanlder(this);
    }


    @Override
    public  void setAdapter(@Nullable PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (autoScrollAble) {
            handler.sendEmptyMessageDelayed(SCROLLER_KEY, timeIntervalMs);
        }
    }

    public void setAutoScrollAble(boolean autoScrollAble) {
        this.autoScrollAble = autoScrollAble;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }
}
