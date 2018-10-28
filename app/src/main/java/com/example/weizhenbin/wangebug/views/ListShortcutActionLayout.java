package com.example.weizhenbin.wangebug.views;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.example.weizhenbin.wangebug.tools.PhoneTool;
import com.example.weizhenbin.wangebug.tools.TouchTool;

public class ListShortcutActionLayout {
    ListPopupWindow listPopupWindow;
    private Context context;

    private View anchor;

    private String[] items;

    private ListShortcutActionLayout(Builder builder) {
        if (builder==null){
            throw new NullPointerException("builder 不能为空");
        }
        this.context=builder.context;
        this.anchor=builder.anchor;
        this.items=builder.items;
        if (context==null){
            throw new NullPointerException("context 不能为空");
        }
        if (items==null){
            throw new NullPointerException("items 不能为空");
        }
        listPopupWindow =new ListPopupWindow(context,items);
        listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (anchor!=null) {
                    anchor.setSelected(false);
                }
            }
        });
        listPopupWindow.setItemListener(builder.iItemListener);
    }


    public void show(){
        if (anchor==null){
            throw new NullPointerException("anchor 不能为空");
        }
        anchor.setSelected(true);
        if (anchor.getParent()!=null) {
            anchor.getParent().requestDisallowInterceptTouchEvent(true);
        }

        boolean isLeft=TouchTool.downX<PhoneTool.getScreenWidth()/2;
        boolean isTop=TouchTool.downY>listPopupWindow.getHeight();
        int x=isLeft?(int)TouchTool.downX:(int)TouchTool.downX-listPopupWindow.getWidth();
        int y=isTop?(int)TouchTool.downY-listPopupWindow.getHeight():(int) TouchTool.downY;
        listPopupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY,
                x,
                y);
    }



    public static class Builder{

        private Context context;

        private View anchor;

        private String[] items;

        private ListPopupWindow.IItemListener iItemListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setAnchor(View anchor) {
            this.anchor = anchor;
            return this;
        }

        public Builder setItems(String[] items) {
            this.items = items;
            return this;
        }

        public Builder setiItemListener(ListPopupWindow.IItemListener iItemListener) {
            this.iItemListener = iItemListener;
            return this;
        }

        public ListShortcutActionLayout build(){
           return new ListShortcutActionLayout(this);
        }
    }


}
