package com.example.weizhenbin.wangebug.views

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout

/**
 * Created by weizhenbin on 2018/3/12.
 */

class WebViewLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    private var webView: WebView? = null
    private var webIndicator: WebIndicator? = null
    private var iEventCallBack: ((title: String)->Unit)? = null

    init {
        orientation = LinearLayout.VERTICAL
        initIndicator()
        initWebView()
        this.addView(webIndicator, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 6))
        this.addView(webView, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    private inner class MyWebCromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (newProgress == 100) {
                //加载完毕进度条消失
                webIndicator!!.end()
            }
            super.onProgressChanged(view, newProgress)
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            if (iEventCallBack != null) {
                iEventCallBack!!(title)
            }

        }
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            super.onReceivedError(view, request, error)

        }
    }

    fun setUrl(url: String) {
        webView!!.loadUrl(url)
    }


    fun addEventCallBack(iEventCallBack: ((title: String)->Unit)?) {
        this.iEventCallBack = iEventCallBack
    }

    private fun initIndicator() {
        if (webIndicator == null) {
            webIndicator = WebIndicator(context)
            webIndicator!!.start()
        }
    }


    fun back(): Boolean {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
            return true
        }
        return false
    }

    private fun initWebView() {
        if (webView == null) {
            webView = WebView(context)
            //支持javascript
            val webSettings = webView!!.settings
            //  webSettings.setJavaScriptEnabled(true);
            // 设置可以支持缩放
            webSettings.setSupportZoom(true)
            // 设置出现缩放工具
            webSettings.builtInZoomControls = false
            //扩大比例的缩放
            webSettings.useWideViewPort = true
            //自适应屏幕
            webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            webSettings.loadWithOverviewMode = true
            webView!!.webChromeClient = MyWebCromeClient()
            webView!!.webViewClient = MyWebViewClient()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.mixedContentMode = webSettings.mixedContentMode
            }
        }
    }

    interface IEventCallBack {
        fun onReceivedTitle(title: String)
    }
}
