package com.example.weizhenbin.wangebug.modules.recreation.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.modules.recreation.configs.JokeType;

/**
 * Created by weizhenbin on 18/8/12.
 */

public class JokeFragment extends BaseFragment {

    View view;
    JokeType jokeType;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fm_joke,null);
        return view;
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
