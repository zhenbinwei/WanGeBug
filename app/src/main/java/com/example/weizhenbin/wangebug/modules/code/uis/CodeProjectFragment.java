package com.example.weizhenbin.wangebug.modules.code.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController;
import com.example.weizhenbin.wangebug.modules.code.entity.ProjectTreeDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/24.
 */

public class CodeProjectFragment extends BaseFragment{

    ViewPager vpProject;
    TabLayout tlProjectType;
    ViewPageAdapter pageAdapter=null;
    List<BaseFragment> fragments=new ArrayList<>();
    List<ProjectTreeDataBean.DataBean> dataBeanList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_code_project,null);
        initViews(view);
        initEvent();
        setData();
        return view;
    }


   @Override
   protected void loadData() {
       if (dataBeanList==null||dataBeanList.isEmpty()) {
           getProjectTreeData();
       }
   }

    private void getProjectTreeData() {
        CodeController.getProjectTreeData(new DataResultAdapter<ProjectTreeDataBean>(){
            @Override
            public void onSuccess(ProjectTreeDataBean projectTreeDataBean) {
                super.onSuccess(projectTreeDataBean);
                if (projectTreeDataBean!=null&&projectTreeDataBean.getErrorCode()==0){
                    if (projectTreeDataBean.getData()!=null&&projectTreeDataBean.getData()!=null){
                        dataBeanList=projectTreeDataBean.getData();
                        int size=dataBeanList.size();
                        for (int i = 0; i < size; i++) {
                            ProjectTreeDataBean.DataBean bean=dataBeanList.get(i);
                            fragments.add(ProjectListFragment.getFragment(bean.getId()+"",bean.getName()));
                        }
                        pageAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void setData() {
        pageAdapter=  new ViewPageAdapter(getChildFragmentManager(),fragments);
        vpProject.setAdapter(pageAdapter);
    }

    private void initEvent() {

    }

    private void initViews(View view) {
        vpProject=view.findViewById(R.id.vp_project);
        tlProjectType=view.findViewById(R.id.tl_project_type);
    }



    @Override
    protected String getPageTitle() {
        return "首页";
    }

    public static CodeProjectFragment getFragment(){
        return new CodeProjectFragment();
    }
}
