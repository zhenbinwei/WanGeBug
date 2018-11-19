package com.example.weizhenbin.wangebug.modules.code.uis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseActivity
import com.example.weizhenbin.wangebug.base.DataResultAdapter
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeArticleListAdapter
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeSearchHistoryAdapter
import com.example.weizhenbin.wangebug.modules.code.adapters.CodeSearchHotKeyFlowAdapter
import com.example.weizhenbin.wangebug.modules.code.controllers.CodeController
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean
import com.example.weizhenbin.wangebug.modules.code.entity.SearchHotKeyDataBean
import com.example.weizhenbin.wangebug.modules.code.entity.TBSearchHistoryKeyBean
import com.example.weizhenbin.wangebug.tools.SoftKeyboardTool
import com.example.weizhenbin.wangebug.views.LinearRecyclerView
import com.example.weizhenbin.wangebug.views.TitleBar
import com.zhy.view.flowlayout.TagFlowLayout
import java.util.*

/**
 * Created by weizhenbin on 2018/8/31.
 */

class CodeSearchActivity : BaseActivity() {
    lateinit var titleBar: TitleBar
    lateinit var llAssist: LinearLayout
    lateinit var srlRefresh: SwipeRefreshLayout
    lateinit var rvDataList: LinearRecyclerView
    lateinit var rvHistoryDataList: LinearRecyclerView
    lateinit var listAdapter: CodeArticleListAdapter
    lateinit var searchHistoryAdapter: CodeSearchHistoryAdapter
     var page = 0
     var datasBeen: MutableList<ArticleListDataBean.DataBean.DatasBean> = ArrayList()
     var hotkeyDatas: List<SearchHotKeyDataBean.DataBean>? = null
     var historyKeyBeans: MutableList<TBSearchHistoryKeyBean> = ArrayList()
    lateinit var key: String
    lateinit var tflHotKey: TagFlowLayout
    lateinit var tvClean: TextView


    lateinit var titleBarCenter: View
    lateinit var ivClean: ImageView
    lateinit var editText: EditText
    lateinit var llHotKey: LinearLayout
    lateinit var llHistoryKey: LinearLayout


    private val titleBarCenterView: View
        get() = LayoutInflater.from(this).inflate(R.layout.code_search_edit_layout, titleBar, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_search)
        initViews()
        initData()
        initEvent()
        getSearchHotKeyData()
        getSearchHistoryData()
    }


    private fun getSearchHotKeyData() {
        CodeController.getSearchHotKeyData(object : DataResultAdapter<SearchHotKeyDataBean>() {
            override fun onSuccess(t: SearchHotKeyDataBean?) {
                super.onSuccess(t)
                if (t != null && t.data != null) {
                    if (!t.data!!.isEmpty()) {
                        llHotKey.visibility = View.VISIBLE
                        hotkeyDatas = t.data
                        tflHotKey.adapter = CodeSearchHotKeyFlowAdapter(hotkeyDatas)
                    } else {
                        llHotKey.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun getSearchHistoryData() {
        CodeController.getSearchHistoryData(object : DataResultAdapter<List<TBSearchHistoryKeyBean>>() {
            override fun onSuccess(t: List<TBSearchHistoryKeyBean>?) {
                super.onSuccess(t)
                if (t != null && !t.isEmpty()) {
                    historyKeyBeans.clear()
                    llHistoryKey.visibility = View.VISIBLE
                    historyKeyBeans.addAll(t)
                    searchHistoryAdapter.notifyDataSetChanged()
                } else {
                    llHistoryKey.visibility = View.GONE
                }
            }
        })


    }

    private fun showAssistLayout() {
        llAssist.visibility = View.VISIBLE
        getSearchHistoryData()
    }

    private fun hideAssistLayout() {
        llAssist.visibility = View.GONE
    }

    private fun initData() {
        listAdapter = CodeArticleListAdapter(this, datasBeen)
        rvDataList.adapter = listAdapter
        listAdapter.bindToRecyclerView(rvDataList)
        listAdapter.disableLoadMoreIfNotFullPage()
        listAdapter.emptyData(false)
        listAdapter.setEmptyText(getString(R.string.input_key_string))

        searchHistoryAdapter = CodeSearchHistoryAdapter(this, historyKeyBeans)
        rvHistoryDataList.adapter = searchHistoryAdapter
    }

    private fun getData(key: String) {
        CodeController.getSearchData(page, key, object : DataResultAdapter<ArticleListDataBean>() {
            override fun onStart() {
                super.onStart()
                if (page == 0 && datasBeen.isEmpty()) {
                    listAdapter.loading(true)
                }
            }

            override fun onSuccess(t: ArticleListDataBean?) {
                super.onSuccess(t)
                srlRefresh.isRefreshing = false
                if (t != null && t.errorCode == 0) {
                    if (t.data != null && t.data!!.datas != null) {
                        if (page == 0) {
                            datasBeen.clear()
                        }
                        datasBeen.addAll(t.data!!.datas!!)
                        if (page == 0) {
                            listAdapter.setNewData(datasBeen)
                        } else {
                            listAdapter.notifyDataSetChanged()
                        }
                        if (t.data!!.isOver) {
                            listAdapter.loadMoreEnd()
                        } else {
                            listAdapter.loadMoreComplete()
                            page++
                        }
                    } else {
                        listAdapter.loadMoreEnd()
                        listAdapter.emptyData()
                    }
                }
            }
        })
    }

    private fun initEvent() {
        titleBar.setLeftOnClickListener { finish() }
        titleBar.setRightOnClickListener { search() }
        editText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
                return@OnEditorActionListener true
            }
            false
        })
        srlRefresh.setOnRefreshListener { search() }
        listAdapter.setOnLoadMoreListener({ getData(key) }, rvDataList)
        tvClean.setOnClickListener {
            CodeController.cleanSearchHistoryKey()
            llHistoryKey.visibility = View.GONE
        }
        ivClean.setOnClickListener { editText.setText("") }
        rvDataList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                SoftKeyboardTool.hideSoftKeyboard(this@CodeSearchActivity)
            }
        })
        tflHotKey.setOnTagClickListener { _, position, _ ->
            if (hotkeyDatas != null) {
                val name = hotkeyDatas!![position].name
                editText.setText(name)
                editText.setSelection(name?.length?:0)
                search()
            }
            false
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    ivClean.visibility = View.VISIBLE
                } else {
                    ivClean.visibility = View.INVISIBLE
                    showAssistLayout()
                }
            }
        })
        searchHistoryAdapter.iDataCallback=(object : CodeSearchHistoryAdapter.IDataCallback {
            override fun onClickItem(position: Int) {
                val name = historyKeyBeans[position].key
                editText.setText(name)
                editText.setSelection(name.length)
                search()
            }

            override fun onDelItem(position: Int) {
                CodeController.removeSearchHistoryKey(historyKeyBeans[position].key)
                historyKeyBeans.removeAt(position)
                searchHistoryAdapter.notifyItemRemoved(position)
                if (historyKeyBeans.isEmpty()) {
                    llHistoryKey.visibility = View.GONE
                }
            }

        })
    }


    private fun search() {
        SoftKeyboardTool.hideSoftKeyboard(this@CodeSearchActivity)
        hideAssistLayout()
        page = 0
        key = editText.text.toString()
        if (TextUtils.isEmpty(key.trim { it <= ' ' })) {
            srlRefresh.isRefreshing = false
            return
        }
        CodeController.saveSearchHistoryKey(key)
        getData(key)
    }

    private fun initViews() {
        titleBar = findViewById(R.id.tb_title)
        titleBarCenter = titleBarCenterView
        editText = titleBarCenter.findViewById(R.id.et_search_input)
        ivClean = titleBarCenter.findViewById(R.id.iv_clean)
        titleBar.addCenterView(titleBarCenter)
        srlRefresh = findViewById(R.id.srl_refresh)
        rvDataList = findViewById(R.id.rv_data_list)
        tflHotKey = findViewById(R.id.tfl_hot_key)
        tvClean = findViewById(R.id.tv_clean)
        llAssist = findViewById(R.id.ll_assist)
        llHistoryKey = findViewById(R.id.ll_history_key)
        llHotKey = findViewById(R.id.ll_hot_key)
        rvHistoryDataList = findViewById(R.id.rv_history_data_list)
    }

    companion object {

        fun startActivity(context: Context) {

            context.startActivity(Intent(context, CodeSearchActivity::class.java))

        }
    }


}
