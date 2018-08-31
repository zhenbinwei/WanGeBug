package com.example.weizhenbin.wangebug;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.modules.code.uis.CodeFragment;
import com.example.weizhenbin.wangebug.modules.code.uis.CodeSearchActivity;
import com.example.weizhenbin.wangebug.modules.news.uis.NewsFragment;
import com.example.weizhenbin.wangebug.modules.recreation.uis.RecreationFragment;
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler;
import com.example.weizhenbin.wangebug.views.TitleBar;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,IOpenMenuHandler {
    DrawerLayout drawer;
    FragmentManager fragmentManager;
    NewsFragment newsFragment;
    CodeFragment codeFragment;
    RecreationFragment recreationFragment;
    TitleBar titleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试
        drawer = findViewById(R.id.drawer_layout);
        titleBar=findViewById(R.id.tb_title);
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            //将侧边栏顶部延伸至status bar
            drawer.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
            drawer.setClipToPadding(false);
        }
        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initFragment(savedInstanceState);

        titleBar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        titleBar.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeSearchActivity.startActivity(MainActivity.this);
            }
        });
    }

    private void initFragment(Bundle savedInstanceState) {
        fragmentManager=getSupportFragmentManager();
        if (savedInstanceState!=null){
            newsFragment= (NewsFragment) fragmentManager.findFragmentByTag("newsFragment");
            codeFragment= (CodeFragment) fragmentManager.findFragmentByTag("codeFragment");
            recreationFragment= (RecreationFragment) fragmentManager.findFragmentByTag("recreationFragment");
        }
        if(newsFragment==null){
            newsFragment=new NewsFragment();
        }
        if (codeFragment==null){
            codeFragment=new CodeFragment();
        }
        if(recreationFragment==null){
            recreationFragment =new RecreationFragment();
        }
        FragmentTransaction transaction;
        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content,codeFragment,"codeFragment");
        transaction.commit();
        titleBar.setTitle(getString(R.string.code_string));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        drawer.closeDrawer(GravityCompat.START);
        int id=item.getItemId();
        FragmentTransaction transaction;
        switch (id){
            case R.id.nav_news:
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_content,newsFragment,"newsFragment");
                transaction.commit();
                titleBar.setTitle(getString(R.string.news_string));
                break;
            case R.id.nav_code:
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_content, codeFragment,"codeFragment");
                transaction.commit();
                titleBar.setTitle(getString(R.string.code_string));
                break;
            case R.id.nav_recreation:
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_content,recreationFragment,"recreationFragment");
                transaction.commit();
                titleBar.setTitle(getString(R.string.recreation_string));
                break;
        }
        return true;
    }

    @Override
    public void openMenu() {
        drawer.openDrawer(GravityCompat.START);
    }
}
