package com.example.weizhenbin.wangebug.views

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.tools.PhoneTool

/**
 * Created by weizhenbin on 2018/10/16.
 */
class CustomDialog private constructor(context: Context, builder: Builder?) : Dialog(context, R.style.CustomDialog) {
    init {

        if (builder == null) {
            throw NullPointerException("builder 不能为空")
        }
        val view: View
        if (builder.items != null && builder.items!!.isNotEmpty()) {
            view = View.inflate(context, R.layout.custom_list_dialog, null)
            val lvList = view.findViewById<ListView>(R.id.lv_list)
            val arrayAdapter = ArrayAdapter(context, R.layout.list_dialog_item, builder.items!!)
            lvList.adapter = arrayAdapter
            lvList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                dismiss()
                if (builder.onListItemClickListener != null) {
                    builder.onListItemClickListener!!.onClick(this@CustomDialog, position)
                }
            }
        } else {
            view = View.inflate(context, R.layout.custom_dialog, null)
            val tvTitle = view.findViewById<TextView>(R.id.tv_title)
            val tvMsg = view.findViewById<TextView>(R.id.tv_msg)
            val tvPositive = view.findViewById<TextView>(R.id.tv_positive)
            val tvNegative = view.findViewById<TextView>(R.id.tv_negative)
            if (TextUtils.isEmpty(builder.title) && builder.titleResId <= 0) {
                tvTitle.visibility = View.GONE
            } else {
                tvTitle.visibility = View.VISIBLE
                if (builder.titleResId > 0) {
                    tvTitle.setText(builder.titleResId)
                }
                if (!TextUtils.isEmpty(builder.title)) {
                    tvTitle.text = builder.title
                }
            }
            if (TextUtils.isEmpty(builder.message) && builder.messageResId <= 0) {
                tvMsg.visibility = View.GONE
            } else {
                tvMsg.visibility = View.VISIBLE
                if (builder.messageResId > 0) {
                    tvMsg.setText(builder.messageResId)
                }
                if (!TextUtils.isEmpty(builder.message)) {
                    tvMsg.text = builder.message
                }
            }

            if (TextUtils.isEmpty(builder.negativeBtnStr) && builder.negativeBtnStrResId <= 0 || builder.onNegativeClickListener == null) {
                tvNegative.visibility = View.GONE
            } else {
                tvNegative.visibility = View.VISIBLE
                if (builder.negativeBtnStrResId > 0) {
                    tvNegative.setText(builder.negativeBtnStrResId)
                }
                if (!TextUtils.isEmpty(builder.negativeBtnStr)) {
                    tvNegative.text = builder.negativeBtnStr
                }
                tvNegative.setOnClickListener {
                    dismiss()
                    if (builder.onNegativeClickListener != null) {
                        builder.onNegativeClickListener!!(this@CustomDialog, 0)
                    }
                }
            }
            if (TextUtils.isEmpty(builder.positiveBtnStr) && builder.positiveBtnStrResId <= 0 || builder.onPositiveClickListener == null) {
                tvPositive.visibility = View.GONE
            } else {
                tvPositive.visibility = View.VISIBLE
                if (builder.positiveBtnStrResId > 0) {
                    tvPositive.setText(builder.positiveBtnStrResId)
                }
                if (!TextUtils.isEmpty(builder.positiveBtnStr)) {
                    tvPositive.text = builder.positiveBtnStr
                }
                tvPositive.setOnClickListener {
                    dismiss()
                    if (builder.onPositiveClickListener != null) {
                        builder.onPositiveClickListener!!(this@CustomDialog, 0)
                    }
                }
            }
        }
        setContentView(view)
    }


    override fun show() {
        super.show()
        val window = window ?: return
        val layoutParams = getWindow()!!.attributes
        layoutParams.gravity = Gravity.CENTER
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

        getWindow()!!.decorView.setPadding(PhoneTool.dip2px(30f), 0, PhoneTool.dip2px(30f), 0)

        getWindow()!!.attributes = layoutParams
    }

    class Builder(private val context: Context) {
         var title: String? = null
         var titleResId: Int = 0
         var message: String? = null
         var messageResId: Int = 0
         var positiveBtnStr: String? = null
         var positiveBtnStrResId: Int = 0
         var negativeBtnStr: String? = null
         var negativeBtnStrResId: Int = 0
         var onPositiveClickListener: ((dialog: DialogInterface, which: Int)->Unit)? = null
         var onNegativeClickListener: ((dialog: DialogInterface, which: Int)->Unit)? = null
         var items: Array<String>? = null
         var onListItemClickListener: OnClickListener? = null

        fun setTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun setTitleResId(titleResId: Int): Builder {
            this.titleResId = titleResId
            return this
        }

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setMessageResId(messageResId: Int): Builder {
            this.messageResId = messageResId
            return this
        }

        fun setPositiveButton(positiveBtnStr: String, onPositiveClickListener: ((dialog: DialogInterface, which: Int)->Unit)?): Builder {
            this.positiveBtnStr = positiveBtnStr
            this.onPositiveClickListener = onPositiveClickListener
            return this
        }


        fun setNegativeButton(negativeBtnStr: String, onNegativeClickListener: ((dialog: DialogInterface, which: Int)->Unit)?): Builder {
            this.negativeBtnStr = negativeBtnStr
            this.onNegativeClickListener = onNegativeClickListener
            return this
        }


        fun setItems(items: Array<String>, onListItemClickListener: OnClickListener): Builder {
            this.items = items
            this.onListItemClickListener = onListItemClickListener
            return this
        }

        fun create(): CustomDialog {
            return CustomDialog(context, this)
        }
    }

    interface OnClickListener {
        fun onClick(dialog: DialogInterface, which: Int)
    }
}
