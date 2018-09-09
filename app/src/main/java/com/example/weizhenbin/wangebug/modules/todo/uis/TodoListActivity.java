package com.example.weizhenbin.wangebug.modules.todo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;
import com.example.weizhenbin.wangebug.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 18/9/8.
 */

public class TodoListActivity extends BaseActivity {
    ViewPageAdapter pageAdapter = null;
    List<BaseFragment> fragments = new ArrayList<>();
    ViewPager vpTodo;
    TabLayout tlTodoStatus;
    FloatingActionButton fabAdd;
    TitleBar tbTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        initViews();
        if (fragments.isEmpty()) {
            addFragments();
        }
        setData();
        initEvent();
    }

    private void initEvent() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoEditActivity.startActivity(TodoListActivity.this);
            }
        });
        tbTitle.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setData() {
        pageAdapter = new ViewPageAdapter(getSupportFragmentManager(), fragments);
        vpTodo.setAdapter(pageAdapter);
    }

    private void addFragments() {
        fragments.add(TodoListFragment.getFragment(-1));
        fragments.add(TodoListFragment.getFragment(0));
        fragments.add(TodoListFragment.getFragment(1));
    }

    private void initViews() {
        vpTodo = findViewById(R.id.vp_todo);
        tlTodoStatus = findViewById(R.id.tl_todo_status);
        fabAdd = findViewById(R.id.fab_add);
        tbTitle=findViewById(R.id.tb_title);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, TodoListActivity.class));
    }
}
