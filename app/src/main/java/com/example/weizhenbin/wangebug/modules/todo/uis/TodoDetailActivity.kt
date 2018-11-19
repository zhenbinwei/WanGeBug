package com.example.weizhenbin.wangebug.modules.todo.uis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseActivity
import com.example.weizhenbin.wangebug.modules.todo.controllers.TodoController
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean
import com.example.weizhenbin.wangebug.views.TitleBar

/**
 * Created by weizhenbin on 2018/9/21.
 */

class TodoDetailActivity : BaseActivity() {
    private var tbTitle: TitleBar? = null
    private var tvDoneTime: TextView? = null
    private var tvTitle: TextView? = null
    private var tvContent: TextView? = null


    private var tbTodoBean: TBTodoBean? = null


    private fun initViews() {
        tbTitle = findViewById(R.id.tb_title)
        tvDoneTime = findViewById(R.id.tv_done_time)
        tvTitle = findViewById(R.id.tv_title)
        tvContent = findViewById(R.id.tv_content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_done)
        initViews()
        val intent = intent
        if (intent.hasExtra("uuid")) {
            tbTodoBean = TodoController.getTodoBean(intent.getStringExtra("uuid"))
        }
        initData()
        initEvent()
    }

    private fun initEvent() {
        tbTitle!!.setLeftOnClickListener { finish() }
    }

    private fun initData() {
        if (tbTodoBean != null) {
            tvTitle!!.text = tbTodoBean!!.todoTitle
            tvContent!!.text = tbTodoBean!!.todoContent
            tvDoneTime!!.text = tbTodoBean!!.todoDoneTimeStr
        }
    }

    companion object {


        fun startActivity(context: Context, uuid: String) {
            val intent = Intent(context, TodoDetailActivity::class.java)
            intent.putExtra("uuid", uuid)
            context.startActivity(intent)
        }
    }
}
