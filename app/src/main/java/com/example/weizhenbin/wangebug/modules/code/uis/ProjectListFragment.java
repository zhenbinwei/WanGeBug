package com.example.weizhenbin.wangebug.modules.code.uis;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeProjectListAdapter;
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController;
import com.example.weizhenbin.wangebug.modules.code.entity.ProjectListDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/28.
 */

public class ProjectListFragment extends BaseFragment {

    private SwipeRefreshLayout srlRefresh;
    private RecyclerView rvDataList;
    private CodeProjectListAdapter listAdapter;
    int page=1;

    List<ProjectListDataBean.DataBean.DatasBean> contentlistBeen=new ArrayList<>();

    private String id;
    private String name;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            id=savedInstanceState.getString("id");
            name=savedInstanceState.getString("name");
        }
    }

   @Override
   protected int getContentViewLayoutId() {
       return R.layout.fm_project_list;
   }

    @Override
    protected void initFragment(View view) {
        initViews(view);
        initData();
        initEvent();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("id",id);
        outState.putString("name",name);
        super.onSaveInstanceState(outState);
    }

    private void setId(String id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    public static ProjectListFragment getFragment(String id, String name){
        ProjectListFragment projectListFragment=new ProjectListFragment();
        projectListFragment.setId(id);
        projectListFragment.setName(name);
        return projectListFragment;
    }


    private void initData() {
        listAdapter=new CodeProjectListAdapter(getContext(),contentlistBeen);
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
        if (TextUtils.isEmpty(id)){
            return;
        }
        CodeController.getProjectListData(page,id,new DataResultAdapter<ProjectListDataBean>(){
            @Override
            public void onStart() {
                super.onStart();
                if (page==1) {
                    srlRefresh.setRefreshing(true);
                }
            }

            @Override
            public void onSuccess(ProjectListDataBean projectListDataBean) {
                super.onSuccess(projectListDataBean);
                srlRefresh.setRefreshing(false);
                if (projectListDataBean!=null&&projectListDataBean.getErrorCode()==0){
                    if (projectListDataBean.getData()!=null&&projectListDataBean.getData().getDatas()!=null){
                        if (page==1){
                            contentlistBeen.clear();
                        }
                        contentlistBeen.addAll(projectListDataBean.getData().getDatas());
                        if(page==1){
                            listAdapter.setNewData(contentlistBeen);
                        }else {
                            listAdapter.notifyDataSetChanged();
                        }
                        if (projectListDataBean.getData().isOver()){
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
        srlRefresh =  view.findViewById(R.id.srl_refresh);
        rvDataList = view.findViewById(R.id.rv_data_list);
    }



    @Override
    protected void loadData() {
        if (contentlistBeen.isEmpty()) {
            getData();
        }
    }

    @Override
    protected String getPageTitle() {
        return name;
    }
}
