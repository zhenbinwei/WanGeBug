package com.example.weizhenbin.wangebug.modules.todo.controllers;

import com.example.weizhenbin.wangebug.dp.db.BaseDao;
import com.example.weizhenbin.wangebug.dp.db.BaseDaoFactory;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean;

import java.util.List;

/**
 * Created by weizhenbin on 18/9/8.
 */

public class TodoController {



    /***
     * 存储todo
     * */
    public static long saveTodo(TBTodoBean TBTodoBean){

        BaseDao baseDao= BaseDaoFactory.getOurInstance().getBaseDao(BaseDao.class,TBTodoBean.class);
        return  baseDao.insert(TBTodoBean);

    }


    public static List<TBTodoBean> getTodoList(TBTodoBean where){
        BaseDao baseDao= BaseDaoFactory.getOurInstance().getBaseDao(BaseDao.class,TBTodoBean.class);
        TBTodoBean TBTodoBean =new TBTodoBean();
        return baseDao.query(where);
    }







}
