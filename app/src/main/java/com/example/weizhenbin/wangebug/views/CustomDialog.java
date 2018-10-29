package com.example.weizhenbin.wangebug.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.tools.PhoneTool;

/**
 * Created by weizhenbin on 2018/10/16.
 */
public class CustomDialog extends Dialog {
    protected CustomDialog(@NonNull Context context, final Builder builder) {
        super(context, R.style.CustomDialog);

        if (builder==null){
            throw  new NullPointerException("builder 不能为空");
        }
        View view;
        if (builder.items!=null&&builder.items.length>0){
            view=View.inflate(context,R.layout.custom_list_dialog,null);
            ListView lvList=view.findViewById(R.id.lv_list);
            ArrayAdapter arrayAdapter=new ArrayAdapter<String>(context, R.layout.list_dialog_item,builder.items);
            lvList.setAdapter(arrayAdapter);
            lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dismiss();
                    if (builder.onListItemClickListener!=null) {
                        builder.onListItemClickListener.onClick(CustomDialog.this,position);
                    }
                }
            });
        }else {
            view=View.inflate(context,R.layout.custom_dialog,null);
            TextView tvTitle=view.findViewById(R.id.tv_title);
            TextView tvMsg=view.findViewById(R.id.tv_msg);
            TextView tvPositive=view.findViewById(R.id.tv_positive);
            TextView tvNegative=view.findViewById(R.id.tv_negative);
            if (TextUtils.isEmpty(builder.title)&&builder.titleResId<=0){
                tvTitle.setVisibility(View.GONE);
            }else {
                tvTitle.setVisibility(View.VISIBLE);
                if (builder.titleResId>0){
                    tvTitle.setText(builder.titleResId);
                }
                if (!TextUtils.isEmpty(builder.title)){
                    tvTitle.setText(builder.title);
                }
            }
            if (TextUtils.isEmpty(builder.message)&&builder.messageResId<=0){
                tvMsg.setVisibility(View.GONE);
            }else {
                tvMsg.setVisibility(View.VISIBLE);
                if (builder.messageResId>0){
                    tvMsg.setText(builder.messageResId);
                }
                if (!TextUtils.isEmpty(builder.message)){
                    tvMsg.setText(builder.message);
                }
            }

            if ((TextUtils.isEmpty(builder.negativeBtnStr)&&builder.negativeBtnStrResId<=0)||builder.onNegativeClickListener==null){
                tvNegative.setVisibility(View.GONE);
            }else {
                tvNegative.setVisibility(View.VISIBLE);
                if (builder.negativeBtnStrResId>0){
                    tvNegative.setText(builder.negativeBtnStrResId);
                }
                if (!TextUtils.isEmpty(builder.negativeBtnStr)){
                    tvNegative.setText(builder.negativeBtnStr);
                }
                tvNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (builder.onNegativeClickListener!=null){
                            builder.onNegativeClickListener.onClick(CustomDialog.this,0);
                        }
                    }
                });
            }
            if ((TextUtils.isEmpty(builder.positiveBtnStr)&&builder.positiveBtnStrResId<=0)||builder.onPositiveClickListener==null){
                tvPositive.setVisibility(View.GONE);
            }else {
                tvPositive.setVisibility(View.VISIBLE);
                if (builder.positiveBtnStrResId>0){
                    tvPositive.setText(builder.positiveBtnStrResId);
                }
                if (!TextUtils.isEmpty(builder.positiveBtnStr)){
                    tvPositive.setText(builder.positiveBtnStr);
                }
                tvPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (builder.onPositiveClickListener!=null){
                            builder.onPositiveClickListener.onClick(CustomDialog.this,0);
                        }
                    }
                });
            }
        }
        setContentView(view);
    }


    @Override
    public void show() {
        super.show();
        Window window=getWindow();
        if (window==null){
            return;
        }
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(PhoneTool.dip2px(30), 0, PhoneTool.dip2px(30), 0);

        getWindow().setAttributes(layoutParams);
    }

    public static class Builder{

        private Context context;
        private String title;
        private int titleResId;
        private String message;
        private int messageResId;
        private String positiveBtnStr;
        private int positiveBtnStrResId;
        private String negativeBtnStr;
        private int negativeBtnStrResId;
        private OnClickListener onPositiveClickListener;
        private OnClickListener onNegativeClickListener;
        private String[] items;
        private OnClickListener onListItemClickListener;
        public Builder(Context context){
           this.context=context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitleResId(int titleResId) {
            this.titleResId = titleResId;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessageResId(int messageResId) {
            this.messageResId = messageResId;
            return this;
        }

        public Builder setPositiveButton(String positiveBtnStr,OnClickListener onPositiveClickListener) {
            this.positiveBtnStr = positiveBtnStr;
            this.onPositiveClickListener = onPositiveClickListener;
            return this;
        }

        public Builder setPositiveButton(int positiveBtnStrResId,OnClickListener onPositiveClickListener) {
            this.positiveBtnStrResId = positiveBtnStrResId;
            this.onPositiveClickListener = onPositiveClickListener;
            return this;
        }

        public Builder setNegativeButton(String negativeBtnStr,OnClickListener onNegativeClickListener) {
            this.negativeBtnStr = negativeBtnStr;
            this.onNegativeClickListener = onNegativeClickListener;
            return this;
        }

        public Builder setNegativeButton(int negativeBtnStrResId,OnClickListener onNegativeClickListener) {
            this.negativeBtnStrResId = negativeBtnStrResId;
            this.onNegativeClickListener = onNegativeClickListener;
            return this;
        }

        public Builder setItems(String[] items,OnClickListener onListItemClickListener) {
            this.items = items;
            this.onListItemClickListener=onListItemClickListener;
            return this;
        }

        public CustomDialog create(){
            return new CustomDialog(context,this);
        }
    }

   public interface OnClickListener {
        void onClick(DialogInterface dialog, int which);
    }
}
