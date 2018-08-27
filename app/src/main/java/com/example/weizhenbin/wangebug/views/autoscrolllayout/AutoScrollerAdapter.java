package com.example.weizhenbin.wangebug.views.autoscrolllayout;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by weizhenbin on 2018/8/27.
 */

public abstract class AutoScrollerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


    public abstract int getReadCount();


}
