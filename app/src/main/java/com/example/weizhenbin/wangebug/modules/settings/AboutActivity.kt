package com.example.weizhenbin.wangebug.modules.settings

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.util.Linkify
import android.util.Patterns
import android.view.View
import android.widget.TextView
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseActivity
import com.example.weizhenbin.wangebug.base.WebActivity
import com.example.weizhenbin.wangebug.views.TitleBar
import java.util.regex.Matcher

/**
 * Created by weizhenbin on 2018/10/17.
 */
class AboutActivity : BaseActivity() {
    lateinit var tvVersion: TextView
    lateinit var tbTitle: TitleBar
    lateinit var tvProjectContent: TextView
    lateinit var tvDeveloperInfo: TextView
    private val versionName: String?
        get() {
            val manager = packageManager
            var name: String? = null
            try {
                val info = manager.getPackageInfo(packageName, 0)
                name = info.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return name
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        initViews()
        initData()
        initEvent()
    }

    private fun initEvent() {
        tbTitle.setLeftOnClickListener { finish() }
    }

    private fun initData() {
        val name = versionName
        tvVersion.text = getString(R.string.version_string, name)
        interceptHyperLink(tvProjectContent)
        interceptHyperLink(tvDeveloperInfo)

    }

    private fun initViews() {
        tvVersion = findViewById(R.id.tv_version)
        tbTitle = findViewById(R.id.tb_title)
        tvProjectContent = findViewById(R.id.tv_project_content)
        tvDeveloperInfo = findViewById(R.id.tv_developer_info)
    }

    private fun interceptHyperLink(tv: TextView) {
        val text = tv.text
        val spannableStringBuilder = SpannableStringBuilder(text)
        //系统识别url的正则
        val r = Patterns.WEB_URL
        // 现在创建 matcher 对象
        val m: Matcher
        m = r.matcher(text)
        //匹配成功
        while (m.find()) {
            //得到网址数
            //排除一些qq.com 邮箱后缀  思路来自TextView autoLink 方法
            if (sUrlMatchFilter.acceptMatch(text, m.start(), m.end())) {
                spannableStringBuilder.setSpan(CustomUrlSpan(this, m.group()), m.start(), m.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
        tv.text = spannableStringBuilder
        tv.movementMethod = LinkMovementMethod.getInstance()
    }

    inner class CustomUrlSpan(private val context: Context, private val url: String) : ClickableSpan() {

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = context.resources.getColor(R.color.colorPrimary)
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            WebActivity.startActivity(context, url)
        }
    }

    companion object {

        fun startActivity(context: Context?) {
            if (context == null) {
                return
            }
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }

        val sUrlMatchFilter: Linkify.MatchFilter = Linkify.MatchFilter { s, start, _ ->
            if (start == 0) {
                return@MatchFilter true
            }

            s[start - 1] != '@'
        }
    }

}
