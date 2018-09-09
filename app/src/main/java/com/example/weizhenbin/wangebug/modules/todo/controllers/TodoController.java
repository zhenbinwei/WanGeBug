package com.example.weizhenbin.wangebug.modules.todo.controllers;

import com.example.weizhenbin.wangebug.dp.db.BaseDao;
import com.example.weizhenbin.wangebug.dp.db.BaseDaoFactory;
import com.example.weizhenbin.wangebug.modules.todo.entity.TodoBean;

import java.util.List;

/**
 * Created by weizhenbin on 18/9/8.
 */

public class TodoController {



    /***
     * 存储todo
     * */
    public static long saveTodo(TodoBean todoBean){

        BaseDao baseDao= BaseDaoFactory.getOurInstance().getBaseDao(BaseDao.class,TodoBean.class);
        return  baseDao.insert(todoBean);

    }


    public static List<TodoBean> getTodoList(TodoBean where){
        BaseDao baseDao= BaseDaoFactory.getOurInstance().getBaseDao(BaseDao.class,TodoBean.class);
        TodoBean todoBean=new TodoBean();
        return baseDao.query(where);
    }







}
