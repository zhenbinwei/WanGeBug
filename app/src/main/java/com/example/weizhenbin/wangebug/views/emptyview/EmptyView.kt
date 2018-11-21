package com.example.weizhenbin.wangebug.views.emptyview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.weizhenbin.wangebug.R

/**
 * Created by weizhenbin on 2018/9/27.
 */

class EmptyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), IEmptyViewProxy {

    private val tvEmpty: TextView?
    private val tvEvent: TextView?
    private val lvLoading: com.example.weizhenbin.wangebug.views.LoadingView

    init {
        LayoutInflater.from(context).inflate(R.layout.empty_layout, this)
        setBackgroundColor(resources.getColor(R.color.colorWhite))
        gravity = Gravity.CENTER
        orientation = LinearLayout.VERTICAL
        isClickable = true
        tvEmpty = findViewById(R.id.tv_empty)
        tvEvent = findViewById(R.id.tv_event)
        lvLoading = findViewById(R.id.lv_loading)
    }

    override fun setEmptyText(text: String) {
        if (tvEmpty != null) {
            tvEmpty.text = text
        }
    }

    override fun setActionText(text: String) {
        if (tvEvent != null) {
            tvEvent.text = text
        }
    }

    override fun setAction(clickListener: View.OnClickListener?) {
        if (tvEvent == null) {
            return
        }
        if (clickListener == null) {
            tvEvent.visibility = View.GONE
        } else {
            tvEvent.visibility = View.INVISIBLE
        }
        tvEvent.setOnClickListener(clickListener)
    }

    override fun setEventVisibility(visibility: Int) {
        if (tvEvent != null) {
            tvEvent.visibility = visibility
        }
    }

    override fun loading() {
        loading(false)
    }

    override fun loading(isShowProgressBar: Boolean) {
        setEmptyText(context.getString(R.string.loading_string))
        setEventVisibility(View.INVISIBLE)
        setProgressBarVisibility(if (isShowProgressBar) View.VISIBLE else View.GONE)
    }

    override fun emptyData() {
        emptyData(true)
    }

    override fun emptyData(showAction: Boolean) {
        setEmptyText(context.getString(R.string.no_data_string))
        setEventVisibility(if (showAction) View.VISIBLE else View.INVISIBLE)
        setProgressBarVisibility(View.INVISIBLE)
    }

    override fun setProgressBarVisibility(visibility: Int) {
        lvLoading.visibility = visibility
    }


}
