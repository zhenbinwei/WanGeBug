package com.example.weizhenbin.wangebug.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.tools.PhoneTool

/**
 * Created by weizhenbin on 2018/9/30.
 */
class LoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var paint: Paint

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0


    private var dotCount: Int = 0

    private var dotRadius: Int = 0

    private var dotSpace: Int = 0

    private var dotBgColor: Int = 0//背景色
    private var dotFgColor: Int = 0//前景色

    private var dotTotalWidth: Int = 0

    private var index: Int = 0

    private var myHandler: Handler

    init {
        try {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
            dotCount = typedArray.getInt(R.styleable.LoadingView_dotCount, 3)
            dotRadius = typedArray.getInt(R.styleable.LoadingView_dotRadius, PhoneTool.dip2px(4f))
            dotSpace = typedArray.getInt(R.styleable.LoadingView_dotSpace, PhoneTool.dip2px(4f))
            dotBgColor = typedArray.getInt(R.styleable.LoadingView_dotBgColor, resources.getColor(R.color.colorGray9))
            dotFgColor = typedArray.getInt(R.styleable.LoadingView_dotFgColor, resources.getColor(R.color.colorPrimary))
            typedArray.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        paint = Paint()
        paint.isAntiAlias = true
        dotTotalWidth = dotCount * dotRadius * 2 + dotSpace * (dotCount - 1)
        myHandler = AnimHandler(this)
        myHandler.sendEmptyMessage(1)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(Math.max(w, dotTotalWidth), Math.max(h, dotRadius * 2))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (viewHeight == 0 || viewWidth == 0) {
            viewHeight = height
            viewWidth = width
            canvas.drawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        }

        for (i in 0 until dotCount) {
            if (i == index) {
                paint.color = dotFgColor
            } else {
                paint.color = dotBgColor
            }
            canvas.drawCircle((dotRadius * (2 * i + 1) + i * dotSpace + (viewWidth - dotTotalWidth) / 2).toFloat(), (viewHeight / 2).toFloat(), dotRadius.toFloat(), paint!!)
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)

        if (visibility == View.VISIBLE) {
            //显示的启动动画
            myHandler.removeCallbacksAndMessages(null)
            myHandler.sendEmptyMessage(1)
        } else {
            //关闭动画
            myHandler.removeCallbacksAndMessages(null)
        }
    }

    private class AnimHandler  constructor(internal var loadingView: LoadingView) : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            sendEmptyMessageDelayed(1, 160)
            if (loadingView.index < loadingView.dotCount) {
                loadingView.index++
            } else {
                loadingView.index = 0
            }
            loadingView.postInvalidate()
        }
    }

}
