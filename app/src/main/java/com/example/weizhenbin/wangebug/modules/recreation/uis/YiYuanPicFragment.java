package com.example.weizhenbin.wangebug.modules.recreation.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.modules.recreation.adapters.YiYuanDataListAdapter;
import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanPicBean;
import com.example.weizhenbin.wangebug.net.retrofit.HttpHelper;
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.YiYuanApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observer;

/**
 * Created by weizhenbin on 2018/8/17.
 */

public class YiYuanPicFragment extends BaseFragment {

    SwipeRefreshLayout srlRefresh;
    RecyclerView rvDataList;
    YiYuanDataListAdapter listAdapter;

    int page=1;

    List<YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean>  contentlistBeen=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_yiyuan_pic,null);
        initViews(view);
        initData();
        return view;
    }

    private void initData() {
        listAdapter=new YiYuanDataListAdapter(getContext(),R.layout.yiyuan_pic_item_layout,contentlistBeen);
        rvDataList.setAdapter(listAdapter);
        rvDataList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        getData();
    }

    private void getData(){
        HashMap<String,String> hashMap=new HashMap();
        hashMap.put("showapi_appid", YiYuanApi.clienId);
        hashMap.put("showapi_sign",YiYuanApi.sign);
        hashMap.put("type","10");
        hashMap.put("page",page+"");
        HttpHelper.getHttpHelper().getApi(YiYuanApi.class).getData(hashMap).
                compose(HttpHelper.<YiYuanPicBean>setThread()).
                subscribe(new Observer<YiYuanPicBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(YiYuanPicBean s) {
                        Log.d("NewsFragment", s.getShowapi_res_body().toString());


                        contentlistBeen.addAll(s.getShowapi_res_body().getPagebean().getContentlist());
                        listAdapter.notifyDataSetChanged();

                    }
                });
    }


    private void initViews(View view) {
        srlRefresh=view.findViewById(R.id.srl_refresh);
        rvDataList=view.findViewById(R.id.rv_data_list);

    }

    @Override
    public String getPageTitle() {
        return "图片";
    }
}
