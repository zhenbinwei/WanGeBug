package com.example.weizhenbin.wangebug.modules.todo.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter;
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler;
import com.example.weizhenbin.wangebug.eventbus.EventCode;
import com.example.weizhenbin.wangebug.eventbus.MessageEvent;
import com.example.weizhenbin.wangebug.modules.todo.controllers.TodoController;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean;
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoDetailActivity;
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoEditActivity;
import com.example.weizhenbin.wangebug.tools.DateTool;
import com.example.weizhenbin.wangebug.views.ListPopupWindow;
import com.example.weizhenbin.wangebug.views.ListShortcutActionLayout;

import java.util.List;

/**
 * Created by weizhenbin on 18/9/9.
 */

public class TodoListAdapter extends BaseSimpleAdapter<TBTodoBean,BaseViewHolder> {

    public TodoListAdapter(final Context mContext, @Nullable final List<TBTodoBean> data, final int todoStatus) {
        super(mContext,R.layout.todo_list_item,data);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (data!=null) {
                    TodoDetailActivity.startActivity(mContext,data.get(position).getUuid());
                }
            }
        });
        setOnItemChildLongClickListener(new OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, final int position) {
                String[] items;
                if (todoStatus==-1||todoStatus==0){
                    items=new String[]{mContext.getString(R.string.done_string),mContext.getString(R.string.edit_string),mContext.getString(R.string.del_string)};

                    new ListShortcutActionLayout.Builder(mContext).setItems(items).setAnchor(view).setiItemListener(new ListPopupWindow.IItemListener() {
                        @Override
                        public void onItemClick(int position) {
                            if (data==null){
                                return;
                            }
                            TBTodoBean todoBean=data.get(position);
                            switch (position){
                                case 0:
                                    todoBean.setIsDone(true);
                                    long doneTime=System.currentTimeMillis();
                                    todoBean.setTodoDoneTime(doneTime);
                                    todoBean.setTodoDoneTimeStr(DateTool.getDateToString(doneTime, "yyyy-MM-dd HH:mm"));
                                    TodoController.updateTodoByUuid(todoBean,todoBean.getUuid());
                                    EventBusHandler.post(new MessageEvent(EventCode.DONE_TODO_CODE,data.get(position)));
                                    break;
                                case 1:
                                    TodoEditActivity.startActivity(mContext, todoBean);
                                    break;
                                case 2:
                                    if (todoBean.getIsTodoRemind()==1){
                                        TodoController.cancelAlarm(mContext,todoBean.getUuid());
                                    }
                                    TodoController.delTodo(todoBean.getUuid());
                                    EventBusHandler.post(new MessageEvent(EventCode.DEL_TODO_CODE,todoBean));
                                    break;
                            }
                        }
                    }).build().show();
                }else {
                    items=new String[]{mContext.getString(R.string.del_string),mContext.getString(R.string.edit_string)};


                    new ListShortcutActionLayout.Builder(mContext).setItems(items).setAnchor(view).setiItemListener(new ListPopupWindow.IItemListener() {
                        @Override
                        public void onItemClick(int position) {
                            if (data==null){
                                return;
                            }
                            TBTodoBean todoBean=data.get(position);
                            switch (position){
                                case 0:
                                    if (todoBean.getIsTodoRemind()==1){
                                        TodoController.cancelAlarm(mContext,todoBean.getUuid());
                                    }
                                    TodoController.delTodo(todoBean.getUuid());
                                    EventBusHandler.post(new MessageEvent(EventCode.DEL_TODO_CODE,data.get(position)));
                                    break;
                                case 1:
                                    TodoEditActivity.startActivity(mContext, todoBean);
                                    break;
                            }
                        }
                    }).build().show();


                }


                return true;
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, TBTodoBean item) {
         helper.setText(R.id.tv_create_time,item.getTodoCreateTimeStr());

        if (TextUtils.isEmpty(item.getTodoTitle())){
            helper.setText(R.id.tv_title,"-- --");
        }else {
            helper.setText(R.id.tv_title,item.getTodoTitle());
        }
        if (TextUtils.isEmpty(item.getTodoContent())){
            helper.setText(R.id.tv_content,"-- --");
        }else {
            helper.setText(R.id.tv_content,item.getTodoContent());
        }
        if (TextUtils.isEmpty(item.getTodoDoneTimeStr())){
            helper.setGone(R.id.tv_done_time,false);
        }else {
            helper.setGone(R.id.tv_done_time,true);
            helper.setText(R.id.tv_done_time, item.getTodoDoneTimeStr());
        }
        if (item.getIsTodoRemind()==0){
            helper.setGone(R.id.tv_remind_time,false);
        }else {
            helper.setGone(R.id.tv_remind_time,true);
            helper.setText(R.id.tv_remind_time, item.getTodoRemindDateStr()+" "+item.getTodoRemindTimeStr());
        }
        helper.addOnClickListener(R.id.ll_item);
        helper.addOnLongClickListener(R.id.ll_item);
    }
}
