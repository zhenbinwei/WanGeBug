package com.example.weizhenbin.wangebug.base

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.KeyEvent
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler
import com.example.weizhenbin.wangebug.eventbus.EventCode
import com.example.weizhenbin.wangebug.eventbus.MessageEvent
import com.example.weizhenbin.wangebug.modules.collect.controllers.CollectController
import com.example.weizhenbin.wangebug.modules.collect.entity.TBCollectBean
import com.example.weizhenbin.wangebug.tools.PhoneTool
import com.example.weizhenbin.wangebug.tools.UUIDTool
import com.example.weizhenbin.wangebug.views.ListPopupWindow
import com.example.weizhenbin.wangebug.views.TitleBar
import com.example.weizhenbin.wangebug.views.WebViewLayout

/**
 * Created by weizhenbin on 2018/8/24.
 */

class WebActivity : BaseActivity() {
    lateinit var wbLayout: WebViewLayout
    lateinit var url: String
    lateinit var titleBar: TitleBar
    lateinit var items: Array<String>
    lateinit var mTitle: String
     var urlTypeEnum: UrlTypeEnum=UrlTypeEnum.UNKNOWN;
     var isCollect = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFormat(PixelFormat.TRANSLUCENT)
        window.setFlags(android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        setContentView(R.layout.activity_web)
        initViews()
        initEvent()
        initData()
    }

    private fun initData() {
        items = arrayOf(getString(R.string.collect_string), getString(R.string.share_string), getString(R.string.browser_open_string))
        url = intent.getStringExtra("Url")
        mTitle = intent.getStringExtra("Title")
        urlTypeEnum = intent.getSerializableExtra("UrlType") as UrlTypeEnum
        if (urlTypeEnum == null) {
            urlTypeEnum = UrlTypeEnum.UNKNOWN
        }
        wbLayout.setUrl(url)
        titleBar.setTitle(mTitle)
    }

    private fun initViews() {
        wbLayout = findViewById(R.id.wb_layout)
        titleBar = findViewById(R.id.tb_title)
    }

    private fun initEvent() {
        wbLayout.addEventCallBack { title ->
            if (TextUtils.isEmpty(mTitle)) {
                titleBar.setTitle(title)
                mTitle = title
            }
            isCollect = CollectController.isExistByTitle(mTitle)
        }
        titleBar.setLeftOnClickListener { finish() }
        titleBar.setRightOnClickListener { v ->
            if (isCollect) {
                items[0] = getString(R.string.cancel_collect_string)
            } else {
                items[0] = getString(R.string.collect_string)
            }
            ListPopupWindow(this, items).setItemListener { position ->
                when (position) {
                    0 -> if (isCollect) {
                        CollectController.removeCollectByTitle(mTitle)
                        isCollect = false
                        EventBusHandler.post(MessageEvent(EventCode.CANCEL_COLLECT_CODE, mTitle))
                    } else {
                        val tbCollectBean = TBCollectBean()
                        tbCollectBean.title = mTitle
                        tbCollectBean.url = url
                        tbCollectBean.key = UUIDTool.uuid
                        tbCollectBean.collectTime = System.currentTimeMillis()
                        tbCollectBean.type = urlTypeEnum!!.typeValue
                        CollectController.addCollect(tbCollectBean)
                        isCollect = true
                        EventBusHandler.post(MessageEvent(EventCode.ADD_COLLECT_CODE, tbCollectBean))
                    }
                    1 -> {
                    }
                    2 -> {
                        val uri = Uri.parse(url)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                    }
                }
            }.showAsDropDown(v, -PhoneTool.dip2px(8f), -PhoneTool.dip2px(8f), Gravity.END)
        }
    }


    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {

        return if (event.action == KeyEvent.KEYCODE_BACK) {
            wbLayout.back()
        } else super.onKeyUp(keyCode, event)
    }

    companion object {

        @JvmOverloads
        fun startActivity(context: Context?, url: String, title: String? = null, urlTypeEnum: UrlTypeEnum? = null) {

            context?:return

            if (TextUtils.isEmpty(url)) {
                return
            }
            Intent(context,WebActivity::class.java).run {
               putExtra("Url", url)
                putExtra("UrlType", urlTypeEnum)
                putExtra("Title", title)
                context.startActivity(this);
            }
        }
    }
}
