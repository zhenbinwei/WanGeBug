package com.example.weizhenbin.wangebug.modules.recreation.uis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.WindowManager;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.views.PhotoViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/20.
 */

public class PicBrowserActivity extends BaseActivity {

    PhotoViewPager vpBrowser;

    List<String> pics=new ArrayList<>();


    int index;



    public static void startBrowserActivity(Context context,ArrayList<String> pics,int index){
        Intent intent=new Intent(context,PicBrowserActivity.class);
        intent.putStringArrayListExtra("picList",pics);
        intent.putExtra("index",index);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.activity_open_in, R.anim.activity_close_in);
        }
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pic_browser);
        pics.addAll(getIntent().getStringArrayListExtra("picList"));
        index=getIntent().getIntExtra("index",0);
        vpBrowser=findViewById(R.id.vp_browser);
        vpBrowser.setAdapter(new BrowserViewPageAdapter(getSupportFragmentManager()));
        if(index>=0&&index<pics.size()){
            vpBrowser.setCurrentItem(index);
        }
    }



   private class BrowserViewPageAdapter extends FragmentStatePagerAdapter{


        public BrowserViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PicBrowserFragment.getFragment(pics.get(position));
        }

        @Override
        public int getCount() {
            return pics.size();
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_open_out, R.anim.activity_close_out);
    }
}
