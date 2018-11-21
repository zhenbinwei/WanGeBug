package com.example.weizhenbin.wangebug.views.autoscrolllayout

import android.content.Context
import android.os.Handler
import android.os.Message
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet

import java.lang.ref.WeakReference

/**
 * Created by weizhenbin on 2018/8/27.
 * 自动滚懂viewpgae
 */

class AutoScrollerViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    private var myHandler: Handler? = null

    /**时间间隔 毫秒
     */
    private var timeIntervalMs = 3000

    private var autoScrollAble = true

    init {
        initHandler()
    }

    fun setTimeIntervalMs(timeIntervalMs: Int) {
        this.timeIntervalMs = timeIntervalMs
    }

    private class ViewPageHandle  constructor(autoScrollerViewPager: AutoScrollerViewPager) : Handler() {
        private val weakReference: WeakReference<AutoScrollerViewPager> = WeakReference(autoScrollerViewPager)
        private var pagerAdapter: PagerAdapter? = null
        private var pageCount: Int = 0
        private var currentIndex = 0

        init {
            weakReference.get()?.let {
                pagerAdapter = it.adapter
            }
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            weakReference.get()?.let {
                if (msg.what == SCROLLER_KEY) {
                    if (pagerAdapter == null) {
                        pagerAdapter = it.adapter
                        if (pagerAdapter != null) {
                            pageCount = pagerAdapter!!.count
                        }
                    }
                    currentIndex = it.currentItem
                    if (currentIndex < pageCount) {
                        currentIndex++
                    }
                    it.currentItem = currentIndex
                    sendEmptyMessageDelayed(SCROLLER_KEY, it.timeIntervalMs.toLong())
                }
            }

        }
    }

    private fun initHandler() {
        myHandler = ViewPageHandle(this)
    }


    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (autoScrollAble) {
            myHandler!!.sendEmptyMessageDelayed(SCROLLER_KEY, timeIntervalMs.toLong())
        }
    }

    fun setAutoScrollAble(autoScrollAble: Boolean) {
        this.autoScrollAble = autoScrollAble
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        myHandler!!.removeCallbacksAndMessages(null)
    }

    companion object {

        private val SCROLLER_KEY = 100
    }
}
