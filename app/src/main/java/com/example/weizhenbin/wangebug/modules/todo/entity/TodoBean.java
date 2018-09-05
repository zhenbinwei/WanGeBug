package com.example.weizhenbin.wangebug.modules.todo.entity;

/**
 * Created by weizhenbin on 2018/9/5.
 */

public class TodoBean {
    //事件时间
    private long todoTime;
    private String todoTimeStr;
    //事件标题
    private String todoTitle;
    //事件内容
    private String todoContent;
    //事件状态
    private int todoStatus;
    //事件完成时间
    private long todoDoneTime;
    private String todoDoneTimeStr;
    //事件是否提醒
    private boolean isTodoRemind;
    //事件提醒时间
    private long todoRemindTime;
    private String todoRemindTimeStr;

    public long getTodoTime() {
        return todoTime;
    }

    public void setTodoTime(long todoTime) {
        this.todoTime = todoTime;
    }

    public String getTodoTimeStr() {
        return todoTimeStr;
    }

    public void setTodoTimeStr(String todoTimeStr) {
        this.todoTimeStr = todoTimeStr;
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

    public int getTodoStatus() {
        return todoStatus;
    }

    public void setTodoStatus(int todoStatus) {
        this.todoStatus = todoStatus;
    }

    public long getTodoDoneTime() {
        return todoDoneTime;
    }

    public void setTodoDoneTime(long todoDoneTime) {
        this.todoDoneTime = todoDoneTime;
    }

    public String getTodoDoneTimeStr() {
        return todoDoneTimeStr;
    }

    public void setTodoDoneTimeStr(String todoDoneTimeStr) {
        this.todoDoneTimeStr = todoDoneTimeStr;
    }

    public boolean isTodoRemind() {
        return isTodoRemind;
    }

    public void setTodoRemind(boolean todoRemind) {
        isTodoRemind = todoRemind;
    }

    public long getTodoRemindTime() {
        return todoRemindTime;
    }

    public void setTodoRemindTime(long todoRemindTime) {
        this.todoRemindTime = todoRemindTime;
    }

    public String getTodoRemindTimeStr() {
        return todoRemindTimeStr;
    }

    public void setTodoRemindTimeStr(String todoRemindTimeStr) {
        this.todoRemindTimeStr = todoRemindTimeStr;
    }
}
