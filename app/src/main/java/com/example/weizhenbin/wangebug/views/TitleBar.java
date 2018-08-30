package com.example.weizhenbin.wangebug.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.tools.PhoneTool;
import com.example.weizhenbin.wangebug.tools.StatusTool;

/**
 * Created by weizhenbin on 2018/8/3.
 */

public class TitleBar extends RelativeLayout{

    private RelativeLayout rlLeft;
    private RelativeLayout rlCenter;
    private RelativeLayout rlRight;

    private String title;
    private int leftSrc =-1;
    private int rightSrc =-1;
    private int titleColor;
    private int titleSize;

    private void assignViews() {
        rlLeft = (RelativeLayout) findViewById(R.id.rl_left);
        rlCenter = (RelativeLayout) findViewById(R.id.rl_center);
        rlRight = (RelativeLayout) findViewById(R.id.rl_right);
    }


    public TitleBar(Context context) {
        this(context,null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.app_title_bar,this);
        assignViews();
        setPadding(0, StatusTool.getStatusBarHeight(context),0,0);
        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.titleBar);
            title=typedArray.getString(R.styleable.titleBar_title);
            leftSrc =typedArray.getResourceId(R.styleable.titleBar_leftSrc,-1);
            rightSrc =typedArray.getResourceId(R.styleable.titleBar_rightSrc,-1);
            titleColor=typedArray.getColor(R.styleable.titleBar_titleColor,0xFF000000);
            titleSize=typedArray.getDimensionPixelSize(R.styleable.titleBar_titleSize, PhoneTool.dip2px(context,16));
            typedArray.recycle();
        }catch (Exception e){
            e.printStackTrace();
        }
        init();
    }

    private void init() {
        if(!TextUtils.isEmpty(title)){
            addTitle(title);
        }
        if(leftSrc >0){
            rlLeft.setVisibility(VISIBLE);
            addLeftIcon(leftSrc);
        }else {
            rlLeft.setVisibility(GONE);
        }
        if(rightSrc >0){
            rlRight.setVisibility(VISIBLE);
            addRightIcon(rightSrc);
        }else {
            rlRight.setVisibility(GONE);
        }
    }

    private void addLeftIcon(int resId) {
        ImageView imageView = getImageView(resId);
        addLeftView(imageView);
    }
    private void addRightIcon(int resId) {
        ImageView imageView = getImageView(resId);
        addRightView(imageView);
    }
    @NonNull
    private ImageView getImageView(int resId) {
        ImageView imageView=new ImageView(getContext());
        imageView.setImageResource(resId);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        LayoutParams layoutParams=new LayoutParams(PhoneTool.dip2px(getContext(),46), PhoneTool.dip2px(getContext(),46));
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    public void setTitle(String title){
        addTitle(title);
    }

    private void addTitle(String title) {
        TextView textView=new TextView(getContext());
        textView.setText(title);
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextColor(titleColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleSize);
        LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_VERTICAL);
        textView.setLayoutParams(layoutParams);
        addCenterView(textView);
    }

    public void setLeftOnClickListener(View.OnClickListener leftOnClickListener){
        if(rlLeft!=null){
            rlLeft.setOnClickListener(leftOnClickListener);
        }
    }
    public void setRightOnClickListener(View.OnClickListener rightOnClickListener){
          if(rlRight!=null){
              rlRight.setOnClickListener(rightOnClickListener);
          }
    }
    public RelativeLayout getRlLeft() {
        return rlLeft;
    }

    public RelativeLayout getRlCenter() {
        return rlCenter;
    }

    public RelativeLayout getRlRight() {
        return rlRight;
    }

    public void addLeftView(View view){
        if(view!=null){
            rlLeft.setVisibility(VISIBLE);
            rlLeft.removeAllViews();
            rlLeft.addView(view);
        }
    }
    public void addCenterView(View view){
        if(view!=null){
            rlCenter.setVisibility(VISIBLE);
            rlCenter.removeAllViews();
            rlCenter.addView(view);
        }
    }
    public void addRightView(View view){
        if(view!=null){
            rlRight.setVisibility(VISIBLE);
            rlRight.removeAllViews();
            rlRight.addView(view);
        }
    }

}
