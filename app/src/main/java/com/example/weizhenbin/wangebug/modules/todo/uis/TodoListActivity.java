package com.example.weizhenbin.wangebug.modules.todo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 18/9/8.
 */

public class TodoListActivity extends BaseActivity {
    ViewPageAdapter pageAdapter=null;
    List<BaseFragment> fragments=new ArrayList<>();
     ViewPager vpTodo;
     TabLayout tlTodoStatus;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        initViews();
        if (fragments.isEmpty()) {
            addFragments();
        }
        setData();
    }

    private void setData() {
        pageAdapter=  new ViewPageAdapter(getSupportFragmentManager(),fragments);
        vpTodo.setAdapter(pageAdapter);
    }

    private void addFragments() {
        fragments.add(TodoListFragment.getFragment(-1));
        fragments.add(TodoListFragment.getFragment(0));
        fragments.add(TodoListFragment.getFragment(1));
    }

    private void initViews() {
        vpTodo =  findViewById(R.id.vp_todo);
        tlTodoStatus = findViewById(R.id.tl_todo_status);
    }

    public static void startActivity(Context context){
        context.startActivity(new Intent(context,TodoListActivity.class));
    }
}
