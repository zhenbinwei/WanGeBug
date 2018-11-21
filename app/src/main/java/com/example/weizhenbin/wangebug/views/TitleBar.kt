package com.example.weizhenbin.wangebug.views

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.tools.PhoneTool
import com.example.weizhenbin.wangebug.tools.StatusTool

/**
 * Created by weizhenbin on 2018/8/3.
 */

class TitleBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

   private var rlLeft: RelativeLayout? = null
    private var rlCenter: RelativeLayout? = null
    private var rlRight: RelativeLayout? = null


    private var title: String? = null
    private var leftSrc = -1
    private var rightSrc = -1
    private var titleColor: Int = 0
    private var titleSize: Int = 0

    var llTitleBar: LinearLayout? = null
        private set

    private fun assignViews() {
        rlLeft = findViewById(R.id.rl_left)
        rlCenter = findViewById(R.id.rl_center)
        rlRight = findViewById(R.id.rl_right)
        llTitleBar = findViewById(R.id.ll_title_bar)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.app_title_bar, this)
        assignViews()
        setPadding(0, StatusTool.getStatusBarHeight(context), 0, 0)
        try {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.titleBar)
            title = typedArray.getString(R.styleable.titleBar_title)
            leftSrc = typedArray.getResourceId(R.styleable.titleBar_leftSrc, -1)
            rightSrc = typedArray.getResourceId(R.styleable.titleBar_rightSrc, -1)
            titleColor = typedArray.getColor(R.styleable.titleBar_titleColor, -0x1000000)
            titleSize = typedArray.getDimensionPixelSize(R.styleable.titleBar_titleSize, PhoneTool.dip2px(16f))
            typedArray.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        init()
    }

    private fun init() {
        if (!TextUtils.isEmpty(title)) {
            addTitle(title)
        }
        if (leftSrc > 0) {
            rlLeft!!.visibility = View.VISIBLE
            addLeftIcon(leftSrc)
        } else {
            rlLeft!!.visibility = View.GONE
        }
        if (rightSrc > 0) {
            rlRight!!.visibility = View.VISIBLE
            addRightIcon(rightSrc)
        } else {
            rlRight!!.visibility = View.GONE
        }
    }

    private fun addLeftIcon(resId: Int) {
        val imageView = getImageView(resId)
        addLeftView(imageView)
    }

    private fun addRightIcon(resId: Int) {
        val imageView = getImageView(resId)
        addRightView(imageView)
    }

    private fun getImageView(resId: Int): ImageView {
        val imageView = ImageView(context)
        imageView.setImageResource(resId)
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        val layoutParams = RelativeLayout.LayoutParams(PhoneTool.dip2px(46f), PhoneTool.dip2px(46f))
        imageView.layoutParams = layoutParams
        return imageView
    }

    fun setTitle(title: String?) {
        addTitle(title)
    }

    private fun addTitle(title: String?) {
        title?.let {
            val textView = TextView(context)
            textView.text = title
            textView.maxLines = 1
            textView.ellipsize = TextUtils.TruncateAt.END
            textView.setTextColor(titleColor)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
            val layoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            textView.layoutParams = layoutParams
            addCenterView(textView)
        }
    }

    fun setLeftOnClickListener(leftOnClickListener: (v : View)->Unit) {
        if (rlLeft != null) {
            rlLeft!!.setOnClickListener(leftOnClickListener)
        }
    }

    fun setRightOnClickListener(rightOnClickListener: (v : View)->Unit) {
        if (rlRight != null) {
            rlRight!!.setOnClickListener(rightOnClickListener)
        }
    }

    fun addLeftView(view: View?) {
        if (view != null) {
            rlLeft!!.visibility = View.VISIBLE
            rlLeft!!.removeAllViews()
            rlLeft!!.addView(view)
        }
    }

    fun addCenterView(view: View?) {
        if (view != null) {
            rlCenter!!.visibility = View.VISIBLE
            rlCenter!!.removeAllViews()
            rlCenter!!.addView(view)
        }
    }

    fun addRightView(view: View?) {
        if (view != null) {
            rlRight!!.visibility = View.VISIBLE
            rlRight!!.removeAllViews()
            rlRight!!.addView(view)
        }
    }
}
