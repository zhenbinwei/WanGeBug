/*
 * Copyright (C)  Justson(https://github.com/Justson/AgentWeb)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.weizhenbin.wangebug.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator

/**
 * @author cenxiaozhong
 * @since 1.0.0
 */
class WebIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    /**
     * 进度条颜色
     */
    private var mColor: Int = 0
    /**
     * 进度条的画笔
     */
    private var mPaint: Paint? = null
    /**
     * 正常进度条动画
     */
    private var mAnimator: Animator? = null

    /**
     * 标志当前进度条的状态
     */
    private  val TAG = 0


    private var mCurrentProgress = 0f

    private val mAnimatorUpdateListener = ValueAnimator.AnimatorUpdateListener { animation ->
        val t = animation.animatedValue as Float
        this@WebIndicator.mCurrentProgress = t
        this@WebIndicator.invalidate()
    }

    private val mAnimatorListenerAdapter = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            doEnd()
        }
    }

    init {
        mPaint = Paint()
        mColor = Color.parseColor("#1e90ff")
        mPaint!!.isAntiAlias = true
        mPaint!!.color = mColor
        mPaint!!.isDither = true
        mPaint!!.strokeCap = Paint.Cap.SQUARE
    }


    fun setColor(color: Int) {
        this.mColor = color
        mPaint!!.color = color
    }

    fun setColor(color: String) {
        this.setColor(Color.parseColor(color))
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0f, 0f, mCurrentProgress / 100 * java.lang.Float.valueOf(this.width.toFloat())!!, this.height.toFloat(), mPaint!!)
    }

    fun start() {
        if (visibility == View.GONE) {
            this.visibility = View.VISIBLE
        }
        mCurrentProgress = 0f
        startAnim(false)
    }

    fun setProgress(progress: Float) {
        if (visibility == View.GONE) {
            visibility = View.VISIBLE
        }
        if (progress < 95f) {
            return
        }
        if (TAG != FINISH) {
            startAnim(true)
        }
    }

    private fun hide() {
        if (visibility == View.VISIBLE) {
            visibility = View.GONE
        }
        if (mAnimator != null) {
            mAnimator!!.cancel()
        }
    }

    fun end() {
        startAnim(true)
    }

    @Synchronized
    private fun startAnim(isFinished: Boolean) {


        val v = (if (isFinished) 100 else 95).toFloat()


        if (mAnimator != null && mAnimator!!.isStarted) {
            mAnimator!!.cancel()
        }

        if (!isFinished) {
            val mAnimator = ValueAnimator.ofFloat(mCurrentProgress, v)
            mAnimator.interpolator = LinearInterpolator()
            mAnimator.duration = (4 * 1000).toLong()
            mAnimator.addUpdateListener(mAnimatorUpdateListener)
            mAnimator.start()
            this.mAnimator = mAnimator
        } else {
            if (mAnimator != null) {
                mAnimator!!.cancel()
            }
            var endAnimator: ValueAnimator? = null
            endAnimator = ValueAnimator.ofFloat(mCurrentProgress, v)
            endAnimator!!.duration = 300
            endAnimator.interpolator = AccelerateInterpolator()
            endAnimator.addUpdateListener(mAnimatorUpdateListener)
            endAnimator.addListener(mAnimatorListenerAdapter)
            endAnimator.start()
            mAnimator = endAnimator
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        /**
         * animator cause leak , if not cancel;
         */
        if (mAnimator != null && mAnimator!!.isStarted) {
            mAnimator!!.cancel()
            mAnimator = null
        }
    }

    private fun doEnd() {
        mCurrentProgress = 50f
        hide()
    }

    companion object {
        val UN_START = 0
        val STARTED = 1
        val FINISH = 2
    }
}
