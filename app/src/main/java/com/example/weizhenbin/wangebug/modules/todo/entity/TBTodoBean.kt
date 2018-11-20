package com.example.weizhenbin.wangebug.modules.todo.entity

import android.text.TextUtils
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.io.Serializable

/**
 * Created by weizhenbin on 2018/9/5.
 * 使用greenRobot 开源的 ObjectBox 框架
 */
@Entity
class TBTodoBean : Serializable {

    @Id
    var id: Long = 0


    var uuid: String? = null//唯一设别码


    //事件时间
    var todoCreateTime: Long = 0
    var todoCreateTimeStr: String? = null
    //事件标题
    var todoTitle: String? = null
    //事件内容
    var todoContent: String? = null
    //事件状态
    var todoStatus: Int = 0  //0未完成  1 已完成
    //事件完成时间
    var todoDoneTime: Long = 0
    var todoDoneTimeStr: String? = null
    //事件是否提醒
    var todoRemind: Int = 0//0 代表false 1代表true
    //事件提醒时间
    var todoRemindTime: Long = 0
    var todoRemindTimeStr: String? = null

    //事件提醒日期
    var todoRemindDate: Long = 0
    var todoRemindDateStr: String? = null


    var todoLastUpdateDate: Long = 0
    var todoLastUpdateDateStr: String? = null


    var isDone: Boolean
        get() = todoStatus == 1
        set(isDone) = if (isDone) {
            todoStatus = 1
        } else {
            todoStatus = 0
        }


    val isEmpty: Boolean
        get() = TextUtils.isEmpty(todoContent) &&
                TextUtils.isEmpty(todoCreateTimeStr) &&
                TextUtils.isEmpty(todoTitle) &&
                TextUtils.isEmpty(todoDoneTimeStr) &&
                TextUtils.isEmpty(todoRemindTimeStr) &&
                TextUtils.isEmpty(todoRemindDateStr)


    constructor() {}

    constructor(id: Long, uuid: String, todoCreateTime: Long?, todoCreateTimeStr: String, todoTitle: String, todoContent: String, todoStatus: Int?, todoDoneTime: Long?, todoDoneTimeStr: String, isTodoRemind: Int?, todoRemindTime: Long?, todoRemindTimeStr: String, todoRemindDate: Long?, todoRemindDateStr: String, todoLastUpdateDate: Long?, todoLastUpdateDateStr: String) {
        this.id = id
        this.uuid = uuid
        this.todoCreateTime = todoCreateTime!!
        this.todoCreateTimeStr = todoCreateTimeStr
        this.todoTitle = todoTitle
        this.todoContent = todoContent
        this.todoStatus = todoStatus!!
        this.todoDoneTime = todoDoneTime!!
        this.todoDoneTimeStr = todoDoneTimeStr
        this.todoRemind = isTodoRemind!!
        this.todoRemindTime = todoRemindTime!!
        this.todoRemindTimeStr = todoRemindTimeStr
        this.todoRemindDate = todoRemindDate!!
        this.todoRemindDateStr = todoRemindDateStr
        this.todoLastUpdateDate = todoLastUpdateDate!!
        this.todoLastUpdateDateStr = todoLastUpdateDateStr
    }

    override fun toString(): String {
        return "TBTodoBean{" +
                "todoCreateTime=" + todoCreateTime +
                ", todoCreateTimeStr='" + todoCreateTimeStr + '\''.toString() +
                ", todoTitle='" + todoTitle + '\''.toString() +
                ", todoContent='" + todoContent + '\''.toString() +
                ", todoStatus=" + todoStatus +
                ", todoDoneTime=" + todoDoneTime +
                ", todoDoneTimeStr='" + todoDoneTimeStr + '\''.toString() +
                ", todoRemind=" + todoRemind +
                ", todoRemindTime=" + todoRemindTime +
                ", todoRemindTimeStr='" + todoRemindTimeStr + '\''.toString() +
                ", todoRemindDate=" + todoRemindDate +
                ", todoRemindDateStr='" + todoRemindDateStr + '\''.toString() +
                '}'.toString()
    }

    fun reset() {
        todoContent = null
        todoCreateTimeStr = null
        todoTitle = null
        todoDoneTimeStr = null
        todoRemindTimeStr = null
        todoRemindDateStr = null
        todoCreateTime = -1
        todoStatus = -1
        todoDoneTime = -1
        todoRemindDate = -1
        todoRemind = -1
        todoRemindTime = -1
    }


    fun update(todoBean: TBTodoBean?) {
        if (todoBean === null) {
            return
        }
        this.id = todoBean.id
        this.todoRemind = todoBean.todoRemind
        this.todoContent = todoBean.todoContent
        this.todoCreateTime = todoBean.todoCreateTime
        this.todoCreateTimeStr = todoBean.todoCreateTimeStr
        this.todoDoneTime = todoBean.todoDoneTime
        this.todoDoneTimeStr = todoBean.todoDoneTimeStr
        this.todoLastUpdateDate = todoBean.todoLastUpdateDate
        this.todoLastUpdateDateStr = todoBean.todoLastUpdateDateStr
        this.todoRemindDate = todoBean.todoRemindDate
        this.todoRemindDateStr = todoBean.todoRemindDateStr
        this.todoRemindTime = todoBean.todoRemindTime
        this.todoRemindTimeStr = todoBean.todoRemindTimeStr
        this.todoStatus = todoBean.todoStatus
        this.todoTitle = todoBean.todoTitle
        this.uuid = todoBean.uuid
    }

}
