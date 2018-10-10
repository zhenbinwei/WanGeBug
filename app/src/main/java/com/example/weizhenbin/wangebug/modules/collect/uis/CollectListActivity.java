package com.example.weizhenbin.wangebug.modules.collect.uis;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.modules.collect.adapters.CollectListAdapter;
import com.example.weizhenbin.wangebug.modules.collect.controllers.CollectController;
import com.example.weizhenbin.wangebug.modules.collect.entity.TBCollectBean;
import com.example.weizhenbin.wangebug.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by weizhenbin on 2018/10/10.
 */
public class CollectListActivity extends BaseActivity {
    private SwipeRefreshLayout srlRefresh;
    private RecyclerView rvDataList;
    private CollectListAdapter listAdapter;
    private List<TBCollectBean> beanList=new ArrayList<>();
    private int page=1;
    private int pageCount=15;
    private TitleBar tbTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initViews();
        initData();
        initEvent();
        getData();
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
        tbTitle.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        Observable.create(new Observable.OnSubscribe< List<TBCollectBean>>() {
            @Override
            public void call(Subscriber<? super  List<TBCollectBean>> subscriber) {
                List<TBCollectBean> beanList= CollectController.getCollectList(page,pageCount);
                subscriber.onNext(beanList);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer< List<TBCollectBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        srlRefresh.setRefreshing(false);
                        if (page==1){
                            listAdapter.emptyData();
                        }else {
                            listAdapter.loadMoreFail();
                        }
                    }

                    @Override
                    public void onNext( List<TBCollectBean> collectBeans) {
                        srlRefresh.setRefreshing(false);
                        if (collectBeans!=null){
                            if (page==1){
                                beanList.clear();
                            }
                            beanList.addAll(collectBeans);
                            if(page==1){
                                listAdapter.setNewData(beanList);
                            }else {
                                listAdapter.notifyDataSetChanged();
                            }
                            if (collectBeans.isEmpty()){
                                if (page==1){
                                    listAdapter.emptyData(false);
                                }else {
                                    listAdapter.loadMoreComplete();
                                }
                            }else if (collectBeans.size()<pageCount){
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
                });
    }

    private void initData() {

        listAdapter=new CollectListAdapter(this,beanList);
        rvDataList.setAdapter(listAdapter);
        rvDataList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        Drawable drawable=getResources().getDrawable(R.drawable.divider_line);
        itemDecoration.setDrawable(drawable);
        rvDataList.addItemDecoration(itemDecoration);
        listAdapter.bindToRecyclerView(rvDataList);
        listAdapter.disableLoadMoreIfNotFullPage();
    }

    private void initViews() {
        srlRefresh =  findViewById(R.id.srl_refresh);
        rvDataList = findViewById(R.id.rv_data_list);
        tbTitle=findViewById(R.id.tb_title);
    }


    public static void startActivity(Context context){
        context.startActivity(new Intent(context,CollectListActivity.class));
    }

}
