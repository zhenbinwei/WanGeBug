package com.example.weizhenbin.wangebug.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.weizhenbin.wangebug.R;

/**
 * Created by weizhenbin on 2018/8/23.
 */

public class RatioImageView extends AppCompatImageView {

    private float ratio=1;

    public RatioImageView(Context context) {
        this(context,null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
            ratio=typedArray.getFloat(R.styleable.RatioImageView_imageRatio,1);
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
        //super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (int) (MeasureSpec.getSize(widthMeasureSpec)*ratio));
    }
}
