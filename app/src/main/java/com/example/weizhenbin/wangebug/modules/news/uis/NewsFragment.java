package com.example.weizhenbin.wangebug.modules.news.uis;

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
import com.example.weizhenbin.wangebug.net.retrofit.HttpHelper;
import com.example.weizhenbin.wangebug.net.retrofit.apiservice.CodeApi;
import com.example.weizhenbin.wangebug.views.TitleBar;

import rx.Observer;

/**
 * Created by weizhenbin on 2018/8/6.
 */

public class NewsFragment extends Fragment {
    TitleBar tbNews;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view=inflater.inflate(R.layout.fm_news,null);
        tbNews=view.findViewById(R.id.tb_news);
        initEvent();
        return view;
    }

    private void initEvent() {
        tbNews.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof IOpenMenuHandler){
                    ((IOpenMenuHandler) getActivity()).openMenu();
                }
                HttpHelper.getHttpHelper()
                        .getApi(CodeApi.class)
                        .getArticleList("1")
                        .compose(HttpHelper.<String>setThread())
                        .subscribe(new Observer<String>() {//订阅
                            @Override
                            public void onCompleted() {
                                //所有事件都完成，可以做些操作。。。
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace(); //请求过程中发生错误
                            }

                            @Override
                            public void onNext(String book) {//这里的book就是我们请求接口返回的实体类
                                Log.d("NewsFragment", book);
                            }
                        });
            }
        });
    }

}
