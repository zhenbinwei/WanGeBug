package com.example.weizhenbin.wangebug.modules.recreation.uis;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

    List<YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> contentListBeen =new ArrayList<>();


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fm_yiyuan_pic;
    }

    @Override
    protected void initFragment(View view) {
        initViews(view);
        initData();
        initEvent();
    }

    private void initData() {
        listAdapter=new YiYuanDataListAdapter(getContext(),R.layout.yiyuan_pic_item_layout, contentListBeen);
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
        listAdapter.setAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page=1;
                getData();
            }
        });
    }

    private void getData(){
        JokeController.getYiYuanPicData(page,new DataResultAdapter<YiYuanPicBean>(){

            @Override
            public void onSuccess(YiYuanPicBean yiYuanPicBean) {
                super.onSuccess(yiYuanPicBean);
                srlRefresh.setRefreshing(false);
                if (yiYuanPicBean!=null&&yiYuanPicBean.getShowapi_res_code()==0){
                    if (yiYuanPicBean.getShowapi_res_body()!=null&&yiYuanPicBean.getShowapi_res_body().getPagebean()!=null){
                        if (page==1){
                            contentListBeen.clear();
                        }
                        contentListBeen.addAll(yiYuanPicBean.getShowapi_res_body().getPagebean().getContentlist());
                        if(page==1){
                            listAdapter.setNewData(contentListBeen);
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
                }else {
                    if (page==1){
                        listAdapter.emptyData();
                    }else {
                        listAdapter.loadMoreFail();
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
                srlRefresh.setRefreshing(false);
                if (page==1){
                    listAdapter.emptyData();
                }else {
                    listAdapter.loadMoreFail();
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
        if (contentListBeen.isEmpty()) {
            getData();
        }
    }

    @Override
    protected String getPageTitle() {
        return "图片";
    }
}
