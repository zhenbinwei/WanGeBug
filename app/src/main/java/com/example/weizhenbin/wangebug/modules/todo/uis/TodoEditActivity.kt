package com.example.weizhenbin.wangebug.modules.todo.uis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.R.id.tb_title
import com.example.weizhenbin.wangebug.base.BaseActivity
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler
import com.example.weizhenbin.wangebug.eventbus.EventCode
import com.example.weizhenbin.wangebug.eventbus.MessageEvent
import com.example.weizhenbin.wangebug.modules.todo.controllers.TodoController
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean
import com.example.weizhenbin.wangebug.tools.DateTool
import com.example.weizhenbin.wangebug.tools.DialogTool
import com.example.weizhenbin.wangebug.tools.UUIDTool
import com.example.weizhenbin.wangebug.views.TitleBar

/**
 * Created by weizhenbin on 18/9/2.
 */

class TodoEditActivity : BaseActivity(), View.OnClickListener {

    private var tbTitle: TitleBar? = null
    private var tvRemindTime: TextView? = null
    private var edTitle: EditText? = null
    private var edContent: EditText? = null
    private var tvLoc: TextView? = null
    private var ivDelRemindTime: ImageView? = null
    private var tbTodoBean: TBTodoBean? = null

    private var timeStringBuilder: StringBuilder? = null

    private var isAlterRemindDate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_edit)
        initViews()
        initEvent()
        initData()
    }

    private fun initData() {
        val intent = intent
        if (intent.hasExtra("todo") && intent.getSerializableExtra("todo") != null) {
            tbTodoBean = intent.getSerializableExtra("todo") as TBTodoBean
            if (!TextUtils.isEmpty(tbTodoBean!!.todoContent)) {
                edContent!!.setText(tbTodoBean!!.todoContent)
                edContent!!.setSelection(tbTodoBean!!.todoContent?.length?:0)
            }
            if (!TextUtils.isEmpty(tbTodoBean!!.todoTitle)) {
                edTitle!!.setText(tbTodoBean!!.todoTitle)
                edTitle!!.setSelection(tbTodoBean!!.todoTitle?.length?:0)
            }
            if (!TextUtils.isEmpty(tbTodoBean!!.todoRemindTimeStr)) {
                tvRemindTime!!.text = tbTodoBean!!.todoRemindTimeStr
                ivDelRemindTime!!.visibility = View.VISIBLE
            }

        } else {
            tbTodoBean = TBTodoBean()
            tbTodoBean!!.todoStatus = 0
        }
        timeStringBuilder = StringBuilder()
    }

    private fun initEvent() {
        ivDelRemindTime!!.setOnClickListener(this)
        tvRemindTime!!.setOnClickListener(this)
        tbTitle!!.setLeftOnClickListener { onBackPressed() }
        tbTitle!!.setRightOnClickListener { saveOrUpdateTodo() }
        edContent!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                tbTodoBean!!.todoContent = s.toString()
            }
        })
        edTitle!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                tbTodoBean!!.todoTitle = s.toString()
            }
        })
    }


    override fun onBackPressed() {
        if (!tbTodoBean!!.isEmpty) {
            DialogTool.showAlertDialog(this@TodoEditActivity, null, getString(R.string.save_todo_remind_string), getString(R.string.save_string), { _, _ -> saveOrUpdateTodo() }, getString(R.string.cancel_string),{ _, _ -> finish() })
        } else {
            super.onBackPressed()
        }
    }

    private fun saveOrUpdateTodo() {
        if (tbTodoBean==null)
            return
        if (TextUtils.isEmpty(tbTodoBean!!.uuid)) {
            val todoCreateTime = System.currentTimeMillis()
            tbTodoBean!!.uuid = UUIDTool.uuid
            tbTodoBean!!.todoCreateTime = todoCreateTime
            tbTodoBean!!.todoCreateTimeStr = DateTool.getDateToString(todoCreateTime, "yyyy-MM-dd HH:mm")
            if (!setAlarm()) {
                return
            }
            TodoController.saveTodo(tbTodoBean!!)
            EventBusHandler.post(MessageEvent(EventCode.ADD_TODO_CODE, tbTodoBean!!))
        } else {
            val todoUpdateTime = System.currentTimeMillis()
            tbTodoBean!!.todoLastUpdateDate = todoUpdateTime
            tbTodoBean!!.todoLastUpdateDateStr = DateTool.getDateToString(todoUpdateTime, "yyyy-MM-dd HH:mm")
            if (!setAlarm()) {
                return
            }
            TodoController.updateTodoByUuid(tbTodoBean!!, tbTodoBean!!.uuid!!)
            EventBusHandler.post(MessageEvent(EventCode.UPDATE_TODO_CODE, tbTodoBean!!))
        }
        finish()
    }

    private fun setAlarm(): Boolean {
        if (isAlterRemindDate) {
            if (tbTodoBean!!.todoRemind == 1) {
                if (tbTodoBean!!.todoRemindTime < System.currentTimeMillis()) {
                    //提醒时间小于当前时间
                    DialogTool.showAlertDialog(this@TodoEditActivity, null, getString(R.string.remind_time_invalid_string), getString(R.string.confirm_string))
                    return false
                } else {
                    TodoController.setAlarm(this@TodoEditActivity, tbTodoBean!!)
                }
            } else {
                TodoController.cancelAlarm(this@TodoEditActivity, tbTodoBean!!)
            }
        }
        return true
    }

    private fun initViews() {
        tbTitle = findViewById(tb_title)
        tvRemindTime = findViewById(R.id.tv_remind_time)
        edTitle = findViewById(R.id.ed_title)
        edContent = findViewById(R.id.ed_content)
        tvLoc = findViewById(R.id.tv_loc)
        ivDelRemindTime = findViewById(R.id.iv_del_remind_time)
    }

    override fun onClick(v: View) {
        if (v === ivDelRemindTime) {
            tvRemindTime!!.text = getString(R.string.hint_time_string)
            tbTodoBean!!.todoRemind = 0
            tbTodoBean!!.todoRemindTime = -1
            tbTodoBean!!.todoRemindTimeStr = null
            tbTodoBean!!.todoRemindDate = -1
            tbTodoBean!!.todoRemindDateStr = null
            timeStringBuilder!!.delete(0, timeStringBuilder!!.length)
            ivDelRemindTime!!.visibility = View.GONE
            isAlterRemindDate = true
        } else if (v === tvRemindTime) {
            DateTool.showDateDialog(this@TodoEditActivity) { year, monthOfYear, dayOfMonth ->
                isAlterRemindDate = true
                tbTodoBean!!.todoRemind = 1
                timeStringBuilder!!.delete(0, timeStringBuilder!!.length)
                val dateBuffer = StringBuffer()
                dateBuffer.append(year).append("-").append(if (monthOfYear < 10) "0$monthOfYear" else monthOfYear).append("-").append(if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth)
                timeStringBuilder!!.append(dateBuffer.toString())
                tvRemindTime!!.text = timeStringBuilder!!.toString()
                ivDelRemindTime!!.visibility = View.VISIBLE
                tbTodoBean!!.todoRemindDateStr = dateBuffer.toString()
                tbTodoBean!!.todoRemindDate = DateTool.getStringToDate(dateBuffer.toString(), "yyyy-MM-dd")
                tbTodoBean!!.todoRemindTimeStr = dateBuffer.toString()
                tbTodoBean!!.todoRemindTime = DateTool.getStringToDate(dateBuffer.toString(), "yyyy-MM-dd")
                DateTool.showTimeDialog(this@TodoEditActivity) { hourOfDay, minute ->
                    val timeBuffer = StringBuffer()
                    timeBuffer.append(if (hourOfDay < 10) "0$hourOfDay" else hourOfDay).append(":").append(if (minute < 10) "0$minute" else minute)
                    timeStringBuilder!!.append("   ").append(timeBuffer.toString())
                    tvRemindTime!!.text = timeStringBuilder!!.toString()
                    tbTodoBean!!.todoRemindTimeStr = timeBuffer.toString()
                    tbTodoBean!!.todoRemindTime = DateTool.getStringToDate(timeStringBuilder!!.toString(), "yyyy-MM-dd HH:mm")
                }
            }
        }
    }

    companion object {


        fun startActivity(context: Context) {
            context.startActivity(Intent(context, TodoEditActivity::class.java))
        }

        fun startActivity(context: Context, tbTodoBean: TBTodoBean?) {
            val intent = Intent(context, TodoEditActivity::class.java)
            if (tbTodoBean != null) {
                intent.putExtra("todo", tbTodoBean)
            }
            context.startActivity(intent)
        }
    }
}

