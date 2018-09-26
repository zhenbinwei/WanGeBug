package com.example.weizhenbin.wangebug.modules.code.uis;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler;
import com.example.weizhenbin.wangebug.eventbus.EventCode;
import com.example.weizhenbin.wangebug.eventbus.MessageEvent;
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool;
import com.example.weizhenbin.wangebug.views.TitleBar;
import com.example.weizhenbin.wangebug.views.nestlayout.IRollChange;
import com.example.weizhenbin.wangebug.views.nestlayout.NestLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/6.
 */

public class CodeFragment extends BaseFragment {
    View view;
    TitleBar tbTitle;
    BottomNavigationView bnvNavigation;
    public List<BaseFragment> modeFragments = new ArrayList<>();
    ViewPager vpCodeMode;
    ViewPageAdapter viewPageAdapter;
    NestLayout nlContent;
    private TranslateAnimation mShowAction, mHiddenAction;

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
        initAnim();
        EventBusHandler.register(this);
        nlContent.post(new Runnable() {
            @Override
            public void run() {
                hideTab(PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_HIDE_TAB,true));
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusHandler.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Log.d("CodeFragment", "event:" + event);
        if (event.code== EventCode.HIDE_TAB_CODE){
            hideTab(event.msg!=null&&event.msg==Boolean.valueOf(true));
        }
    }


    private void hideTab(boolean hide){
        if (hide){
            nlContent.setPadding(0,0,0,0);
        }else {
            nlContent.setPadding(0,0,0,bnvNavigation.getHeight());
            bnvNavigation.setVisibility(View.VISIBLE);
        }
    }



    private void initAnim() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(200);
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        mHiddenAction.setDuration(200);
    }

    @Override
    protected void loadData() {

    }

    private void setModes() {
        viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), modeFragments);
        vpCodeMode.setAdapter(viewPageAdapter);
        vpCodeMode.setOffscreenPageLimit(2);
    }

    private void addModes() {
        modeFragments.add(CodeHomeFragment.getFragment());
        modeFragments.add(CodeSystemFragment.getFragment());
        modeFragments.add(CodeProjectFragment.getFragment());
    }

    private void initViews(View view) {
        tbTitle = view.findViewById(R.id.tb_title);
        vpCodeMode = view.findViewById(R.id.vp_code_mode);
        bnvNavigation = view.findViewById(R.id.bnv_navigation);
        nlContent = view.findViewById(R.id.nl_content);
    }

    private void initEvent() {
        tbTitle.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof IOpenMenuHandler) {
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
                switch (item.getItemId()) {
                    case R.id.action_home:
                        vpCodeMode.setCurrentItem(0, false);
                        break;
                    case R.id.action_system:
                        vpCodeMode.setCurrentItem(1, false);
                        break;
                    case R.id.action_project:
                        vpCodeMode.setCurrentItem(2, false);
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
                switch (position) {
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
        nlContent.setiRollChange(new IRollChange() {
            @Override
            public void onRollChange(int dy) {
                if (!PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_HIDE_TAB, true)) {
                    return;
                }
                if (Math.abs(dy) >= 4) {
                    if (dy > 0) {
                        //隐藏
                        if (bnvNavigation.getVisibility() == View.VISIBLE) {
                            bnvNavigation.startAnimation(mHiddenAction);
                            bnvNavigation.setVisibility(View.GONE);
                        }
                    } else if (dy < 0) {
                        //显示
                        if (bnvNavigation.getVisibility() == View.GONE) {
                            bnvNavigation.startAnimation(mShowAction);
                            bnvNavigation.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }
}
