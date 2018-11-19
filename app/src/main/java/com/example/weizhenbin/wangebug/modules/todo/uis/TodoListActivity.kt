package com.example.weizhenbin.wangebug.modules.todo.uis

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatImageView
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseActivity
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.ViewPageAdapter
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler
import com.example.weizhenbin.wangebug.eventbus.EventCode
import com.example.weizhenbin.wangebug.eventbus.MessageEvent
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean
import com.example.weizhenbin.wangebug.views.TitleBar
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * Created by weizhenbin on 18/9/8.
 */

class TodoListActivity : BaseActivity() {
     var pageAdapter: ViewPageAdapter? = null
     var fragments: MutableList<BaseFragment> = ArrayList()
    lateinit var vpTodo: ViewPager
    lateinit var tlTodoStatus: TabLayout
    lateinit var ivAdd: AppCompatImageView
    lateinit var tbTitle: TitleBar
    lateinit var all: TodoListFragment
    lateinit var noDone: TodoListFragment
    lateinit var done: TodoListFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
        initViews()
        if (fragments.isEmpty()) {
            addFragments()
        }
        setData()
        initEvent()
        EventBusHandler.register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.code == EventCode.ADD_TODO_CODE) {
            if (event.msg is TBTodoBean) {
                all.addTodo(event.msg as TBTodoBean)
                noDone.addTodo(event.msg as TBTodoBean)
            }
        } else if (event.code == EventCode.DEL_TODO_CODE) {
            if (event.msg is TBTodoBean) {
                all.delTodo(event.msg as TBTodoBean)
                noDone.delTodo(event.msg as TBTodoBean)
                done.delTodo(event.msg as TBTodoBean)
            }
        } else if (event.code == EventCode.UPDATE_TODO_CODE) {
            if (event.msg is TBTodoBean) {
                all.updateTodo(event.msg as TBTodoBean)
                noDone.updateTodo(event.msg as TBTodoBean)
                done.updateTodo(event.msg as TBTodoBean)
            }
        } else if (event.code == EventCode.DONE_TODO_CODE) {
            if (event.msg is TBTodoBean) {
                all.updateTodo(event.msg as TBTodoBean)
                noDone.delTodo(event.msg as TBTodoBean)
                done.addTodo(event.msg as TBTodoBean)
            }
        }
    }


    private fun initEvent() {
        ivAdd.setOnClickListener { TodoEditActivity.startActivity(this@TodoListActivity) }
        tbTitle.setLeftOnClickListener { finish() }
    }

    private fun setData() {
        pageAdapter = ViewPageAdapter(supportFragmentManager, fragments)
        vpTodo.adapter = pageAdapter
    }

    private fun addFragments() {
        fragments.add( TodoListFragment.getFragment(-1))
        fragments.add( TodoListFragment.getFragment(0))
        fragments.add( TodoListFragment.getFragment(1))
    }

    private fun initViews() {
        vpTodo = findViewById(R.id.vp_todo)
        tlTodoStatus = findViewById(R.id.tl_todo_status)
        ivAdd = findViewById(R.id.iv_add)
        tbTitle = findViewById(R.id.tb_title)
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBusHandler.unregister(this)
    }

    companion object {

        fun startActivity(context: Context) {
            context.startActivity(Intent(context, TodoListActivity::class.java))
        }
    }
}
