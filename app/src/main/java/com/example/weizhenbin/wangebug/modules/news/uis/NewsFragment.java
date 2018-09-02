package com.example.weizhenbin.wangebug.modules.news.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler;
import com.example.weizhenbin.wangebug.modules.news.controllers.NewController;
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsChannelBean;
import com.example.weizhenbin.wangebug.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/6.
 */

public class NewsFragment extends Fragment {
    ViewPager vpNews;
    TabLayout tlNewsType;
    ViewPageAdapter pageAdapter=null;
    List<BaseFragment> fragments=new ArrayList<>();
    List<YiYuanNewsChannelBean.ShowapiResBodyBean.ChannelListBean> channelListBeen;
    TitleBar tbTitle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("onCreateView", "NewsFragment onCreateView");
        View  view=inflater.inflate(R.layout.fm_news,null);
        initViews(view);
        initEvent();
        setData();
        if (channelListBeen==null||channelListBeen.isEmpty()) {
            getChannleData();
        }
        return view;
    }

    private void getChannleData() {
        NewController.getNewsChannelData(new DataResultAdapter<YiYuanNewsChannelBean>(){
            @Override
            public void onSuccess(YiYuanNewsChannelBean yiYuanNewsChannelBean) {
                super.onSuccess(yiYuanNewsChannelBean);
                if (yiYuanNewsChannelBean!=null&&yiYuanNewsChannelBean.getShowapi_res_code()==0){
                    if (yiYuanNewsChannelBean.getShowapi_res_body()!=null&&yiYuanNewsChannelBean.getShowapi_res_body().getChannelList()!=null){
                        channelListBeen=yiYuanNewsChannelBean.getShowapi_res_body().getChannelList();
                        int size=channelListBeen.size();
                        fragments.add(NewsListFragment.getFragment("","全部"));
                        for (int i = 0; i < size; i++) {
                            YiYuanNewsChannelBean.ShowapiResBodyBean.ChannelListBean bean=channelListBeen.get(i);
                           fragments.add(NewsListFragment.getFragment(bean.getChannelId(),bean.getName()));
                        }
                        pageAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void setData() {
        pageAdapter=  new ViewPageAdapter(getChildFragmentManager(),fragments);
        vpNews.setAdapter(pageAdapter);
    }
    private void initViews(View view) {
        vpNews=view.findViewById(R.id.vp_news);
        tlNewsType=view.findViewById(R.id.tl_news_type);
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
