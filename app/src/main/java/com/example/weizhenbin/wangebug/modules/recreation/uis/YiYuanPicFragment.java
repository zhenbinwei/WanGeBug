package com.example.weizhenbin.wangebug.modules.recreation.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.modules.recreation.adapters.YiYuanDataListAdapter;
import com.example.weizhenbin.wangebug.modules.recreation.controllers.JokeController;
import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanPicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/17.
 */

public class YiYuanPicFragment extends BaseFragment {

    SwipeRefreshLayout srlRefresh;
    RecyclerView rvDataList;
    YiYuanDataListAdapter listAdapter;
    int page=1;

    List<YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>  contentlistBeen=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_yiyuan_pic,null);
        initViews(view);
        initData();
        initEvent();
        return view;
    }

    private void initData() {
        listAdapter=new YiYuanDataListAdapter(getContext(),R.layout.yiyuan_pic_item_layout,contentlistBeen);
        rvDataList.setAdapter(listAdapter);
        rvDataList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
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
        JokeController.getYiYuanPicData(page,new DataResultAdapter<YiYuanPicBean>(){
            @Override
            public void onStart() {
                super.onStart();
                if (page==1){
                    srlRefresh.setRefreshing(true);
                }
            }

            @Override
            public void onSuccess(YiYuanPicBean yiYuanPicBean) {
                super.onSuccess(yiYuanPicBean);
                srlRefresh.setRefreshing(false);
                if (yiYuanPicBean!=null&&yiYuanPicBean.getShowapi_res_code()==0){
                    if (yiYuanPicBean.getShowapi_res_body()!=null&&yiYuanPicBean.getShowapi_res_body().getPagebean()!=null){
                        if (page==1){
                            contentlistBeen.clear();
                        }
                        contentlistBeen.addAll(yiYuanPicBean.getShowapi_res_body().getPagebean().getContentlist());
                        if(page==1){
                            listAdapter.setNewData(contentlistBeen);
                        }else {
                            listAdapter.notifyDataSetChanged();
                        }
                        if (yiYuanPicBean.getShowapi_res_body().getPagebean().isLastPage()){
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


    private void initViews(View view) {
        srlRefresh=view.findViewById(R.id.srl_refresh);
        rvDataList=view.findViewById(R.id.rv_data_list);

    }

    @Override
    protected void loadData() {
        if (contentlistBeen.isEmpty()) {
            getData();
        }
    }

    @Override
    protected String getPageTitle() {
        return "图片";
    }
}
