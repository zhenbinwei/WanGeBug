package com.example.weizhenbin.wangebug.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.weizhenbin.wangebug.R;

/**
 * Created by weizhenbin on 2018/8/27.
 */

public class RatioFrameLayout extends FrameLayout{
    private float ratio=1;

    public RatioFrameLayout(@NonNull Context context) {
        this(context,null);
    }

    public RatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout);
            ratio=typedArray.getFloat(R.styleable.RatioFrameLayout_frameRatio,1);
            typedArray.recycle();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setRatio(float ratio) {
        this.ratio = ratio;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        /**按照比例改变高度值*/
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childWidthSize*ratio), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


}
