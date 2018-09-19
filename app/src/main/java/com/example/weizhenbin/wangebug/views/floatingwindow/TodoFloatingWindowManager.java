package com.example.weizhenbin.wangebug.views.floatingwindow;

import android.util.Log;
import android.view.View;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.App;
import com.example.weizhenbin.wangebug.base.AppStatusListener;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig;
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool;

/**
 * Created by weizhenbin on 2018/9/17.
 */

public class TodoFloatingWindowManager extends AppStatusListener {

    private static TodoFloatingWindowManager todoFloatingWindowManager;

    private FloatingWindow floatingWindow;

    private TodoFloatingWindowManager() {
        floatingWindow=new FloatingWindow();
        App.app.addAppStatusListener(this);
        View view=View.inflate(App.app.getApplicationContext(), R.layout.floating_window_todo_edit_view,null);
        floatingWindow.addRealContentView(view);
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
    protected void onAppForeground() {
        Log.d("TodoFloatingWindowManag", "onAppForeground");
        if (floatingWindow!=null&& PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_FLOATTING_WINDOW,false)){
            floatingWindow.addFloatingWindow();
        }
    }

    @Override
    protected void onAppBackground() {
        Log.d("TodoFloatingWindowManag", "onAppBackground");
        if (floatingWindow!=null){
            floatingWindow.removeFloatingWindow();
        }
    }

}
