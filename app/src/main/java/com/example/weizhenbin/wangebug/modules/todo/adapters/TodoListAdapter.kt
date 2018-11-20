package com.example.weizhenbin.wangebug.modules.todo.adapters

import android.content.Context
import android.text.TextUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler
import com.example.weizhenbin.wangebug.eventbus.EventCode
import com.example.weizhenbin.wangebug.eventbus.MessageEvent
import com.example.weizhenbin.wangebug.modules.todo.controllers.TodoController
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoDetailActivity
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoEditActivity
import com.example.weizhenbin.wangebug.tools.DateTool
import com.example.weizhenbin.wangebug.views.ListPopupWindow
import com.example.weizhenbin.wangebug.views.ListShortcutActionLayout

/**
 * Created by weizhenbin on 18/9/9.
 */

class TodoListAdapter(mContext: Context, data: List<TBTodoBean>?, todoStatus: Int) : BaseSimpleAdapter<TBTodoBean, BaseViewHolder>(mContext, R.layout.todo_list_item, data) {

    init {
        onItemChildClickListener = OnItemChildClickListener { _, _, position ->
            if (data != null) {
                TodoDetailActivity.startActivity(mContext, data[position].uuid!!)
            }
        }
        onItemChildLongClickListener = OnItemChildLongClickListener { _, view, position ->
            val items: Array<String>
            if (todoStatus == -1 || todoStatus == 0) {
                items = arrayOf(mContext.getString(R.string.done_string), mContext.getString(R.string.edit_string), mContext.getString(R.string.del_string))

                ListShortcutActionLayout.Builder(mContext).setItems(items).setAnchor(view).setiItemListener(ListPopupWindow.IItemListener { which ->
                    if (data == null) {
                        return@IItemListener
                    }
                    val todoBean = data[position]
                    when (which) {
                        0 -> {
                            todoBean.isDone=true
                            val doneTime = System.currentTimeMillis()
                            todoBean.todoDoneTime = doneTime
                            todoBean.todoDoneTimeStr = DateTool.getDateToString(doneTime, "yyyy-MM-dd HH:mm")
                            TodoController.updateTodoByUuid(todoBean, todoBean.uuid!!)
                            EventBusHandler.post(MessageEvent(EventCode.DONE_TODO_CODE, data[position]))
                        }
                        1 -> TodoEditActivity.startActivity(mContext, todoBean)
                        2 -> {
                            if (todoBean.todoRemind == 1) {
                                TodoController.cancelAlarm(mContext, todoBean.uuid!!)
                            }
                            TodoController.delTodo(todoBean.uuid!!)
                            EventBusHandler.post(MessageEvent(EventCode.DEL_TODO_CODE, todoBean))
                        }
                    }
                }).build().show()
            } else {
                items = arrayOf(mContext.getString(R.string.del_string), mContext.getString(R.string.edit_string))


                ListShortcutActionLayout.Builder(mContext).setItems(items).setAnchor(view).setiItemListener(ListPopupWindow.IItemListener { which ->
                    if (data == null) {
                        return@IItemListener
                    }
                    val todoBean = data[position]
                    when (which) {
                        0 -> {
                            if (todoBean.todoRemind == 1) {
                                TodoController.cancelAlarm(mContext, todoBean.uuid!!)
                            }
                            TodoController.delTodo(todoBean.uuid!!)
                            EventBusHandler.post(MessageEvent(EventCode.DEL_TODO_CODE, data[position]))
                        }
                        1 -> TodoEditActivity.startActivity(mContext, todoBean)
                    }
                }).build().show()


            }


            true
        }
    }

    override fun convert(helper: BaseViewHolder, item: TBTodoBean) {
        helper.setText(R.id.tv_create_time, item.todoCreateTimeStr)

        if (TextUtils.isEmpty(item.todoTitle)) {
            helper.setText(R.id.tv_title, "-- --")
        } else {
            helper.setText(R.id.tv_title, item.todoTitle)
        }
        if (TextUtils.isEmpty(item.todoContent)) {
            helper.setText(R.id.tv_content, "-- --")
        } else {
            helper.setText(R.id.tv_content, item.todoContent)
        }
        if (TextUtils.isEmpty(item.todoDoneTimeStr)) {
            helper.setGone(R.id.tv_done_time, false)
        } else {
            helper.setGone(R.id.tv_done_time, true)
            helper.setText(R.id.tv_done_time, item.todoDoneTimeStr)
        }
        if (item.todoRemind == 0) {
            helper.setGone(R.id.tv_remind_time, false)
        } else {
            helper.setGone(R.id.tv_remind_time, true)
            helper.setText(R.id.tv_remind_time, item.todoRemindDateStr + " " + item.todoRemindTimeStr)
        }
        helper.addOnClickListener(R.id.ll_item)
        helper.addOnLongClickListener(R.id.ll_item)
    }
}
