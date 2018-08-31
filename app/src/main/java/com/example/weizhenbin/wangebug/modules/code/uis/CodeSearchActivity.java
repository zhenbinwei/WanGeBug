package com.example.weizhenbin.wangebug.modules.code.uis;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeArticleListAdapter;
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeSearchHotKeyFlowAdapter;
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController;
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean;
import com.example.weizhenbin.wangebug.modules.code.entity.SearchHotKeyDataBean;
import com.example.weizhenbin.wangebug.tools.SoftKeyboardTool;
import com.example.weizhenbin.wangebug.views.TitleBar;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/31.
 */

public class CodeSearchActivity extends BaseActivity {
    TitleBar titleBar;
    EditText editText;

    SwipeRefreshLayout srlRefresh;
    RecyclerView rvDataList;
    CodeArticleListAdapter listAdapter;
    int page=0;
    List<ArticleListDataBean.DataBean.DatasBean> datasBeen=new ArrayList<>();
    String key;
    TagFlowLayout tflHotKey;
    TextView tvClean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_search);
        initViews();
        initData();
        initEvent();
        getSearchHotKeyData();
    }

    private void getSearchHotKeyData() {
        CodeController.getSearchHotKeyData(new DataResultAdapter<SearchHotKeyDataBean>(){
            @Override
            public void onSuccess(SearchHotKeyDataBean searchHotKeyDataBean) {
                super.onSuccess(searchHotKeyDataBean);
                if (searchHotKeyDataBean!=null&&searchHotKeyDataBean.getData()!=null){
                    if (!searchHotKeyDataBean.getData().isEmpty()){
                        tflHotKey.setAdapter(new CodeSearchHotKeyFlowAdapter(searchHotKeyDataBean.getData()));
                    }
                }
            }
        });
    }

    private void initData() {
        listAdapter=new CodeArticleListAdapter(this,datasBeen);
        rvDataList.setAdapter(listAdapter);
        rvDataList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        Drawable drawable=getResources().getDrawable(R.drawable.divider_line);
        itemDecoration.setDrawable(drawable);
        rvDataList.addItemDecoration(itemDecoration);
        listAdapter.bindToRecyclerView(rvDataList);
        listAdapter.disableLoadMoreIfNotFullPage();
    }

    private void getData(String key) {
        CodeController.getSearchData(page,key,new DataResultAdapter<ArticleListDataBean>(){
            @Override
            public void onStart() {
                super.onStart();
                if (page==0){
                    srlRefresh.setRefreshing(true);
                }
            }

            @Override
            public void onSuccess(ArticleListDataBean articleListDataBean) {
                super.onSuccess(articleListDataBean);
                srlRefresh.setRefreshing(false);
                if (articleListDataBean!=null&&articleListDataBean.getErrorCode()==0){
                    if (articleListDataBean.getData()!=null&&articleListDataBean.getData().getDatas()!=null){
                        if (page==0){
                            datasBeen.clear();
                        }
                        datasBeen.addAll(articleListDataBean.getData().getDatas());
                        if(page==0){
                            listAdapter.setNewData(datasBeen);
                        }else {
                            listAdapter.notifyDataSetChanged();
                        }
                        if (articleListDataBean.getData().isOver()){
                            listAdapter.loadMoreEnd();
                        }else {
                            listAdapter.loadMoreComplete();
                            page++;
                        }
                    }else {
                        listAdapter.loadMoreEnd();
                    }
                }
            }
        });
    }
    private void initEvent() {
        titleBar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               search();
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    search();
                }
                return false;
            }
        });
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                search();
            }
        });
        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData(key);
            }
        },rvDataList);
        tvClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void search() {
        SoftKeyboardTool.hideSoftKeyboard(CodeSearchActivity.this);
        page=0;
        key=editText.getText().toString();
        if (TextUtils.isEmpty(key.trim())){
            //Snackbar.make()
            srlRefresh.setRefreshing(false);
            return;
        }
        getData(key);
    }

    private void initViews() {
        titleBar=findViewById(R.id.tb_title);
        editText=getEditTextView();
        titleBar.addCenterView(editText);
        srlRefresh=findViewById(R.id.srl_refresh);
        rvDataList=findViewById(R.id.rv_data_list);
        tflHotKey=findViewById(R.id.tfl_hot_key);
        tvClean=findViewById(R.id.tv_clean);
    }


    private EditText getEditTextView() {
       return   (EditText) LayoutInflater.from(this).inflate(R.layout.code_search_edit_layout,titleBar,false);
    }

    public static void startActivity(Context context){

        context.startActivity(new Intent(context,CodeSearchActivity.class));

    }



}
