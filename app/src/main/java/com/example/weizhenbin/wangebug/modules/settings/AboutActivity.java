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
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseActivity;
import com.example.weizhenbin.wangebug.base.WebActivity;
import com.example.weizhenbin.wangebug.views.TitleBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by weizhenbin on 2018/10/17.
 */
public class AboutActivity extends BaseActivity {
    TextView tvVersion;
    TitleBar tbTitle;
    TextView tvProjectContent;
    TextView tvDeveloperInfo;
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
        interceptHyperLink(tvDeveloperInfo);

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
        tvDeveloperInfo=findViewById(R.id.tv_developer_info);
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
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        //系统识别url的正则
        Pattern r = Patterns.WEB_URL;
        // 现在创建 matcher 对象
        Matcher m;
        m = r.matcher(text);
        //匹配成功
        while (m.find()) {
            //得到网址数
            //排除一些qq.com 邮箱后缀  思路来自TextView autoLink 方法
            if (sUrlMatchFilter .acceptMatch(text,m.start(),m.end())) {
                spannableStringBuilder.setSpan(new CustomUrlSpan(this, m.group()), m.start(), m.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        tv.setText(spannableStringBuilder);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public static final Linkify.MatchFilter sUrlMatchFilter = new Linkify.MatchFilter() {
        public final boolean acceptMatch(CharSequence s, int start, int end) {
            if (start == 0) {
                return true;
            }

            if (s.charAt(start - 1) == '@') {
                return false;
            }

            return true;
        }
    };
    public class CustomUrlSpan extends ClickableSpan {

        private Context context;
        private String url;
        public CustomUrlSpan(Context context,String url){
            this.context = context;
            this.url = url;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(context.getResources().getColor(R.color.colorPrimary));
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            WebActivity.startActivity(context,url);
        }
    }

}
