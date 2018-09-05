package com.example.weizhenbin.wangebug;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;

import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler;
import com.example.weizhenbin.wangebug.modules.code.uis.CodeFragment;
import com.example.weizhenbin.wangebug.modules.news.uis.NewsFragment;
import com.example.weizhenbin.wangebug.modules.recreation.uis.RecreationFragment;
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoEditActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,IOpenMenuHandler {

    private final static int CODE_MODULE=1;
    private final static int NEWS_MODULE=2;
    private final static int RECREATION_MODULE=3;

    int currentModule=CODE_MODULE;

    DrawerLayout drawer;
    FragmentManager fragmentManager;
    NewsFragment newsFragment;
    CodeFragment codeFragment;
    RecreationFragment recreationFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试
        drawer = findViewById(R.id.drawer_layout);
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            //将侧边栏顶部延伸至status bar
            drawer.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
            drawer.setClipToPadding(false);
        }
        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        fragmentManager=getSupportFragmentManager();
        if (savedInstanceState!=null){
            newsFragment= (NewsFragment) fragmentManager.findFragmentByTag(NewsFragment.class.getSimpleName());
            codeFragment= (CodeFragment) fragmentManager.findFragmentByTag(CodeFragment.class.getSimpleName());
            recreationFragment= (RecreationFragment) fragmentManager.findFragmentByTag(RecreationFragment.class.getSimpleName());
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
        hideAllFragment();
        if (savedInstanceState!=null){
            currentModule=savedInstanceState.getInt("currentModule");
            Log.d("MainActivity", "currentModule:" + currentModule);
            if (currentModule==RECREATION_MODULE){
                if (!recreationFragment.isAdded()) {
                    transaction.add(R.id.fl_content, recreationFragment, RecreationFragment.class.getSimpleName());
                }
                transaction.show(recreationFragment);
                transaction.commit();
            }else if (currentModule==NEWS_MODULE){
                if (!newsFragment.isAdded()) {
                    transaction.add(R.id.fl_content, newsFragment, NewsFragment.class.getSimpleName());
                }
                transaction.show(newsFragment);
                transaction.commit();
            }else {
                if (!codeFragment.isAdded()) {
                    transaction.add(R.id.fl_content, codeFragment, CodeFragment.class.getSimpleName());
                }
                transaction.show(codeFragment);
                transaction.commit();
            }

        }else {
            if (!codeFragment.isAdded()) {
                transaction.add(R.id.fl_content, codeFragment, CodeFragment.class.getSimpleName());
            }
            transaction.show(codeFragment);
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("MainActivity", "onSaveInstanceState-currentModule:" + currentModule);
        outState.putInt("currentModule",currentModule);
        super.onSaveInstanceState(outState);
    }

    private void hideAllFragment(){
        FragmentTransaction   transaction=fragmentManager.beginTransaction();
        if (codeFragment!=null&&codeFragment.isAdded()){
            transaction.hide(codeFragment);
        }
        if (newsFragment!=null&&newsFragment.isAdded()){
            transaction.hide(newsFragment);
        }
        if (recreationFragment!=null&&recreationFragment.isAdded()){
            transaction.hide(recreationFragment);
        }
        transaction.commit();
    }

    private void showFragment(Fragment fragment){
        if (fragment==null){
            return;
        }
        FragmentTransaction transaction=fragmentManager.beginTransaction();;
        hideAllFragment();
        if (!fragment.isAdded()) {
          transaction.add(R.id.fl_content,fragment,fragment.getClass().getSimpleName());
        }
        transaction.show(fragment);
        transaction.commit();
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
        int id=item.getItemId();
        FragmentTransaction transaction;
        switch (id){
            case R.id.nav_news:
                /*transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_content,newsFragment,"newsFragment");
                transaction.commit();*/
                showFragment(newsFragment);
                currentModule=NEWS_MODULE;
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_code:
               /* transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_content, codeFragment,"codeFragment");
                transaction.commit();*/
                showFragment(codeFragment);
                currentModule=CODE_MODULE;
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_recreation:
               /* transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_content,recreationFragment,"recreationFragment");
                transaction.commit();*/
                showFragment(recreationFragment);
                currentModule=RECREATION_MODULE;
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_todo:
                TodoEditActivity.startActivity(MainActivity.this);
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_about:
                break;
        }
        return true;
    }

    @Override
    public void openMenu() {
        drawer.openDrawer(GravityCompat.START);
    }
}
