package com.example.weizhenbin.wangebug.modules.news.uis;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.modules.news.adapters.NewsListAdapter;
import com.example.weizhenbin.wangebug.modules.news.controllers.NewController;
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/23.
 */

public class NewsListFragment extends BaseFragment {

    private SwipeRefreshLayout srlRefresh;
    private RecyclerView rvDataList;
    private NewsListAdapter listAdapter;
    int page=1;

    List<YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentlistBeen=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_news_list,null);
        initViews(view);
        initData();
        initEvent();
        getData();
        return view;
    }


    private void initData() {
        srlRefresh.setRefreshing(true);
        listAdapter=new NewsListAdapter(getContext(),contentlistBeen);
        rvDataList.setAdapter(listAdapter);
        rvDataList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration itemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable=getResources().getDrawable(R.drawable.divider_line);
        itemDecoration.setDrawable(drawable);
        rvDataList.addItemDecoration(itemDecoration);
        listAdapter.bindToRecyclerView(rvDataList);
        listAdapter.disableLoadMoreIfNotFullPage();
    }
    protected void initEvent() {
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                getData();
            }
        });
        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData();
            }
        },rvDataList);
    }
    private void getData(){
        NewController.getNewsListData(page,"",new DataResultAdapter<YiYuanNewsBean>(){
            @Override
            public void onSuccess(YiYuanNewsBean yiYuanNewsBean) {
                super.onSuccess(yiYuanNewsBean);
                srlRefresh.setRefreshing(false);
                if (yiYuanNewsBean!=null&&yiYuanNewsBean.getShowapi_res_code()==0){
                    if (yiYuanNewsBean.getShowapi_res_body()!=null&&yiYuanNewsBean.getShowapi_res_body().getPagebean()!=null){
                        if (page==1){
                            contentlistBeen.clear();
                        }
                        contentlistBeen.addAll(yiYuanNewsBean.getShowapi_res_body().getPagebean().getContentlist());
                    }
                    if(page==1){
                        listAdapter.setNewData(contentlistBeen);
                    }else {
                        listAdapter.notifyDataSetChanged();
                    }
                    listAdapter.loadMoreComplete();
                    page++;
                }
            }
        });
    }

    private void initViews(View view) {
        srlRefresh =  view.findViewById(R.id.srl_refresh);
        rvDataList = view.findViewById(R.id.rv_data_list);
    }

    @Override
    public String getPageTitle() {
        return "测试";
    }
}
