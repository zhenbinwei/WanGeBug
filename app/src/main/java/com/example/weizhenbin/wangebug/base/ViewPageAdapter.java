package com.example.weizhenbin.wangebug.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by weizhenbin on 18/8/12.
 */

public class ViewPageAdapter extends FragmentStatePagerAdapter {


    private List<BaseFragment> fragments;

    public ViewPageAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments==null?0:fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getPageTitle();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
         return POSITION_NONE;
    }


}
