package com.example.weizhenbin.wangebug.modules.recreation.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.modules.recreation.configs.JokeType;
import com.example.weizhenbin.wangebug.modules.recreation.controllers.JokeController;
import com.example.weizhenbin.wangebug.modules.recreation.entity.TextJokeBean;
import com.example.weizhenbin.wangebug.net.retrofit.entity.AliBean;

/**
 * Created by weizhenbin on 18/8/12.
 */

public class JokeFragment extends BaseFragment {

   private View view;
   private JokeType jokeType;
   private SwipeRefreshLayout srlRefresh;
   private RecyclerView rvDataList;

   private int page=1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fm_joke,null);
        initViews(view);
        initEvent();
        getData(page);
        return view;
    }

    protected void initViews(View view) {
        srlRefresh=view.findViewById(R.id.srl_refresh);
        rvDataList=view.findViewById(R.id.rv_data_list);
        srlRefresh.setRefreshing(true);
    }


    protected void getData(int page){
        JokeController.getTextJoke(page, new JokeController.DataResultAdapter<AliBean<TextJokeBean>>() {

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(AliBean<TextJokeBean> textJokeBeanAliBean) {

            }
        });
    }

    protected void initEvent() {
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }


    public void setJokeType(JokeType jokeType) {
        this.jokeType = jokeType;
    }

    public static JokeFragment getFragment(JokeType jokeType){
        JokeFragment jokeFragment=new JokeFragment();
        jokeFragment.setJokeType(jokeType);

        return jokeFragment;
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
