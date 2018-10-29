package com.example.weizhenbin.wangebug.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.tools.PhoneTool;

/**
 * Created by weizhenbin on 2018/10/10.
 */
public class ListPopupWindow extends PopupWindow {


    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private IItemListener listener;
    public ListPopupWindow(Context context,String[] items) {
        View view=View.inflate(context,R.layout.list_popupwindow_layout,null);
        initViews(view);
        setContentView(view);
        arrayAdapter=new ArrayAdapter<String>(context, R.layout.list_popupwindow_item,items);
        listView.setAdapter(arrayAdapter);
        setWidth(PhoneTool.dip2px(120));
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if (listener!=null) {
                    listener.onItemClick(position);
                }
            }
        });
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("ListPopupWindow", "onTouch");
                return false;
            }
        });
    }




    @Override
    public int getHeight() {
       return arrayAdapter.getCount()*PhoneTool.dip2px(44);
    }

    private void initViews(View view) {
        listView=view.findViewById(R.id.lv);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }

    public interface IItemListener{
        void onItemClick(int which);
    }

    public ListPopupWindow setItemListener(IItemListener listener) {
        this.listener = listener;
        return this;
    }
}
