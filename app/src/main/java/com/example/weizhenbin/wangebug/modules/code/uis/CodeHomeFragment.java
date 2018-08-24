package com.example.weizhenbin.wangebug.modules.code.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;

/**
 * Created by weizhenbin on 2018/8/24.
 */

public class CodeHomeFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_code_home,null);
        return view;
    }

    @Override
    public String getPageTitle() {
        return "首页";
    }

    public static CodeHomeFragment getFragment(){
        return new CodeHomeFragment();
    }


}
