package com.example.weizhenbin.wangebug.views.floatingwindow

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PixelFormat
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.App
import com.example.weizhenbin.wangebug.tools.PhoneTool
import com.example.weizhenbin.wangebug.tools.SoftKeyboardTool
import com.example.weizhenbin.wangebug.tools.StatusTool

/**
 * Created by weizhenbin on 2018/9/12.
 * 悬浮窗
 */

class FloatingWindow : View.OnTouchListener {

    private var windowManager: WindowManager? = null
    private var controlLayoutParams: WindowManager.LayoutParams? = null//活动控制层
    private var baseLayoutParams: WindowManager.LayoutParams? = null//基础层 底层
    private val contentView: ViewGroup
    private val baseView: FrameLayout//基础层


    private val controller: ImageView//控制器 透明 用于接受触摸事件 浮窗位置
    private val fvView: FloatingView

    /**
     * 悬浮窗大小
     */
    private var windowMiniWidth = PhoneTool.dip2px(48f)

    private var windowMiniHeight = PhoneTool.dip2px(48f)


    /**
     * 按下相对View的坐标
     */
    private var downViewX: Int = 0
    private var downViewY: Int = 0

    /**
     * 屏幕宽高
     */
    private val screenW: Int
    private val screenH: Int

    /**
     * 状态栏高度
     */
    private val statusBarHeight: Int


    /**
     * 属性动画 用于回弹效果
     */
    private var animator: ValueAnimator? = null

    private var mGestureDetector: GestureDetector? = null

    private var isAddView = false

    /**迷你状态 */
    private var isMini = true


    private var iWindowChangeListener: IWindowChangeListener? = null

    private val context: Context

    init {
        context = App.app.applicationContext
        initWindowManager(context)
        baseView = FloatingContentView(context)
        contentView = LayoutInflater.from(context).inflate(R.layout.floating_window_layout, baseView, false) as ViewGroup
        baseView.addView(contentView)
        controller = ImageView(context)
        controller.setImageResource(R.drawable.add)
        controller.scaleType = ImageView.ScaleType.CENTER_INSIDE
        controller.setBackgroundResource(R.drawable.round_bg_primary)
        fvView = contentView.findViewById(R.id.fv_view)
        screenW = PhoneTool.screenWidth
        screenH = PhoneTool.screenHeight
        statusBarHeight = StatusTool.getStatusBarHeight(context)
        initEvent()
    }


    /**
     * 添加实际可操作的布局
     */
    fun addRealContentView(view: View?) {
        if (view != null && view.parent == null) {
            this.contentView.addView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        }
    }

    /**
     * 设置可缩放布局大小
     */
    fun setContentViewLayoutParams(layoutParams: FrameLayout.LayoutParams) {
        contentView.layoutParams = layoutParams
    }

    /**
     * 设置悬浮窗大小
     */
    fun setMiniWindowSize(width: Int, height: Int) {
        controlLayoutParams!!.height = height
        controlLayoutParams!!.width = width
        windowMiniHeight = height
        windowMiniWidth = width
        windowManager!!.updateViewLayout(controller, controlLayoutParams)
    }

    /**
     * 设置悬浮窗背景
     */
    fun setMiniWindowBackground(background: Drawable) {
        controller.background = background
    }

    /**
     * 设置悬浮窗图标
     */
    fun setMiniWindowIcon(resId: Int) {
        controller.setImageResource(resId)
    }

    /**
     * 设置悬浮窗图标
     */
    fun setMiniWindowIcon(drawable: Drawable) {
        controller.setImageDrawable(drawable)
    }

    private fun initEvent() {
        controller.setOnTouchListener(this)
        controller.setOnClickListener {
            if (!isMini) {
                zoomOutWindow()
            } else {
                zoomInWindow()
            }
        }
        baseView.setOnClickListener {
            if (!isMini) {
                viewZoomOut()
            }
        }
        baseView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (!isMini) {
                    viewZoomOut()
                }

                return@OnKeyListener true
            }
            false
        })

    }

    fun zoomInWindow() {
        if (isMini) {
            if (controlLayoutParams!!.y > 0 || controlLayoutParams!!.x > 0 && controlLayoutParams!!.x < screenW - windowMiniWidth) {
                val end: PointF
                if (controlLayoutParams!!.x + windowMiniWidth / 2 > screenW / 2) {
                    end = PointF((screenW - windowMiniWidth).toFloat(), 0f)
                } else {
                    end = PointF(0f, 0f)
                }
                scroll(PointF(controlLayoutParams!!.x.toFloat(), controlLayoutParams!!.y.toFloat()), end, object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        viewZoomIn()
                    }
                })
            } else {
                viewZoomIn()
            }
        }
    }

    fun zoomOutWindow() {
        if (!isMini) {
            viewZoomOut()
        }
    }

    /**
     * 扩展动画展开
     */
    private fun viewZoomIn() {
        val animatorListenerAdapter = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                baseLayoutParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                controlLayoutParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                windowManager!!.updateViewLayout(baseView, baseLayoutParams)
                windowManager!!.updateViewLayout(controller, controlLayoutParams)
                if (iWindowChangeListener != null) {
                    iWindowChangeListener!!.onWindowZoomIn()
                }
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                controller.visibility = View.INVISIBLE
            }
        }
        if (isMini) {
            isMini = false
            fvView.zoomIn(controlLayoutParams!!.x + windowMiniWidth / 2, controlLayoutParams!!.y + windowMiniHeight / 2, windowMiniWidth / 2, animatorListenerAdapter)
        }
    }

    private fun viewZoomOut() {
        SoftKeyboardTool.hideSoftKeyboard(context, baseView)
        if (!isMini) {
            windowManager!!.updateViewLayout(controller, controlLayoutParams)
            fvView.zoomOut(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    isMini = true
                    baseLayoutParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    controlLayoutParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    windowManager!!.updateViewLayout(baseView, baseLayoutParams)
                    windowManager!!.updateViewLayout(controller, controlLayoutParams)
                    controller.visibility = View.VISIBLE
                    fvView.postDelayed({ fvView.setCircleRadius(0) }, 100)
                    if (iWindowChangeListener != null) {
                        iWindowChangeListener!!.onWindowZoomOut()
                    }
                }
            })
        }
    }


    fun addFloatingWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                Log.d("FloatingWindow", "没有悬浮窗权限")
                return
            }
        }
        if (!isAddView) {
            isAddView = true
            windowManager!!.addView(baseView, baseLayoutParams)
            windowManager!!.addView(controller, controlLayoutParams)
        }

    }

    fun removeFloatingWindow() {
        if (isAddView) {
            isAddView = false
            windowManager!!.removeView(baseView)
            windowManager!!.removeView(controller)
        }
    }


    private fun initWindowManager(context: Context) {
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        controlLayoutParams = WindowManager.LayoutParams()
        baseLayoutParams = WindowManager.LayoutParams()
        /**
         * 8.0以上 没有授权会直接闪退 8.0以下部分手机没有授权 home切换到桌面 悬浮窗会消失 系统会提示禁止了弹窗 应用内能提示
         */
        if (Build.VERSION.SDK_INT >= 26) {
            controlLayoutParams!!.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            baseLayoutParams!!.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            controlLayoutParams!!.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            baseLayoutParams!!.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }

        //基础层初始状态不接收触摸事件
        baseLayoutParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE

        controlLayoutParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        controlLayoutParams!!.format = PixelFormat.TRANSLUCENT
        baseLayoutParams!!.format = PixelFormat.TRANSLUCENT
        controlLayoutParams!!.width = windowMiniWidth
        controlLayoutParams!!.height = windowMiniHeight
        baseLayoutParams!!.width = WindowManager.LayoutParams.MATCH_PARENT
        baseLayoutParams!!.height = WindowManager.LayoutParams.MATCH_PARENT
        controlLayoutParams!!.gravity = Gravity.TOP or Gravity.START
        baseLayoutParams!!.gravity = Gravity.TOP or Gravity.START

    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {

        if (mGestureDetector == null) {
            mGestureDetector = GestureDetector(v.context, GestureListener(v))
        }
        //长按 点击和onTouch 冲突问题  借助GestureDetector来解决
        mGestureDetector!!.onTouchEvent(event)
        if (!isMini) {
            return true
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downViewX = event.x.toInt()
                downViewY = event.y.toInt()
                fvView.setCircleRadius(0)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                controlLayoutParams!!.y = event.rawY.toInt() - statusBarHeight - downViewY
                controlLayoutParams!!.x = event.rawX.toInt() - downViewX
                windowManager!!.updateViewLayout(controller, controlLayoutParams)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val w = controlLayoutParams!!.width.toFloat()
                val h = controlLayoutParams!!.height.toFloat()
                if (controlLayoutParams!!.x < 0) {
                    controlLayoutParams!!.x = 0
                } else if (controlLayoutParams!!.x > screenW - controlLayoutParams!!.width) {
                    controlLayoutParams!!.x = screenW - controlLayoutParams!!.width
                }
                if (controlLayoutParams!!.y < 0) {
                    controlLayoutParams!!.y = 0
                } else if (controlLayoutParams!!.y > screenH - statusBarHeight - controlLayoutParams!!.height) {
                    controlLayoutParams!!.y = screenH - statusBarHeight - controlLayoutParams!!.height
                }

                val x = controlLayoutParams!!.x + w / 2  //用中心点来决定位置
                val y = controlLayoutParams!!.y + h / 2

                if (x >= screenW / 2 && y <= (screenH + statusBarHeight) / 2) {
                    //第一象限
                    if (screenW - x > y) {
                        scroll(PointF(controlLayoutParams!!.x.toFloat(), controlLayoutParams!!.y.toFloat()), PointF(controlLayoutParams!!.x.toFloat(), 0f))
                    } else {
                        scroll(PointF(controlLayoutParams!!.x.toFloat(), controlLayoutParams!!.y.toFloat()), PointF((screenW - controlLayoutParams!!.width).toFloat(), controlLayoutParams!!.y.toFloat()))
                    }
                } else if (x < screenW / 2 && y < (screenH + statusBarHeight) / 2) {
                    //第二象限
                    if (x > y) {
                        scroll(PointF(controlLayoutParams!!.x.toFloat(), controlLayoutParams!!.y.toFloat()), PointF(controlLayoutParams!!.x.toFloat(), 0f))
                    } else {
                        scroll(PointF(controlLayoutParams!!.x.toFloat(), controlLayoutParams!!.y.toFloat()), PointF(0f, controlLayoutParams!!.y.toFloat()))
                    }
                } else

                    if (x <= screenW / 2 && y >= (screenH + statusBarHeight) / 2) {
                        //第三象限
                        if (x > screenH - y) {
                            scroll(PointF(controlLayoutParams!!.x.toFloat(), controlLayoutParams!!.y.toFloat()), PointF(controlLayoutParams!!.x.toFloat(), (screenH - statusBarHeight - controlLayoutParams!!.height).toFloat()))
                        } else {
                            scroll(PointF(controlLayoutParams!!.x.toFloat(), controlLayoutParams!!.y.toFloat()), PointF(0f, controlLayoutParams!!.y.toFloat()))
                        }
                    } else if (x > screenW / 2 && y > (screenH + statusBarHeight) / 2) {
                        //第四象限
                        if (screenW - x > screenH - y) {
                            scroll(PointF(controlLayoutParams!!.x.toFloat(), controlLayoutParams!!.y.toFloat()), PointF(controlLayoutParams!!.x.toFloat(), (screenH - statusBarHeight - controlLayoutParams!!.height).toFloat()))
                        } else {
                            scroll(PointF(controlLayoutParams!!.x.toFloat(), controlLayoutParams!!.y.toFloat()), PointF((screenW - controlLayoutParams!!.width).toFloat(), controlLayoutParams!!.y.toFloat()))
                        }
                    }
            }
        }
        return false
    }


    private inner class PointTypeEvaluator : TypeEvaluator<PointF> {

        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            startValue.x = startValue.x + fraction * (endValue.x - startValue.x)
            startValue.y = startValue.y + fraction * (endValue.y - startValue.y)
            return startValue
        }
    }

    /**
     * 使用属性动画 实现缓慢回弹效果
     */
    private fun scroll(start: PointF, end: PointF, listener: Animator.AnimatorListener? = null) {
        if (animator != null && animator!!.isRunning) {
            animator!!.cancel()
            animator = null
        }
        animator = ValueAnimator.ofObject(PointTypeEvaluator(), start, end)
        animator!!.duration = 300
        animator!!.addUpdateListener { animation ->
            val point = animation.animatedValue as PointF
            controlLayoutParams!!.x = point.x.toInt()
            controlLayoutParams!!.y = point.y.toInt()
            windowManager!!.updateViewLayout(controller, controlLayoutParams)
        }
        if (listener != null) {
            animator!!.addListener(listener)
        }
        animator!!.start()
    }

    private inner class GestureListener (private val view: View?) : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return view != null && view.performClick()
        }

        override fun onLongPress(e: MotionEvent) {
            if (view == null) {
                return
            }
            view.performLongClick()
        }
    }

    fun setiWindowChangeListener(iWindowChangeListener: IWindowChangeListener) {
        this.iWindowChangeListener = iWindowChangeListener
    }

    interface IWindowChangeListener {
        fun onWindowZoomIn()
        fun onWindowZoomOut()
    }

}
