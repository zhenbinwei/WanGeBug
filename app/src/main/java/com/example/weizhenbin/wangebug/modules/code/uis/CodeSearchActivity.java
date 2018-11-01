package com.example.weizhenbin.wangebug.modules.code.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.base.DataResultAdapter;
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeArticleListAdapter;
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeSearchHistoryAdapter;
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeSearchHotKeyFlowAdapter;
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController;
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean;
import com.example.weizhenbin.wangebug.modules.code.entity.SearchHotKeyDataBean;
import com.example.weizhenbin.wangebug.modules.code.entity.TBSearchHistoryKeyBean;
import com.example.weizhenbin.wangebug.tools.SoftKeyboardTool;
import com.example.weizhenbin.wangebug.views.LinearRecyclerView;
import com.example.weizhenbin.wangebug.views.TitleBar;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/31.
 */

public class CodeSearchActivity extends BaseActivity {
    TitleBar titleBar;
    LinearLayout llAssist;
    SwipeRefreshLayout srlRefresh;
    LinearRecyclerView rvDataList,rvHistoryDataList;
    CodeArticleListAdapter listAdapter;
    CodeSearchHistoryAdapter searchHistoryAdapter;
    int page=0;
    List<ArticleListDataBean.DataBean.DatasBean> datasBeen=new ArrayList<>();
    List<SearchHotKeyDataBean.DataBean> hotkeyDatas;
    List<TBSearchHistoryKeyBean> historyKeyBeans=new ArrayList<>();
    String key;
    TagFlowLayout tflHotKey;
    TextView tvClean;


    View titleBarCenter;
    ImageView ivClean;
    EditText editText;
    LinearLayout llHotKey,llHistoryKey;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_search);
        initViews();
        initData();
        initEvent();
        getSearchHotKeyData();
        getSearchHistoryData();
    }


    private void getSearchHotKeyData() {
        CodeController.getSearchHotKeyData(new DataResultAdapter<SearchHotKeyDataBean>(){
            @Override
            public void onSuccess(SearchHotKeyDataBean searchHotKeyDataBean) {
                super.onSuccess(searchHotKeyDataBean);
                if (searchHotKeyDataBean!=null&&searchHotKeyDataBean.getData()!=null){
                    if (!searchHotKeyDataBean.getData().isEmpty()){
                        llHotKey.setVisibility(View.VISIBLE);
                        hotkeyDatas=searchHotKeyDataBean.getData();
                        tflHotKey.setAdapter(new CodeSearchHotKeyFlowAdapter(hotkeyDatas));
                    }else {
                        llHotKey.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void getSearchHistoryData(){
        CodeController.getSearchHistoryData(new DataResultAdapter<List<TBSearchHistoryKeyBean>>(){
            @Override
            public void onSuccess(List<TBSearchHistoryKeyBean> tbSearchHistoryKeyBeans) {
                super.onSuccess(tbSearchHistoryKeyBeans);
                if (tbSearchHistoryKeyBeans!=null&&!tbSearchHistoryKeyBeans.isEmpty()){
                    historyKeyBeans.clear();
                    llHistoryKey.setVisibility(View.VISIBLE);
                    historyKeyBeans.addAll(tbSearchHistoryKeyBeans);
                    searchHistoryAdapter.notifyDataSetChanged();
                }else {
                    llHistoryKey.setVisibility(View.GONE);
                }
            }
        });


    }
    private void showAssistLayout(){
      llAssist.setVisibility(View.VISIBLE);
      getSearchHistoryData();
    }
    private void hideAssistLayout(){
        llAssist.setVisibility(View.GONE);
    }

    private void initData() {
        listAdapter=new CodeArticleListAdapter(this,datasBeen);
        rvDataList.setAdapter(listAdapter);
        listAdapter.bindToRecyclerView(rvDataList);
        listAdapter.disableLoadMoreIfNotFullPage();
        listAdapter.emptyData(false);
        listAdapter.setEmptyText(getString(R.string.input_key_string));

        searchHistoryAdapter=new CodeSearchHistoryAdapter(this,historyKeyBeans);
        rvHistoryDataList.setAdapter(searchHistoryAdapter);
    }

    private void getData(String key) {
        CodeController.getSearchData(page,key,new DataResultAdapter<ArticleListDataBean>(){
            @Override
            public void onStart() {
                super.onStart();
                if (page==0&&datasBeen.isEmpty()){
                    listAdapter.loading(true);
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
                        listAdapter.emptyData();
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
                    return true;
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
                 CodeController.cleanSearchHistoryKey();
                 llHistoryKey.setVisibility(View.GONE);
            }
        });
        ivClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               editText.setText("");
            }
        });
        rvDataList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                SoftKeyboardTool.hideSoftKeyboard(CodeSearchActivity.this);
            }
        });
        tflHotKey.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (hotkeyDatas!=null){
                   String name= hotkeyDatas.get(position).getName();
                   editText.setText(name);
                   editText.setSelection(name.length());
                   search();
                }
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    ivClean.setVisibility(View.VISIBLE);
                }else {
                    ivClean.setVisibility(View.INVISIBLE);
                    showAssistLayout();
                }
            }
        });
        searchHistoryAdapter.setiDataCallback(new CodeSearchHistoryAdapter.IDataCallback() {
            @Override
            public void onClickItem(int position) {
                String name= historyKeyBeans.get(position).getKey();
                editText.setText(name);
                editText.setSelection(name.length());
                search();
            }

            @Override
            public void onDelItem(int position) {
                CodeController.removeSearchHistoryKey(historyKeyBeans.get(position).getKey());
                historyKeyBeans.remove(position);
                searchHistoryAdapter.notifyItemRemoved(position);
                if (historyKeyBeans.isEmpty()){
                    llHistoryKey.setVisibility(View.GONE);
                }
            }

        });
    }



    private void search() {
        SoftKeyboardTool.hideSoftKeyboard(CodeSearchActivity.this);
        hideAssistLayout();
        page=0;
        key=editText.getText().toString();
        if (TextUtils.isEmpty(key.trim())){
            srlRefresh.setRefreshing(false);
            return;
        }
        CodeController.saveSearchHistoryKey(key);
        getData(key);
    }

    private void initViews() {
        titleBar=findViewById(R.id.tb_title);
        titleBarCenter= getTitleBarCenterView();
        editText=titleBarCenter.findViewById(R.id.et_search_input);
        ivClean=titleBarCenter.findViewById(R.id.iv_clean);
        titleBar.addCenterView(titleBarCenter);
        srlRefresh=findViewById(R.id.srl_refresh);
        rvDataList=findViewById(R.id.rv_data_list);
        tflHotKey=findViewById(R.id.tfl_hot_key);
        tvClean=findViewById(R.id.tv_clean);
        llAssist=findViewById(R.id.ll_assist);
        llHistoryKey=findViewById(R.id.ll_history_key);
        llHotKey=findViewById(R.id.ll_hot_key);
        rvHistoryDataList=findViewById(R.id.rv_history_data_list);
    }


    private View getTitleBarCenterView() {
       return  LayoutInflater.from(this).inflate(R.layout.code_search_edit_layout,titleBar,false);
    }

    public static void startActivity(Context context){

        context.startActivity(new Intent(context,CodeSearchActivity.class));

    }



}
