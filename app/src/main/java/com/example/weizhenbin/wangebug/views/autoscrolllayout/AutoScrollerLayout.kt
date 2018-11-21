package com.example.weizhenbin.wangebug.views.autoscrolllayout

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.tools.PhoneTool

/**
 * Created by weizhenbin on 2018/8/27.
 * 自动轮播布局
 */

class AutoScrollerLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private val asvpViewpage: AutoScrollerViewPager
    private val llIndicator: LinearLayout
    private var pageAadpter: AutoScrollerAdapter? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.auto_scroller_layout, this)
        asvpViewpage = findViewById(R.id.asvp_viewpage)
        llIndicator = findViewById(R.id.ll_indicator)
        initEvent()
    }

    private fun initEvent() {
        asvpViewpage.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (pageAadpter != null) {
                    if (pageAadpter!!.readCount > 0) {
                        val newPosition = position % pageAadpter!!.readCount
                        selectIndex(newPosition)
                    }
                }
            }
        })
    }


    fun setPagerAdapter(pageAadpter: AutoScrollerAdapter?) {
        if (pageAadpter != null) {
            this.pageAadpter = pageAadpter
            asvpViewpage.adapter = pageAadpter
            if (pageAadpter.readCount > 1) {
                asvpViewpage.setAutoScrollAble(true)
            } else {
                asvpViewpage.setAutoScrollAble(false)
            }
            addIndicator()
        }
    }

    private fun addIndicator() {
        llIndicator.removeAllViews()
        val dotCount = pageAadpter!!.readCount
        for (i in 0 until dotCount) {
            val dotImageView = ImageView(context)
            val params = LinearLayout.LayoutParams(PhoneTool.dip2px(8f), PhoneTool.dip2px(8f))
            params.rightMargin = PhoneTool.dip2px(4f)
            dotImageView.setBackgroundResource(R.drawable.indicator_dot)
            llIndicator.addView(dotImageView, params)
        }
    }

    private fun selectIndex(index: Int) {
        if (index >= 0 && index < llIndicator.childCount) {
            val childCount = llIndicator.childCount
            for (i in 0 until childCount) {
                val view = llIndicator.getChildAt(i)
               view.isSelected=  (index == i)
            }
        }
    }
}
