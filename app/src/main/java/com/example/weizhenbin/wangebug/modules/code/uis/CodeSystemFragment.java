package com.example.weizhenbin.wangebug.modules.code.uis;

import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeSystemTreeAdapter;
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController;
import com.example.weizhenbin.wangebug.modules.code.entity.SystemTreeDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/24.
 */

public class CodeSystemFragment extends BaseFragment{


    SwipeRefreshLayout srlRefresh;
    RecyclerView rvDataList;
    CodeSystemTreeAdapter listAdapter;
    List<SystemTreeDataBean.DataBean> datasBeen=new ArrayList<>();




    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fm_code_system;
    }

    @Override
    protected void initFragment(View view) {
        initViews(view);
        initData();
        initEvent();
    }

    private void getData() {
        CodeController.getSystemTreeData(new DataResultAdapter<SystemTreeDataBean>(){
            @Override
            public void onStart() {
                super.onStart();
                    srlRefresh.setRefreshing(true);
            }

            @Override
            public void onSuccess(SystemTreeDataBean systemTreeDataBean) {
                super.onSuccess(systemTreeDataBean);
                srlRefresh.setRefreshing(false);
                if (systemTreeDataBean!=null&&systemTreeDataBean.getErrorCode()==0){
                    if (systemTreeDataBean.getData()!=null){
                        datasBeen.clear();
                        datasBeen.addAll(systemTreeDataBean.getData());
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void initEvent() {
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    private void initData() {
        if (getContext()==null){
            return;
        }
        listAdapter=new CodeSystemTreeAdapter(datasBeen);
        rvDataList.setAdapter(listAdapter);
        rvDataList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration itemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable=getResources().getDrawable(R.drawable.divider_line);
        itemDecoration.setDrawable(drawable);
        rvDataList.addItemDecoration(itemDecoration);
    }

    private void initViews(View view) {
        srlRefresh=view.findViewById(R.id.srl_refresh);
        rvDataList=view.findViewById(R.id.rv_data_list);
    }


    @Override
    protected void loadData() {
        if (datasBeen.isEmpty()) {
            getData();
        }
    }

    @Override
    protected String getPageTitle() {
        return "体系";
    }

    public static CodeSystemFragment getFragment(){
        return new CodeSystemFragment();
    }


}
