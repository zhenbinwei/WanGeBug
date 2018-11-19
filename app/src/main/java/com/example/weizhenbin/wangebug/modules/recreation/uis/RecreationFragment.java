package com.example.weizhenbin.wangebug.modules.recreation.uis;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler;
import com.example.weizhenbin.wangebug.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/6.
 */

public class RecreationFragment extends BaseFragment {
    ViewPager vpJoke;
    TabLayout tlJokeType;
    ViewPageAdapter pageAdapter=null;
    List<BaseFragment> fragments=new ArrayList<>();
    TitleBar tbTitle;
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fm_recreation;
    }

    @Override
    public void initFragment(View view) {
        initViews(view);
        initEvent();
        if (fragments.isEmpty()) {
            addFragments();
        }
        setData();
    }

    @Override
    public void loadData() {

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
        tbTitle=view.findViewById(R.id.tb_title);
    }

    private void initEvent() {
        tbTitle.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof IOpenMenuHandler){
                    ((IOpenMenuHandler) getActivity()).openMenu();
                }
            }
        });
    }
}
