package com.example.weizhenbin.wangebug.modules.code.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;
import com.example.weizhenbin.wangebug.modules.code.entity.SystemTreeDataBean;
import com.example.weizhenbin.wangebug.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/30.
 */

public class CodeSystemTreeActivity extends BaseActivity {

    ViewPager vpSystemTree;
    TabLayout tlSystemTreeType;
    TitleBar titleBar;
    ViewPageAdapter pageAdapter=null;
    List<BaseFragment> fragments=new ArrayList<>();
    SystemTreeDataBean.DataBean dataBean;
    int index;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_tree);
        initViews();
        initEvent();
        setViews();
        setData();
    }

    private void setData() {
        Intent intent=getIntent();
        dataBean= (SystemTreeDataBean.DataBean) intent.getSerializableExtra("dataBean");
        index=intent.getIntExtra("index",0);
        if (dataBean!=null){
            if (dataBean.getChildren()!=null){
                int size=dataBean.getChildren().size();
                for (int i = 0; i < size; i++) {
                    SystemTreeDataBean.DataBean.ChildrenBean bean=dataBean.getChildren().get(i);
                    fragments.add(CodeSystemTreeChildrenFragment.getFragment(bean.getId(),bean.getName()));
                }
                pageAdapter.notifyDataSetChanged();
            }
            vpSystemTree.setCurrentItem(index,false);
            titleBar.setTitle(dataBean.getName());
        }
    }

    private void setViews() {
        pageAdapter=  new ViewPageAdapter(getSupportFragmentManager(),fragments);
        vpSystemTree.setAdapter(pageAdapter);
    }

    private void initEvent() {
        titleBar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        vpSystemTree=findViewById(R.id.vp_system_tree);
        tlSystemTreeType=findViewById(R.id.tl_system_tree_type);
        titleBar=findViewById(R.id.tb_title);
    }


    public static void startActivity(Context context,SystemTreeDataBean.DataBean dataBean,int index){
        Intent intent=new Intent(context,CodeSystemTreeActivity.class);
        intent.putExtra("dataBean",dataBean);
        intent.putExtra("index",index);
        context.startActivity(intent);
    }
}
