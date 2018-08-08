package com.example.weizhenbin.wangebug.fragments;

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
import com.example.weizhenbin.wangebug.net.HttpHelper;
import com.example.weizhenbin.wangebug.net.impl.HttpResult;
import com.example.weizhenbin.wangebug.views.TitleBar;

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
                HttpHelper.getHttpHelper().get("http://www.wanandroid.com/article/list/1/json/", null, new HttpResult<String>() {


                    @Override
                    public void onFail(Throwable e) {

                    }

                    @Override
                    public void onSuccess(String translation) {
                        Log.d("NewsFragment", translation);
                    }
                });
            }
        });
    }
    public static class Translation {

        private int status;
        private content content;
        private static class content{
            private String from1;
            private String to;
            private String vendor;
            private String out;
            private int errNo;
        }

        public void show() {
            System.out.println(status);
            System.out.println(content.from1);
            System.out.println(content.to);
            System.out.println(content.vendor);
            System.out.println(content.out);
            System.out.println(content.errNo);
        }

    }



}
