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
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;
import com.example.weizhenbin.wangebug.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/6.
 */

public class RecreationFragment extends Fragment {
    TitleBar tbRecreation;
    ViewPager vpJoke;
    TabLayout tlJokeType;
    ViewPageAdapter pageAdapter=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_recreation,null);
        initViews(view);
        initEvent();
        setData();
        return view;
    }

    private void setData() {

        List<BaseFragment> fragments=new ArrayList<>();
        fragments.add(new YiYuanPicFragment());
       // fragments.add(JokeTextFragment.getFragment(JokeType.TEXT));
       // fragments.add(JokePicFragment.getFragment(JokeType.PIC));
       // fragments.add(JokeTextFragment.getFragment(JokeType.PIC));
        pageAdapter=  new ViewPageAdapter(getChildFragmentManager(),fragments);
        vpJoke.setAdapter(pageAdapter);


    }


    private void initViews(View view) {
        tbRecreation=view.findViewById(R.id.tb_recreation);
        vpJoke=view.findViewById(R.id.vp_joke);
        tlJokeType=view.findViewById(R.id.tl_joke_type);
    }

    private void initEvent() {
        tbRecreation.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof IOpenMenuHandler){
                    ((IOpenMenuHandler) getActivity()).openMenu();
                }
            }
        });
    }
}
