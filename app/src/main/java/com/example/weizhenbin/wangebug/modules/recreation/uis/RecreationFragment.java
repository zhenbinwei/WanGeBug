package com.example.weizhenbin.wangebug.modules.recreation.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/6.
 */

public class RecreationFragment extends Fragment {
    ViewPager vpJoke;
    TabLayout tlJokeType;
    ViewPageAdapter pageAdapter=null;
    List<BaseFragment> fragments=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_recreation,null);
        initViews(view);
        initEvent();
        if (fragments.isEmpty()) {
            addFragments();
        }
        setData();
        return view;
    }

    private void addFragments() {
        fragments.add(new YiYuanPicFragment());
    }

    private void setData() {
        pageAdapter=  new ViewPageAdapter(getChildFragmentManager(),fragments);
        vpJoke.setAdapter(pageAdapter);
    }


    private void initViews(View view) {
        vpJoke=view.findViewById(R.id.vp_joke);
        tlJokeType=view.findViewById(R.id.tl_joke_type);
    }

    private void initEvent() {
    }
}
