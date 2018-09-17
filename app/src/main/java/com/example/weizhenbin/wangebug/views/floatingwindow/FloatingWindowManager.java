package com.example.weizhenbin.wangebug.views.floatingwindow;

/**
 * Created by weizhenbin on 2018/9/17.
 */

public class FloatingWindowManager {

    private static FloatingWindowManager floatingWindowManager;

    private FloatingWindow floatingWindow;

    private FloatingWindowManager() {
        floatingWindow=new FloatingWindow();
    }

    public static FloatingWindowManager getFloatingWindowManager() {
        if (floatingWindowManager == null) {
            synchronized (FloatingWindowManager.class) {
                if (floatingWindowManager == null) {
                    floatingWindowManager = new FloatingWindowManager();
                }
            }
        }
        return floatingWindowManager;
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
        floatingWindow=null;
    }
}
