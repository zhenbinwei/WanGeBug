package com.example.weizhenbin.wangebug.modules.code.uis;

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
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeHomeBannerAdapter;
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeArticleListAdapter;
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController;
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean;
import com.example.weizhenbin.wangebug.modules.code.entity.BannerDataBean;
import com.example.weizhenbin.wangebug.views.autoscrolllayout.AutoScrollerLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/24.
 */

public class CodeHomeFragment extends BaseFragment {

    SwipeRefreshLayout srlRefresh;
    RecyclerView rvDataList;
    CodeArticleListAdapter listAdapter;
    int page=0;
    View bannerHeader;
    AutoScrollerLayout autoScrollerLayout;
    List<ArticleListDataBean.DataBean.DatasBean> datasBeen=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_code_home,null);
        initViews(view);
        initData();
        initEvent();
        if (datasBeen.isEmpty()) {
            getData();
        }
        getBannerData();
        return view;
    }

    private void getData() {
        CodeController.getArticleListData(page,new DataResultAdapter<ArticleListDataBean>(){
            @Override
            public void onStart() {
                super.onStart();
                if (page==0){
                    srlRefresh.setRefreshing(true);
                }
            }

            @Override
            public void onSuccess(ArticleListDataBean articleListDataBean) {
                super.onSuccess(articleListDataBean);
                srlRefresh.setRefreshing(false);
                if (articleListDataBean!=null&&articleListDataBean.getErrorCode()==0){
                    if (articleListDataBean.getData()!=null&&articleListDataBean.getData().getDatas()!=null){
                        if (page==0){
                            datasBeen.clear();
                        }
                        datasBeen.addAll(articleListDataBean.getData().getDatas());
                        if(page==0){
                            listAdapter.setNewData(datasBeen);
                        }else {
                            listAdapter.notifyDataSetChanged();
                        }
                        if (articleListDataBean.getData().isOver()){
                            listAdapter.loadMoreEnd();
                        }else {
                            listAdapter.loadMoreComplete();
                            page++;
                        }
                    }else {
                        listAdapter.loadMoreEnd();
                    }
                }
            }
        });
    }

    private void getBannerData(){
        CodeController.getBannerData(new DataResultAdapter<BannerDataBean>(){

            @Override
            public void onSuccess(BannerDataBean bannerDataBean) {
                super.onSuccess(bannerDataBean);
                if (bannerDataBean!=null&&bannerDataBean.getErrorCode()==0){
                    if (bannerDataBean.getData()!=null&&!bannerDataBean.getData().isEmpty()){
                        autoScrollerLayout.setPagerAdapter(new CodeHomeBannerAdapter(bannerDataBean.getData()));
                    }else {
                        listAdapter.removeHeaderView(bannerHeader);
                    }
                }
            }
            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                listAdapter.removeHeaderView(bannerHeader);
            }
        });
    }

    private void initEvent() {
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=0;
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

    private void initData() {
        listAdapter=new CodeArticleListAdapter(getContext(),datasBeen);
        rvDataList.setAdapter(listAdapter);
        rvDataList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration itemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable=getResources().getDrawable(R.drawable.divider_line);
        itemDecoration.setDrawable(drawable);
        rvDataList.addItemDecoration(itemDecoration);
        listAdapter.bindToRecyclerView(rvDataList);
        listAdapter.disableLoadMoreIfNotFullPage();
        listAdapter.addHeaderView(bannerHeader);
    }

    private void initViews(View view) {
        srlRefresh=view.findViewById(R.id.srl_refresh);
        rvDataList=view.findViewById(R.id.rv_data_list);
        bannerHeader=LayoutInflater.from(getContext()).inflate(R.layout.code_home_banner_header,null);
        autoScrollerLayout=bannerHeader.findViewById(R.id.asl_banner);
    }


    @Override
    public String getPageTitle() {
        return "首页";
    }

    public static CodeHomeFragment getFragment(){
        return new CodeHomeFragment();
    }


}