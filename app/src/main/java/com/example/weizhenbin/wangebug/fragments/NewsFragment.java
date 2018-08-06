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
import com.example.weizhenbin.wangebug.views.TitleBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static android.content.ContentValues.TAG;

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
                request();
            }
        });
    }
    public static class Translation {

        private String status;
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
    public interface GetRequestInterface {

        // 注解里传入 网络请求 的部分URL地址
        // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
        // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
        // getCall()是接受网络请求数据的方法

        @GET("ajax.php?a=fy&f=auto&t=auto&w=你好")
        Call<Translation> getCall();
    }
    // 使用Retrofit封装的方法
    private void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") //http://fy.iciba.com/
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequestInterface request = retrofit.create(GetRequestInterface.class);
        Call<Translation> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                Log.e(TAG, "onResponse: "+response.body() );

                Translation translation = response.body();
                translation.show();
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {

            }
        });
    }

}
