package com.example.weizhenbin.wangebug.modules.news.uis;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler;
import com.example.weizhenbin.wangebug.modules.news.controllers.NewController;
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsChannelBean;
import com.example.weizhenbin.wangebug.views.TitleBar;
import com.example.weizhenbin.wangebug.views.emptyview.EmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/6.
 */

public class NewsFragment extends BaseFragment {
    ViewPager vpNews;
    TabLayout tlNewsType;
    ViewPageAdapter pageAdapter=null;
    List<BaseFragment> fragments=new ArrayList<>();
    List<YiYuanNewsChannelBean.ShowapiResBodyBean.ChannelListBean> channelListBeen;
    TitleBar tbTitle;
    EmptyView evEmpty;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fm_news;
    }

    @Override
    protected void initFragment(View view) {
        initViews(view);
        initEvent();
        setData();
        if (channelListBeen==null||channelListBeen.isEmpty()) {
            getChannelData();
        }
    }

    @Override
    protected void loadData() {

    }

    private void getChannelData() {
        NewController.getNewsChannelData(new DataResultAdapter<YiYuanNewsChannelBean>(){
            @Override
            public void onStart() {
                super.onStart();
                evEmpty.setVisibility(View.VISIBLE);
                evEmpty.loading(true);
            }
            @Override
            public void onSuccess(YiYuanNewsChannelBean yiYuanNewsChannelBean) {
                super.onSuccess(yiYuanNewsChannelBean);
                if (yiYuanNewsChannelBean!=null&&yiYuanNewsChannelBean.getShowapi_res_code()==0){
                    if (yiYuanNewsChannelBean.getShowapi_res_body()!=null&&yiYuanNewsChannelBean.getShowapi_res_body().getChannelList()!=null){
                        evEmpty.setVisibility(View.GONE);
                        channelListBeen=yiYuanNewsChannelBean.getShowapi_res_body().getChannelList();
                        int size=channelListBeen.size();
                        fragments.add(NewsListFragment.getFragment("","全部"));
                        for (int i = 0; i < size; i++) {
                            YiYuanNewsChannelBean.ShowapiResBodyBean.ChannelListBean bean=channelListBeen.get(i);
                           fragments.add(NewsListFragment.getFragment(bean.getChannelId(),bean.getName()));
                        }
                        pageAdapter.notifyDataSetChanged();
                    }else {
                        evEmpty.setVisibility(View.VISIBLE);
                        evEmpty.emptyData();
                    }
                }else {
                    evEmpty.setVisibility(View.VISIBLE);
                    evEmpty.emptyData();
                }
            }
            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                evEmpty.setVisibility(View.VISIBLE);
                evEmpty.emptyData();
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
        evEmpty=view.findViewById(R.id.ev_empty);
        evEmpty.setProgressBarVisibility(View.VISIBLE);
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
        evEmpty.setAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChannelData();
            }
        });
    }

}
