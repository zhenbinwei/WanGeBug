package com.example.weizhenbin.wangebug.modules.todo.entity;

import android.text.TextUtils;

import com.example.weizhenbin.wangebug.dp.annotation.DbTable;

import java.io.Serializable;

/**
 * Created by weizhenbin on 2018/9/5.
 * 数据库表 基础类型要用对象表示
 *
 */
@DbTable("tb_todo")
public class TBTodoBean implements Serializable{

    private String uuid;//唯一设别码


    //事件时间
    private Long todoCreateTime;
    private String todoCreateTimeStr;
    //事件标题
    private String todoTitle;
    //事件内容
    private String todoContent;
    //事件状态
    private Integer todoStatus;  //0未完成  1 已完成
    //事件完成时间
    private Long todoDoneTime;
    private String todoDoneTimeStr;
    //事件是否提醒
    private Integer isTodoRemind;//0 代表false 1代表true
    //事件提醒时间
    private Long todoRemindTime;
    private String todoRemindTimeStr;

    //事件提醒日期
    private Long todoRemindDate;
    private String todoRemindDateStr;

    public Long getTodoCreateTime() {
        return todoCreateTime;
    }

    public void setTodoCreateTime(Long todoCreateTime) {
        this.todoCreateTime = todoCreateTime;
    }

    public String getTodoCreateTimeStr() {
        return todoCreateTimeStr;
    }

    public void setTodoCreateTimeStr(String todoCreateTimeStr) {
        this.todoCreateTimeStr = todoCreateTimeStr;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getTodoContent() {
        return todoContent;
    }

    public void setTodoContent(String todoContent) {
        this.todoContent = todoContent;
    }

    public Integer getTodoStatus() {
        return todoStatus;
    }

    public void setTodoStatus(Integer todoStatus) {
        this.todoStatus = todoStatus;
    }

    public Long getTodoDoneTime() {
        return todoDoneTime;
    }

    public void setTodoDoneTime(Long todoDoneTime) {
        this.todoDoneTime = todoDoneTime;
    }

    public String getTodoDoneTimeStr() {
        return todoDoneTimeStr;
    }

    public void setTodoDoneTimeStr(String todoDoneTimeStr) {
        this.todoDoneTimeStr = todoDoneTimeStr;
    }

    public Integer getIsTodoRemind() {
        return isTodoRemind;
    }

    public void setIsTodoRemind(Integer isTodoRemind) {
        this.isTodoRemind = isTodoRemind;
    }

    public Long getTodoRemindTime() {
        return todoRemindTime;
    }

    public void setTodoRemindTime(Long todoRemindTime) {
        this.todoRemindTime = todoRemindTime;
    }

    public String getTodoRemindTimeStr() {
        return todoRemindTimeStr;
    }

    public void setTodoRemindTimeStr(String todoRemindTimeStr) {
        this.todoRemindTimeStr = todoRemindTimeStr;
    }

    public Long getTodoRemindDate() {
        return todoRemindDate;
    }

    public void setTodoRemindDate(Long todoRemindDate) {
        this.todoRemindDate = todoRemindDate;
    }

    public String getTodoRemindDateStr() {
        return todoRemindDateStr;
    }

    public void setTodoRemindDateStr(String todoRemindDateStr) {
        this.todoRemindDateStr = todoRemindDateStr;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "TBTodoBean{" +
                "todoCreateTime=" + todoCreateTime +
                ", todoCreateTimeStr='" + todoCreateTimeStr + '\'' +
                ", todoTitle='" + todoTitle + '\'' +
                ", todoContent='" + todoContent + '\'' +
                ", todoStatus=" + todoStatus +
                ", todoDoneTime=" + todoDoneTime +
                ", todoDoneTimeStr='" + todoDoneTimeStr + '\'' +
                ", isTodoRemind=" + isTodoRemind +
                ", todoRemindTime=" + todoRemindTime +
                ", todoRemindTimeStr='" + todoRemindTimeStr + '\'' +
                ", todoRemindDate=" + todoRemindDate +
                ", todoRemindDateStr='" + todoRemindDateStr + '\'' +
                '}';
    }



    public boolean isEmpty(){
        return TextUtils.isEmpty(todoContent)&&
         TextUtils.isEmpty(todoCreateTimeStr)&&
         TextUtils.isEmpty(todoTitle)&&
         TextUtils.isEmpty(todoDoneTimeStr)&&
         TextUtils.isEmpty(todoRemindTimeStr)&&
         TextUtils.isEmpty(todoRemindDateStr);
    }

    public void reset(){
        todoContent=null;
        todoCreateTimeStr=null;
        todoTitle=null;
        todoDoneTimeStr=null;
        todoRemindTimeStr=null;
        todoRemindDateStr=null;
        todoCreateTime=null;
        todoStatus=null;
        todoDoneTime=null;
        todoRemindDate=null;
        isTodoRemind=null;
        todoRemindTime=null;
    }
}
