package com.example.weizhenbin.wangebug.modules.todo.adapters;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.modules.todo.entity.TodoBean;

import java.util.List;

/**
 * Created by weizhenbin on 18/9/9.
 */

public class TodoListAdapter extends BaseQuickAdapter<TodoBean,BaseViewHolder> {

    public TodoListAdapter(@Nullable List<TodoBean> data) {
        super(R.layout.todo_list_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodoBean item) {
        /* helper.setText(R.id.tv_title,item.getTodoTitle());
         helper.setText(R.id.tv_create_time,item.getTodoCreateTimeStr());
         helper.setText(R.id.tv_content,item.getTodoContent());
         helper.setText(R.id.tv_remind_time,item.getTodoRemindTimeStr());
         helper.setText(R.id.tv_done_time,item.getTodoDoneTimeStr());*/
    }
}
