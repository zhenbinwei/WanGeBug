package com.example.weizhenbin.wangebug.modules.code.uis;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
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

public class CodeFragment extends BaseFragment {
    View view;
    TitleBar tbTitle;
    BottomNavigationView bnvNavigation;
   public List<BaseFragment> modeFragments=new ArrayList<>();
    ViewPager vpCodeMode;
    ViewPageAdapter viewPageAdapter;


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fm_code;
    }

    @Override
    protected void initFragment(View view) {
        initViews(view);
        initEvent();
        if (modeFragments.isEmpty()) {
            addModes();
        }
        setModes();
    }

    @Override
    protected void loadData() {

    }

    private void setModes() {
        viewPageAdapter=new ViewPageAdapter(getChildFragmentManager(),modeFragments);
        vpCodeMode.setAdapter(viewPageAdapter);
        vpCodeMode.setOffscreenPageLimit(2);
    }

    private void addModes() {
        modeFragments.add(CodeHomeFragment.getFragment());
        modeFragments.add(CodeSystemFragment.getFragment());
        modeFragments.add(CodeProjectFragment.getFragment());
    }

    private void initViews(View view) {
        tbTitle=view.findViewById(R.id.tb_title);
        vpCodeMode=view.findViewById(R.id.vp_code_mode);
        bnvNavigation=view.findViewById(R.id.bnv_navigation);
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
        tbTitle.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeSearchActivity.startActivity(getContext());
            }
        });
        bnvNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.action_home:
                       vpCodeMode.setCurrentItem(0,false);
                       break;
                   case R.id.action_system:
                       vpCodeMode.setCurrentItem(1,false);
                       break;
                   case R.id.action_project:
                       vpCodeMode.setCurrentItem(2,false);
                       break;
               }
                return true;
            }
        });
        vpCodeMode.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                 switch (position){
                     case 0:
                         bnvNavigation.setSelectedItemId(R.id.action_home);
                         break;
                     case 1:
                         bnvNavigation.setSelectedItemId(R.id.action_system);
                         break;
                     case 2:
                         bnvNavigation.setSelectedItemId(R.id.action_project);
                         break;
                 }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
