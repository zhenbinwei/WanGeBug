package com.example.weizhenbin.wangebug.modules.todo.entity;

import android.text.TextUtils;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by weizhenbin on 2018/9/5.
 * 使用greenRobot 开源的 ObjectBox 框架
 */
@Entity
public class TBTodoBean implements Serializable{

    @Id
    long id;


    private String uuid;//唯一设别码


    //事件时间
    private long todoCreateTime;
    private String todoCreateTimeStr;
    //事件标题
    private String todoTitle;
    //事件内容
    private String todoContent;
    //事件状态
    private int todoStatus;  //0未完成  1 已完成
    //事件完成时间
    private long todoDoneTime;
    private String todoDoneTimeStr;
    //事件是否提醒
    private int isTodoRemind;//0 代表false 1代表true
    //事件提醒时间
    private long todoRemindTime;
    private String todoRemindTimeStr;

    //事件提醒日期
    private long todoRemindDate;
    private String todoRemindDateStr;


    private long todoLastUpdateDate;
    private String todoLastUpdateDateStr;


    public TBTodoBean() {
    }

    public TBTodoBean(long id, String uuid, Long todoCreateTime, String todoCreateTimeStr, String todoTitle, String todoContent, Integer todoStatus, Long todoDoneTime, String todoDoneTimeStr, Integer isTodoRemind, Long todoRemindTime, String todoRemindTimeStr, Long todoRemindDate, String todoRemindDateStr, Long todoLastUpdateDate, String todoLastUpdateDateStr) {
        this.id = id;
        this.uuid = uuid;
        this.todoCreateTime = todoCreateTime;
        this.todoCreateTimeStr = todoCreateTimeStr;
        this.todoTitle = todoTitle;
        this.todoContent = todoContent;
        this.todoStatus = todoStatus;
        this.todoDoneTime = todoDoneTime;
        this.todoDoneTimeStr = todoDoneTimeStr;
        this.isTodoRemind = isTodoRemind;
        this.todoRemindTime = todoRemindTime;
        this.todoRemindTimeStr = todoRemindTimeStr;
        this.todoRemindDate = todoRemindDate;
        this.todoRemindDateStr = todoRemindDateStr;
        this.todoLastUpdateDate = todoLastUpdateDate;
        this.todoLastUpdateDateStr = todoLastUpdateDateStr;
    }



    public void setIsDone(boolean isDone){
        if (isDone){
            todoStatus=1;
        }else {
            todoStatus=0;
        }
    }


    public boolean isDone(){
        return todoStatus==1;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getTodoCreateTime() {
        return todoCreateTime;
    }

    public void setTodoCreateTime(long todoCreateTime) {
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

    public int getIsTodoRemind() {
        return isTodoRemind;
    }

    public void setIsTodoRemind(int isTodoRemind) {
        this.isTodoRemind = isTodoRemind;
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

    public long getTodoRemindDate() {
        return todoRemindDate;
    }

    public void setTodoRemindDate(long todoRemindDate) {
        this.todoRemindDate = todoRemindDate;
    }

    public String getTodoRemindDateStr() {
        return todoRemindDateStr;
    }

    public void setTodoRemindDateStr(String todoRemindDateStr) {
        this.todoRemindDateStr = todoRemindDateStr;
    }

    public long getTodoLastUpdateDate() {
        return todoLastUpdateDate;
    }

    public void setTodoLastUpdateDate(long todoLastUpdateDate) {
        this.todoLastUpdateDate = todoLastUpdateDate;
    }

    public String getTodoLastUpdateDateStr() {
        return todoLastUpdateDateStr;
    }

    public void setTodoLastUpdateDateStr(String todoLastUpdateDateStr) {
        this.todoLastUpdateDateStr = todoLastUpdateDateStr;
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
        todoCreateTime=-1;
        todoStatus=-1;
        todoDoneTime=-1;
        todoRemindDate=-1;
        isTodoRemind=-1;
        todoRemindTime=-1;
    }


    public void update(TBTodoBean todoBean){
        if (todoBean==null){
            return;
        }
        this.id=todoBean.id;
        this.isTodoRemind=todoBean.isTodoRemind;
        this.todoContent=todoBean.todoContent;
        this.todoCreateTime=todoBean.todoCreateTime;
        this.todoCreateTimeStr=todoBean.todoCreateTimeStr;
        this.todoDoneTime=todoBean.todoDoneTime;
        this.todoDoneTimeStr=todoBean.todoDoneTimeStr;
        this.todoLastUpdateDate=todoBean.todoLastUpdateDate;
        this.todoLastUpdateDateStr=todoBean.todoLastUpdateDateStr;
        this.todoRemindDate=todoBean.todoRemindDate;
        this.todoRemindDateStr=todoBean.todoRemindDateStr;
        this.todoRemindTime=todoBean.todoRemindTime;
        this.todoRemindTimeStr=todoBean.todoRemindTimeStr;
        this.todoStatus=todoBean.todoStatus;
        this.todoTitle=todoBean.todoTitle;
        this.uuid=todoBean.uuid;
    }

}
