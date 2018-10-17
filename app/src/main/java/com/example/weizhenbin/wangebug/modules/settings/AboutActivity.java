package com.example.weizhenbin.wangebug.modules.settings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.views.TitleBar;

/**
 * Created by weizhenbin on 2018/10/17.
 */
public class AboutActivity extends BaseActivity {
    TextView tvVersion;
    TitleBar tbTitle;
    TextView tvProjectContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initViews();
        initData();
        initEvent();
    }

    private void initEvent() {
        tbTitle.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        String name = getVersionName();
        tvVersion.setText(getString(R.string.version_string,name));
        interceptHyperLink(tvProjectContent);
    }

    @Nullable
    private String getVersionName() {
        PackageManager manager = getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    private void initViews() {
        tvVersion=findViewById(R.id.tv_version);
        tbTitle=findViewById(R.id.tb_title);
        tvProjectContent=findViewById(R.id.tv_project_content);
    }

    public static void startActivity (Context context){
        if(context==null){
        return;
        }
        Intent intent = new Intent(context,AboutActivity.class);
        context.startActivity(intent);
 }

    private void interceptHyperLink(TextView tv) {
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
                Log.i("test","true");
             /*   Spannable spannable1 = (Spannable) tv.getText();
                NoUnderlineSpan noUnderlineSpan = new NoUnderlineSpan();
                spannable1.setSpan(noUnderlineSpan,0,text.length(), Spanned.SPAN_MARK_MARK);*/

            int end = text.length();
            Spannable spannable = (Spannable) tv.getText();
            URLSpan[] urlSpans = spannable.getSpans(0, end, URLSpan.class);
            if (urlSpans.length == 0) {
                return;
            }

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
            // 循环遍历并拦截 所有http://开头的链接
            for (URLSpan uri : urlSpans) {
                String url = uri.getURL();
                if (url.indexOf("http://") == 0||url.indexOf("https://")==0) {
                    CustomUrlSpan customUrlSpan = new CustomUrlSpan(this,url);
                    spannableStringBuilder.setSpan(customUrlSpan, spannable.getSpanStart(uri),
                            spannable.getSpanEnd(uri), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                }
            }
            tv.setText(spannableStringBuilder);
            tv.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public class CustomUrlSpan extends URLSpan {

        private Context context;
        private String url;
        public CustomUrlSpan(Context context,String url){
            super("");
            Log.d("CustomUrlSpan", "创建");
            this.context = context;
            this.url = url;
        }
      /*  @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(context.getResources().getColor(R.color.colorPrimary));
            ds.setUnderlineText(false);
        }*/

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(context.getResources().getColor(R.color.colorPrimary));
            ds.setUnderlineText(false);
        }

       /* @Override
        public void onClick(View widget) {
            // 在这里可以做任何自己想要的处理
            Log.d("CustomUrlSpan",""+ url);
        }*/

        @Override
        public void onClick(View widget) {
            Log.d("CustomUrlSpan", "点击");
        }
    }



    public class NoUnderlineSpan extends UnderlineSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }



}
