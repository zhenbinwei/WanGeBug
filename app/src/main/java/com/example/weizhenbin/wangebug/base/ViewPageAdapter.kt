package com.example.weizhenbin.wangebug.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

/**
 * Created by weizhenbin on 18/8/12.
 */

class ViewPageAdapter (fm: FragmentManager, private val fragments: List<BaseFragment>?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments!![position]
    }

    override fun getCount(): Int {
        return fragments?.size ?: 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments!![position].pageTitle
    }

    override fun getItemPosition(any: Any): Int {
        return PagerAdapter.POSITION_NONE
    }


}
