package com.example.weizhenbin.wangebug.modules.recreation.adapters;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.weizhenbin.wangebug.base.BaseFragment;

import java.util.List;

/**
 * Created by weizhenbin on 18/8/12.
 */

public class JokeViewPageAdapter extends FragmentStatePagerAdapter {


    private List<BaseFragment> fragments;

    public JokeViewPageAdapter(FragmentManager fm,List<BaseFragment> fragments) {
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
    public Parcelable saveState() {
        return null;
    }

}
