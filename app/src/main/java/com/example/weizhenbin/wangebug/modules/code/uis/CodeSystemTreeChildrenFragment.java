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
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeArticleListAdapter;
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController;
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/30.
 */

public class CodeSystemTreeChildrenFragment extends BaseFragment {

    SwipeRefreshLayout srlRefresh;
    RecyclerView rvDataList;
    CodeArticleListAdapter listAdapter;
    int page=0;
    List<ArticleListDataBean.DataBean.DatasBean> datasBeen=new ArrayList<>();
    int cid;
    String name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_system_tree_children,null);
        initViews(view);
        initData();
        initEvent();
        if (datasBeen.isEmpty()) {
            getData();
        }
        return view;
    }

    private void getData() {
        CodeController.getArticleListData(page,cid+"",new DataResultAdapter<ArticleListDataBean>(){
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

    private void setCid(int cid) {
        this.cid = cid;
    }

    private void setName(String name) {
        this.name = name;
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
    }

    private void initViews(View view) {
        srlRefresh=view.findViewById(R.id.srl_refresh);
        rvDataList=view.findViewById(R.id.rv_data_list);
    }
    @Override
    public String getPageTitle() {
        return name;
    }


    public static  CodeSystemTreeChildrenFragment getFragment(int cid,String name){
        CodeSystemTreeChildrenFragment fragment=new CodeSystemTreeChildrenFragment();
        fragment.setName(name);
        fragment.setCid(cid);
        return fragment;
    }

}
