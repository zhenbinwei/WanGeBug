package com.example.weizhenbin.wangebug.modules.todo.uis;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.modules.todo.adapters.TodoListAdapter;
import com.example.weizhenbin.wangebug.modules.todo.controllers.TodoController;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 18/9/9.
 */

public class TodoListFragment extends BaseFragment {

    private int todoStatus;
    private SwipeRefreshLayout srlRefresh;
    private RecyclerView rvDataList;
    private TodoListAdapter listAdapter;
    private List<TBTodoBean> beanList=new ArrayList<>();
    private int page=1;
    private int pageCount=15;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            todoStatus=savedInstanceState.getInt("todoStatus");
        }
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fm_todo_list;
    }

    @Override
    protected void initFragment(View view) {
        initViews(view);
        initData();
        initEvent();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("todoStatus",todoStatus);
        super.onSaveInstanceState(outState);
    }

    private void initEvent() {
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

    private void getData() {
        TodoController.asynGetTodoList(todoStatus,page,pageCount,new DataResultAdapter<List<TBTodoBean>>(){
            @Override
            public void onSuccess(List<TBTodoBean> tbTodoBeans) {
                super.onSuccess(tbTodoBeans);
                srlRefresh.setRefreshing(false);
                if (tbTodoBeans!=null){
                    if (page==1){
                        beanList.clear();
                    }
                    beanList.addAll(tbTodoBeans);
                    if(page==1){
                        listAdapter.setNewData(beanList);
                    }else {
                        listAdapter.notifyDataSetChanged();
                    }
                    if (tbTodoBeans.isEmpty()){
                        if (page==1){
                            listAdapter.emptyData(false);
                        }else {
                            listAdapter.loadMoreComplete();
                        }
                    }else if (tbTodoBeans.size()<pageCount){
                        listAdapter.loadMoreEnd();
                    }else {
                        listAdapter.loadMoreComplete();
                        page++;
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
                listAdapter.loadMoreComplete();
                if (page==1){
                    listAdapter.emptyData();
                }else {
                    listAdapter.loadMoreFail();
                }
            }
        });
    }

    private void initViews(View view) {
        srlRefresh =  view.findViewById(R.id.srl_refresh);
        rvDataList = view.findViewById(R.id.rv_data_list);
    }
    private void initData() {
        if (getContext()==null){
            return;
        }
        listAdapter=new TodoListAdapter(getContext(),beanList,todoStatus);
        rvDataList.setAdapter(listAdapter);
        rvDataList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration itemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable=getResources().getDrawable(R.drawable.divider_line);
        itemDecoration.setDrawable(drawable);
        rvDataList.addItemDecoration(itemDecoration);
        listAdapter.bindToRecyclerView(rvDataList);
        listAdapter.disableLoadMoreIfNotFullPage();
    }

    private void setTodoStatus(int todoStatus) {
        this.todoStatus = todoStatus;
    }

    public static TodoListFragment getFragment(int todoStatus){
        TodoListFragment todoListFragment=new TodoListFragment();
        todoListFragment.setTodoStatus(todoStatus);
        return todoListFragment;
    }

    @Override
    protected String getPageTitle() {
      if (todoStatus==0){
            return "未完成";
        }else if (todoStatus==1){
            return "已完成";
        }else {
            return "全部";
        }
    }

    @Override
    protected void loadData() {
        if (beanList.isEmpty()) {
            getData();
        }
    }


    public void addTodo(TBTodoBean tbTodoBean){
        if (listAdapter==null){
            return;
        }
        if (isContain(tbTodoBean)){
            return;
        }
        beanList.add(0,tbTodoBean);
        listAdapter.notifyItemInserted(0);
    }
    public void delTodo(TBTodoBean tbTodoBean){
        if (listAdapter==null){
            return;
        }
        if (!isContain(tbTodoBean)){
            return;
        }
        int size=beanList.size();
        for (int i = 0; i < size; i++) {
            if (tbTodoBean.getUuid().equals(beanList.get(i).getUuid())){
                beanList.remove(i);
                listAdapter.notifyItemRemoved(i);
                break;
            }
        }
        if (beanList.isEmpty()){
            listAdapter.emptyData(false);
        }
    }

    public void updateTodo(TBTodoBean tbTodoBean){
        if (listAdapter==null){
            return;
        }
        if (!isContain(tbTodoBean)){
            return;
        }
        int size=beanList.size();
        for (int i = 0; i < size; i++) {
            if (tbTodoBean.getUuid().equals(beanList.get(i).getUuid())){
                beanList.remove(i);
                beanList.add(i,tbTodoBean);
                listAdapter.notifyItemChanged(i);
                break;
            }
        }
    }



    private boolean isContain(TBTodoBean tbTodoBean){

        if (tbTodoBean==null){
            return false;
        }

        int size=beanList.size();

        for (int i = 0; i < size; i++) {
            if (tbTodoBean.getUuid().equals(beanList.get(i).getUuid())){
                return true;
            }
        }

        return false;
    }


}
