package com.example.weizhenbin.wangebug.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

/**
 * Created by weizhenbin on 2018/3/12.
 */

public class WebViewLayout extends LinearLayout {
    private WebView webView;
    private WebIndicator webIndicator;
    private IEventCallBack iEventCallBack;
    public WebViewLayout(Context context) {
        this(context,null);
    }

    public WebViewLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WebViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        initIndicator();
        initWebView();
        this.addView(webIndicator,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 6));
        this.addView(webView,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
    private class MyWebCromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                //加载完毕进度条消失
                webIndicator.end();
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (iEventCallBack!=null){
                iEventCallBack.onReceivedTitle(title);
            }

        }
    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }
    public void setUrl(String url){
            webView.loadUrl(url);
    }


    public void addEventCallBack(IEventCallBack iEventCallBack){
        this.iEventCallBack=iEventCallBack;
    }

    private void initIndicator(){
        if(webIndicator==null){
            webIndicator=new WebIndicator(getContext());
            webIndicator.start();
        }
    }


    public boolean back(){
        if (webView.canGoBack()){
             webView.goBack();
            return true;
        }
        return false;
    }

    private void initWebView(){
        if(webView==null){
            webView=new WebView(getContext());
            //支持javascript
            WebSettings webSettings=webView.getSettings();
          //  webSettings.setJavaScriptEnabled(true);
            // 设置可以支持缩放
            webSettings.setSupportZoom(true);
            // 设置出现缩放工具
            webSettings.setBuiltInZoomControls(false);
            //扩大比例的缩放
            webSettings.setUseWideViewPort(true);
            //自适应屏幕
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webSettings.setLoadWithOverviewMode(true);
            webView.setWebChromeClient(new MyWebCromeClient());
            webView.setWebViewClient(new MyWebViewClient());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.setMixedContentMode(webSettings.getMixedContentMode());
            }
        }
    }

    public interface IEventCallBack{
        void onReceivedTitle(String title);
    }
}