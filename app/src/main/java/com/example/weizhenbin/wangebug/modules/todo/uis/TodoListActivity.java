package com.example.weizhenbin.wangebug.modules.todo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.ViewPageAdapter;
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler;
import com.example.weizhenbin.wangebug.eventbus.EventCode;
import com.example.weizhenbin.wangebug.eventbus.MessageEvent;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean;
import com.example.weizhenbin.wangebug.views.TitleBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    AppCompatImageView ivAdd;
    TitleBar tbTitle;
    TodoListFragment all,noDone,done;
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
        EventBusHandler.register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.code== EventCode.ADD_TODO_CODE){
           if (event.msg instanceof TBTodoBean){
               all.addTodo((TBTodoBean) event.msg);
               noDone.addTodo((TBTodoBean) event.msg);
           }
        }else if (event.code==EventCode.DEL_TODO_CODE){
            if (event.msg instanceof TBTodoBean){
                all.delTodo((TBTodoBean) event.msg);
                noDone.delTodo((TBTodoBean) event.msg);
                done.delTodo((TBTodoBean) event.msg);
            }
        }else if (event.code==EventCode.UPDATE_TODO_CODE){
            if (event.msg instanceof TBTodoBean){
                all.updateTodo((TBTodoBean) event.msg);
                noDone.updateTodo((TBTodoBean) event.msg);
                done.updateTodo((TBTodoBean) event.msg);
            }
        }else if (event.code==EventCode.DONE_TODO_CODE){
            if (event.msg instanceof TBTodoBean){
                all.updateTodo((TBTodoBean) event.msg);
                noDone.delTodo((TBTodoBean) event.msg);
                done.addTodo((TBTodoBean) event.msg);
            }
        }
    }


    private void initEvent() {
        ivAdd.setOnClickListener(new View.OnClickListener() {
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
        fragments.add(all=TodoListFragment.getFragment(-1));
        fragments.add(noDone=TodoListFragment.getFragment(0));
        fragments.add(done=TodoListFragment.getFragment(1));
    }

    private void initViews() {
        vpTodo = findViewById(R.id.vp_todo);
        tlTodoStatus = findViewById(R.id.tl_todo_status);
        ivAdd = findViewById(R.id.iv_add);
        tbTitle=findViewById(R.id.tb_title);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, TodoListActivity.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHandler.unregister(this);
    }
}
