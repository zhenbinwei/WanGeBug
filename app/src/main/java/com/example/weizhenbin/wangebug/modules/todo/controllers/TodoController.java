package com.example.weizhenbin.wangebug.modules.todo.controllers;

import com.example.weizhenbin.wangebug.base.App;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean_;

import java.util.List;

import io.objectbox.Box;

/**
 * Created by weizhenbin on 18/9/8.
 */

public class TodoController {



    /***
     * 存储todo
     * */
    public static long saveTodo(TBTodoBean tbTodoBean){
        Box<TBTodoBean> todoBeanBox = App.app.getBoxStore().boxFor(TBTodoBean.class);
       return todoBeanBox.put(tbTodoBean);

    }


    /**
     * 获取todo列表
     * */
    public static List<TBTodoBean> getTodoList(int todoStatus,int page){
     return  getTodoList(todoStatus,page,20);
    }

    /**
     * 获取todo列表
     * */
    public static List<TBTodoBean> getTodoList(int todoStatus,int page,int pageCount){
        Box<TBTodoBean> todoBeanBox = App.app.getBoxStore().boxFor(TBTodoBean.class);
        if (todoStatus==-1){
            return todoBeanBox.query().orderDesc(TBTodoBean_.id).build().find((page-1)*pageCount,pageCount);
        }else {
            return todoBeanBox.query().orderDesc(TBTodoBean_.id).equal(TBTodoBean_.todoStatus, todoStatus).build().find();
        }
    }

    /**
     * 修改todo
     * */
    public static long updateTodoByUuid(TBTodoBean todoBean,String uuid){
        Box<TBTodoBean> todoBeanBox = App.app.getBoxStore().boxFor(TBTodoBean.class);
        TBTodoBean tbTodoBean= todoBeanBox.query().equal(TBTodoBean_.uuid,uuid).build().findFirst();
        if (tbTodoBean!=null) {
            tbTodoBean.update(todoBean);
          return   todoBeanBox.put(tbTodoBean);
        }
        return -1;
    }





    /**
     * 删除todo
     * */
     public static long delTodo(String uuid){
         Box<TBTodoBean> todoBeanBox = App.app.getBoxStore().boxFor(TBTodoBean.class);
        return todoBeanBox.query().equal(TBTodoBean_.uuid,uuid).build().remove();
     }


}
