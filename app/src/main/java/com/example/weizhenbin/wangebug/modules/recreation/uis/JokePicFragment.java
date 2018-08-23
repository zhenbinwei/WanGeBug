package com.example.weizhenbin.wangebug.modules.recreation.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.modules.recreation.adapters.JokeListAdapter;
import com.example.weizhenbin.wangebug.modules.recreation.configs.JokeType;
import com.example.weizhenbin.wangebug.modules.recreation.controllers.JokeController;
import com.example.weizhenbin.wangebug.modules.recreation.entity.JokeContentlistBaseBean;
import com.example.weizhenbin.wangebug.modules.recreation.entity.PicJokeBean;
import com.example.weizhenbin.wangebug.net.retrofit.entity.AliBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 18/8/12.
 */

public class JokePicFragment extends BaseFragment {

   private View view;
   private JokeType jokeType;
   private SwipeRefreshLayout srlRefresh;
   private RecyclerView rvDataList;

   private int page=1;


   private JokeListAdapter jokeListAdapter;
    private List<JokeContentlistBaseBean> jokeContentlistBaseBeen=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fm_joke,null);
        initViews(view);
        initEvent();
        getData();
        return view;
    }

    protected void initViews(View view) {
        srlRefresh=view.findViewById(R.id.srl_refresh);
        rvDataList=view.findViewById(R.id.rv_data_list);
        srlRefresh.setRefreshing(true);
        rvDataList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvDataList.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        jokeListAdapter=new JokeListAdapter(getContext(),jokeContentlistBaseBeen);
        jokeListAdapter.bindToRecyclerView(rvDataList);
        jokeListAdapter.disableLoadMoreIfNotFullPage();
    }


    protected void getData(){
        JokeController.getPicJoke(page, new DataResultAdapter<AliBean<PicJokeBean>>() {

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(AliBean<PicJokeBean> picJokeBeanAliBean) {
                srlRefresh.setRefreshing(false);
                 if (picJokeBeanAliBean!=null&&picJokeBeanAliBean.showapi_res_code==0){
                     if (picJokeBeanAliBean.showapi_res_body!=null){
                         if (page==1){
                             jokeContentlistBaseBeen.clear();
                         }
                         jokeContentlistBaseBeen.addAll(picJokeBeanAliBean.showapi_res_body.getContentlist());
                     }

                     if(page==1){
                         jokeListAdapter.setNewData(jokeContentlistBaseBeen);
                     }else {
                         jokeListAdapter.notifyDataSetChanged();
                     }
                         jokeListAdapter.loadMoreComplete();
                     page++;

                 }
            }
        });
    }

    protected void initEvent() {
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                getData();
            }
        });
        jokeListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData();
            }
        },rvDataList);
    }


    public void setJokeType(JokeType jokeType) {
        this.jokeType = jokeType;
    }

    public static JokePicFragment getFragment(JokeType jokeType){
        JokePicFragment jokePicFragment =new JokePicFragment();
        jokePicFragment.setJokeType(jokeType);

        return jokePicFragment;
    }

    @Override
    public String getPageTitle() {
        String title = null;
         if (jokeType==JokeType.GIF){
             title="精彩动图";
         }else if (jokeType==JokeType.PIC){
             title="搞笑图片";
         }else {
             title="欢乐一下";
         }
        return title;
    }
}
