package com.example.weizhenbin.wangebug.modules.code.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler;
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListData;
import com.example.weizhenbin.wangebug.net.retrofit.entity.WanAndroidBean;
import com.example.weizhenbin.wangebug.net.retrofit.HttpHelper;
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.CodeApi;
import com.example.weizhenbin.wangebug.views.TitleBar;

import rx.Observer;

/**
 * Created by weizhenbin on 2018/8/6.
 */

public class CodeFragment extends Fragment {
    TitleBar tbCode;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_code,null);
        tbCode=view.findViewById(R.id.tb_code);
        initEvent();
        return view;
    }

    private void initEvent() {
        tbCode.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof IOpenMenuHandler){
                    ((IOpenMenuHandler) getActivity()).openMenu();
                }
                HttpHelper.getHttpHelper()
                        .getApi(CodeApi.class)
                        .getArticleList("1")
                        .compose(HttpHelper.<WanAndroidBean<ArticleListData>>setThread())
                        .subscribe(new Observer<WanAndroidBean<ArticleListData>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(WanAndroidBean<ArticleListData> articleListDataWanAndroidBean) {
                                Log.d("CodeFragment", "articleListDataWanAndroidBean.data:" + articleListDataWanAndroidBean.data);
                            }
                        });
            }
        });
    }
}
