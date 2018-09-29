package com.example.weizhenbin.wangebug.modules.code.uis;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController;
import com.example.weizhenbin.wangebug.modules.code.entity.ProjectTreeDataBean;
import com.example.weizhenbin.wangebug.views.emptyview.EmptyView;

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
    EmptyView evEmpty;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fm_code_project;
    }

    @Override
    protected void initFragment(View view) {
        initViews(view);
        initEvent();
        setData();
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
            public void onStart() {
                super.onStart();
                evEmpty.setVisibility(View.VISIBLE);
                evEmpty.loading(true);
            }

            @Override
            public void onSuccess(ProjectTreeDataBean projectTreeDataBean) {
                super.onSuccess(projectTreeDataBean);
                if (projectTreeDataBean!=null&&projectTreeDataBean.getErrorCode()==0){
                    evEmpty.setVisibility(View.GONE);
                    if (projectTreeDataBean.getData()!=null&&projectTreeDataBean.getData()!=null){
                        dataBeanList=projectTreeDataBean.getData();
                        int size=dataBeanList.size();
                        for (int i = 0; i < size; i++) {
                            ProjectTreeDataBean.DataBean bean=dataBeanList.get(i);
                            fragments.add(ProjectListFragment.getFragment(bean.getId()+"",bean.getName()));
                        }
                        pageAdapter.notifyDataSetChanged();
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
        vpProject.setAdapter(pageAdapter);
    }

    private void initEvent() {
         evEmpty.setAction(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getProjectTreeData();
             }
         });
    }

    private void initViews(View view) {
        vpProject=view.findViewById(R.id.vp_project);
        tlProjectType=view.findViewById(R.id.tl_project_type);
        evEmpty=view.findViewById(R.id.ev_empty);
        evEmpty.setProgressBarVisibility(View.VISIBLE);
    }



    @Override
    protected String getPageTitle() {
        return "首页";
    }

    public static CodeProjectFragment getFragment(){
        return new CodeProjectFragment();
    }
}
