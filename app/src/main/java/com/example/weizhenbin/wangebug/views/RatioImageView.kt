package com.example.weizhenbin.wangebug.views

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import com.example.weizhenbin.wangebug.R

/**
 * Created by weizhenbin on 2018/8/23.
 */

class RatioImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var ratio = 1f

    init {
        try {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView)
            ratio = typedArray.getFloat(R.styleable.RatioImageView_imageRatio, 1f)
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
        //super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), (View.MeasureSpec.getSize(widthMeasureSpec) * ratio).toInt())
    }
}
