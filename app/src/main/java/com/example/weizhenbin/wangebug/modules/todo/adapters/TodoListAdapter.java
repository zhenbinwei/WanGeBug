package com.example.weizhenbin.wangebug.modules.todo.adapters;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.modules.todo.controllers.TodoController;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean;
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoDoneActivity;
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoEditActivity;
import com.example.weizhenbin.wangebug.tools.DateTool;
import com.example.weizhenbin.wangebug.tools.DialogTool;

import java.util.List;

/**
 * Created by weizhenbin on 18/9/9.
 */

public class TodoListAdapter extends BaseQuickAdapter<TBTodoBean,BaseViewHolder> {

    public TodoListAdapter(@Nullable final List<TBTodoBean> data) {
        super(R.layout.todo_list_item,data);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d(TAG, "点击了");
                if (data!=null) {
                    if (data.get(position).isDone()){
                        TodoDoneActivity.startActicity(mContext,data.get(position));
                    }else {
                        TodoEditActivity.startActivity(mContext, data.get(position));
                    }
                }
            }
        });
        setOnItemChildLongClickListener(new OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, final int position) {
                DialogTool.showListAlertDialog(mContext, new String[]{mContext.getString(R.string.done_string),mContext.getString(R.string.del_string)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                           switch (which){
                               case 0:
                                   if (data==null){
                                       return;
                                   }
                                   TBTodoBean updateWhere=new TBTodoBean();
                                   TBTodoBean todoBean=data.get(position);
                                   todoBean.setIsDone(true);
                                   long doneTime=System.currentTimeMillis();
                                   todoBean.setTodoDoneTime(doneTime);
                                   todoBean.setTodoDoneTimeStr(DateTool.getDateToString(doneTime, "yyyy-MM-dd HH:mm"));
                                   updateWhere.setUuid(data.get(position).getUuid());
                                   TodoController.updateTodo(data.get(position),updateWhere);
                                   break;
                               case 1:
                                   if (data==null){
                                       return;
                                   }
                                   TBTodoBean delWhere=new TBTodoBean();
                                   delWhere.setUuid(data.get(position).getUuid());
                                   TodoController.delTodo(delWhere);
                                   break;
                           }
                    }
                });
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
        if (item.getIsTodoRemind()==null||item.getIsTodoRemind()==0){
            helper.setGone(R.id.tv_remind_time,false);
        }else {
            helper.setGone(R.id.tv_remind_time,true);
            helper.setText(R.id.tv_remind_time, item.getTodoRemindTimeStr());
        }
        helper.addOnClickListener(R.id.ll_item);
        helper.addOnLongClickListener(R.id.ll_item);
    }
}
