package com.example.weizhenbin.wangebug.views.floatingwindow

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.App
import com.example.weizhenbin.wangebug.base.AppStatusListener
import com.example.weizhenbin.wangebug.modules.todo.controllers.TodoController
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoEditActivity
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoListActivity
import com.example.weizhenbin.wangebug.tools.DateTool
import com.example.weizhenbin.wangebug.tools.UUIDTool
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool
import java.util.*

/**
 * Created by weizhenbin on 2018/9/17.
 */

class TodoFloatingWindowManager private constructor(): AppStatusListener(), View.OnClickListener {

    private var floatingWindow: FloatingWindow? = null

    private var edTitle: EditText? = null
    private var edContent: EditText? = null
    private var tvReset: TextView? = null
    private var tvSave: TextView? = null

    private val tbTodoBean: TBTodoBean

    private val filterActivitys = ArrayList<String>()

    private fun assignViews(view: View) {
        edTitle = view.findViewById(R.id.ed_title)
        edContent = view.findViewById(R.id.ed_content)
        tvReset = view.findViewById(R.id.tv_reset)
        tvSave = view.findViewById(R.id.tv_save)
    }


    init {
        floatingWindow = FloatingWindow()
        App.app.addAppStatusListener(this)
        val view = View.inflate(App.app.applicationContext, R.layout.floating_window_todo_edit_view, null)
        floatingWindow!!.addRealContentView(view)
        tbTodoBean = TBTodoBean()
        assignViews(view)
        initEvent()
        addFilterActivity(TodoListActivity::class.java)
        addFilterActivity(TodoEditActivity::class.java)
    }

    private fun initEvent() {
        tvReset!!.setOnClickListener(this)
        tvSave!!.setOnClickListener(this)
        edContent!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                tbTodoBean.todoContent = s.toString()
            }
        })
        edTitle!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                tbTodoBean.todoTitle = s.toString()
            }
        })
    }

    fun showFloatingWindow() {
        if (floatingWindow != null) {
            floatingWindow!!.addFloatingWindow()
        }
    }

    fun hideFloatingWindow() {
        if (floatingWindow != null) {
            floatingWindow!!.removeFloatingWindow()
        }
    }

    fun cleanFloatingWindow() {
        if (floatingWindow != null) {
            floatingWindow!!.removeFloatingWindow()
            floatingWindow = null
        }
    }

    override fun onAppForeground() {
        if (floatingWindow != null && PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, false)) {
            floatingWindow!!.addFloatingWindow()
        }
    }

    override fun onAppBackground() {
        if (floatingWindow != null) {
            floatingWindow!!.removeFloatingWindow()
        }
    }

    override fun onActivityStarted(activity: Activity?) {
        super.onActivityStarted(activity)
        if (filterActivitys.contains(activity!!.javaClass.simpleName)) {
            if (floatingWindow != null) {
                floatingWindow!!.removeFloatingWindow()
            }
        } else {
            if (floatingWindow != null && PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, false)) {
                floatingWindow!!.addFloatingWindow()
            }
        }
    }

    override fun onClick(v: View) {
        if (v === tvReset) {
            edContent!!.setText("")
            edTitle!!.setText("")
            tbTodoBean.reset()
        } else if (v === tvSave) {
            if (tbTodoBean.isEmpty) {
                floatingWindow!!.zoomOutWindow()
                return
            }
            val todoCreateTime = System.currentTimeMillis()
            tbTodoBean.uuid = UUIDTool.uuid
            tbTodoBean.todoCreateTime = todoCreateTime
            tbTodoBean.todoCreateTimeStr = DateTool.getDateToString(todoCreateTime, "yyyy-MM-dd HH:mm")
            TodoController.saveTodo(tbTodoBean)

            edContent!!.setText("")
            edTitle!!.setText("")

            tbTodoBean.reset()

            floatingWindow!!.zoomOutWindow()
        }
    }

    fun addFilterActivity(activity: Class<out Activity>?) {
        if (activity == null) {
            return
        }
        val activityName = activity.simpleName
        if (!filterActivitys.contains(activityName)) {
            filterActivitys.add(activityName)
        }
    }

    fun removeFilterActivity(activity: Class<out Activity>?) {
        if (activity == null) {
            return
        }
        val activityName = activity.simpleName
        if (filterActivitys.contains(activityName)) {
            filterActivitys.remove(activityName)
        }
    }
    private object SingletonHolder {
        val holder= TodoFloatingWindowManager()
    }
    companion object {

        val manager = SingletonHolder.holder
    }
}
