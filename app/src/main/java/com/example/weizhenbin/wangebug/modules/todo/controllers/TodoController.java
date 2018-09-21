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


    /**
     * 获取todo列表
     * */
    public static List<TBTodoBean> getTodoList(TBTodoBean where){
        BaseDao baseDao= BaseDaoFactory.getOurInstance().getBaseDao(BaseDao.class,TBTodoBean.class);
        return baseDao.query(where);
    }



    /**
     * 修改todo
     * */
    public static long updateTodo(TBTodoBean todoBean,TBTodoBean where){
        BaseDao baseDao= BaseDaoFactory.getOurInstance().getBaseDao(BaseDao.class,TBTodoBean.class);
        return baseDao.update(todoBean,where);
    }


    /**
     * 删除todo
     * */
     public static int delTodo(TBTodoBean where){
         BaseDao baseDao= BaseDaoFactory.getOurInstance().getBaseDao(BaseDao.class,TBTodoBean.class);
         return baseDao.delete(where);
     }


}
