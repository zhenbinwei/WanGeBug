package com.example.weizhenbin.wangebug.views

import android.content.Context
import android.support.annotation.AttrRes
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.example.weizhenbin.wangebug.R

/**
 * Created by weizhenbin on 2018/8/27.
 */

class RatioFrameLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    private var ratio = 1f

    init {
        try {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout)
            ratio = typedArray.getFloat(R.styleable.RatioFrameLayout_frameRatio, 1f)
            typedArray.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setRatio(ratio: Float) {
        this.ratio = ratio
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        setMeasuredDimension(View.getDefaultSize(0, widthMeasureSpec), View.getDefaultSize(0, heightMeasureSpec))
        val childWidthSize = measuredWidth
        val childHeightSize = measuredHeight
        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidthSize, View.MeasureSpec.EXACTLY)
        /**按照比例改变高度值 */
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((childWidthSize * ratio).toInt(), View.MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }


}
