package com.example.weizhenbin.wangebug.views.autoscrolllayout

import android.support.v4.view.PagerAdapter
import android.view.View

/**
 * Created by weizhenbin on 2018/8/27.
 */

abstract class AutoScrollerAdapter : PagerAdapter() {


    abstract fun readCount(): Int

    override fun getCount(): Int {
        return Integer.MAX_VALUE
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }


}
