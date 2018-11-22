package com.example.weizhenbin.wangebug.views.nestlayout

import android.content.Context
import android.support.annotation.AttrRes
import android.support.v4.view.NestedScrollingParent2
import android.support.v4.view.NestedScrollingParentHelper
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.views.TitleBar

/**
 * Created by weizhenbin on 2018/9/21.
 */

class NestLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr), NestedScrollingParent2 {

    private var iRollChange: IRollChange? = null
    private var nestedScrollingParentHelper: NestedScrollingParentHelper = NestedScrollingParentHelper(this)

    internal var titleBar: TitleBar? = null

    private var titleBarHeight: Int = 0

    init {
        titleBarHeight = resources.getDimensionPixelSize(R.dimen.title_bar_height)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        //找出所有符合条件的view
        //滑动的时候进行重新派遣指定事件
        titleBar = getTargetView(this, TitleBar::class.java) as TitleBar?

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        if (titleBar != null) {
            setMeasuredDimension(View.getDefaultSize(0, widthMeasureSpec), View.getDefaultSize(0, heightMeasureSpec))
            val childWidthSize = measuredWidth
            val childHeightSize = measuredHeight
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidthSize, View.MeasureSpec.EXACTLY)
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(childHeightSize + titleBarHeight, View.MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    //找到第一个符合条件的view
    private fun getTargetView(viewGroup: ViewGroup?, aClass: Class<*>): View? {
        if (viewGroup == null) {
            return null
        }
        val size = viewGroup.childCount
        for (i in 0 until size) {
            val v = viewGroup.getChildAt(i)
            if (v.javaClass == aClass) {
                return v
            } else if (v is ViewGroup) {
                return this.getTargetView(v, aClass)
            }
        }
        return null
    }


    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        nestedScrollingParentHelper.onStopNestedScroll(target, type)
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        //  Log.d("NestLayout", "onNestedScroll");
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        //  Log.d("NestLayout", "onNestedPreScroll");
        if (titleBar != null) {
            //dy<0  向下拉
            if (dy < 0 && scrollY > 0) {
                if (scrollY + dy < 0) {
                    scrollBy(0, -scrollY)
                    consumed[1] = -scrollY
                } else {
                    scrollBy(0, dy)
                    consumed[1] = dy
                }
            }
            //往上滑
            if (dy > 0 && scrollY < titleBarHeight) {

                if (scrollY + dy > titleBarHeight) {
                    scrollBy(0, titleBarHeight - scrollY)
                    consumed[1] = titleBarHeight - scrollY
                } else {
                    scrollBy(0, dy)
                    consumed[1] = dy
                }
            }
            var alpha = (1 - scrollY / titleBarHeight).toFloat()
            if (alpha < 0.4) {
                alpha = 0f
            }
            titleBar!!.llTitleBar!!.alpha = alpha
        }
        if (iRollChange != null) {
            iRollChange!!.onRollChange(dy)
        }
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return false
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }


    fun setiRollChange(iRollChange: IRollChange) {
        this.iRollChange = iRollChange
    }
}
