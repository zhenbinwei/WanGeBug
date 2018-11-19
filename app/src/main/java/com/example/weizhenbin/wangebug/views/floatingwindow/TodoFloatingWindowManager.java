package com.example.weizhenbin.wangebug.views.floatingwindow;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.App;
import com.example.weizhenbin.wangebug.base.AppStatusListener;
import com.example.weizhenbin.wangebug.modules.todo.controllers.TodoController;
import com.example.weizhenbin.wangebug.modules.todo.entity.TBTodoBean;
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoEditActivity;
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoListActivity;
import com.example.weizhenbin.wangebug.tools.DateTool;
import com.example.weizhenbin.wangebug.tools.UUIDTool;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/9/17.
 */

public class TodoFloatingWindowManager extends AppStatusListener implements View.OnClickListener{

    private static TodoFloatingWindowManager todoFloatingWindowManager;

    private FloatingWindow floatingWindow;

    private EditText edTitle;
    private EditText edContent;
    private TextView tvReset;
    private TextView tvSave;

    private TBTodoBean tbTodoBean;

    private List<String> filterActivitys=new ArrayList<>();

    private void assignViews(View view) {
        edTitle =view.findViewById(R.id.ed_title);
        edContent = view.findViewById(R.id.ed_content);
        tvReset = view. findViewById(R.id.tv_reset);
        tvSave = view. findViewById(R.id.tv_save);
    }


    private TodoFloatingWindowManager() {
        floatingWindow=new FloatingWindow();
        App.Companion.getApp().addAppStatusListener(this);
        View view=View.inflate(App.Companion.getApp().getApplicationContext(), R.layout.floating_window_todo_edit_view,null);
        floatingWindow.addRealContentView(view);
        tbTodoBean=new TBTodoBean();
        assignViews(view);
        initEvent();
        addFilterActivity(TodoListActivity.class);
        addFilterActivity(TodoEditActivity.class);
    }

    private void initEvent() {
        tvReset.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tbTodoBean.setTodoContent(s.toString());
            }
        });
        edTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tbTodoBean.setTodoTitle(s.toString());
            }
        });
    }

    public static TodoFloatingWindowManager getManager() {
        if (todoFloatingWindowManager == null) {
            synchronized (TodoFloatingWindowManager.class) {
                if (todoFloatingWindowManager == null) {
                    todoFloatingWindowManager = new TodoFloatingWindowManager();
                }
            }
        }
        return todoFloatingWindowManager;
    }

    public void showFloatingWindow(){
        if (floatingWindow!=null) {
            floatingWindow.addFloatingWindow();
        }
    }
    public void hideFloatingWindow(){
        if (floatingWindow!=null) {
            floatingWindow.removeFloatingWindow();
        }
    }

    public void cleanFloatingWindow(){
        if (floatingWindow!=null){
            floatingWindow.removeFloatingWindow();
            floatingWindow=null;
        }
        todoFloatingWindowManager=null;
    }

    @Override
    public void onAppForeground() {
        if (floatingWindow!=null&& PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW,false)){
            floatingWindow.addFloatingWindow();
        }
    }

    @Override
    public void onAppBackground() {
        if (floatingWindow!=null){
            floatingWindow.removeFloatingWindow();
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        super.onActivityStarted(activity);
        if (filterActivitys.contains(activity.getClass().getSimpleName())){
            if (floatingWindow!=null){
                floatingWindow.removeFloatingWindow();
            }
        } else {
            if (floatingWindow!=null&& PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW,false)){
                floatingWindow.addFloatingWindow();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v==tvReset){
            edContent.setText("");
            edTitle.setText("");
            tbTodoBean.reset();
        }else if (v==tvSave){
            if (tbTodoBean.isEmpty()){
                floatingWindow.zoomOutWindow();
                return;
            }
            long todoCreateTime=System.currentTimeMillis();
            tbTodoBean.setUuid(UUIDTool.getUUID());
            tbTodoBean.setTodoCreateTime(todoCreateTime);
            tbTodoBean.setTodoCreateTimeStr(DateTool.getDateToString(todoCreateTime,"yyyy-MM-dd HH:mm"));
            TodoController.saveTodo(tbTodoBean);

            edContent.setText("");
            edTitle.setText("");

            tbTodoBean.reset();

            floatingWindow.zoomOutWindow();
        }
    }

    public void addFilterActivity(Class<? extends Activity> activity){
        if (activity==null){
            return;
        }
        String activityName=activity.getSimpleName();
        if (!filterActivitys.contains(activityName)){
            filterActivitys.add(activityName);
        }
    }
    public void removeFilterActivity(Class<? extends Activity> activity){
        if (activity==null){
            return;
        }
        String activityName=activity.getSimpleName();
        if (filterActivitys.contains(activityName)){
            filterActivitys.remove(activityName);
        }
    }
}
