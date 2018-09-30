package com.example.weizhenbin.wangebug.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.tools.PhoneTool;

/**
 * Created by weizhenbin on 2018/9/30.
 */
public class LoadingView extends View {

    private Paint paint;

    private int viewWidth;
    private int viewHeight;


    private int dotCount;

    private int dotRadius;

    private int dotSpace;

    private int dotBgColor;//背景色
    private int dotFgColor;//前景色

    private int dotTotalWidth;

    private int index;

    private android.os.Handler handler;
    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
            dotCount=typedArray.getInt(R.styleable.LoadingView_dotCount,3);
            dotRadius=typedArray.getInt(R.styleable.LoadingView_dotRadius, PhoneTool.dip2px(4));
            dotSpace=typedArray.getInt(R.styleable.LoadingView_dotSpace,PhoneTool.dip2px(4));
            dotBgColor=typedArray.getInt(R.styleable.LoadingView_dotBgColor,getResources().getColor(R.color.colorGray9));
            dotFgColor=typedArray.getInt(R.styleable.LoadingView_dotFgColor,getResources().getColor(R.color.colorPrimary));
            typedArray.recycle();
        }catch (Exception e){
            e.printStackTrace();
        }
        init();
    }

    private void init() {
        paint=new Paint();
        paint.setAntiAlias(true);
        dotTotalWidth=dotCount*dotRadius*2+dotSpace*(dotCount-1);
        handler=new AnimHandler(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w=MeasureSpec.getSize(widthMeasureSpec);
        int h=MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.max(w,dotTotalWidth),Math.max(h,dotRadius*2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("LoadingView", "onDraw");
        if (viewHeight==0||viewWidth==0){
            viewHeight=getHeight();
            viewWidth=getWidth();
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        }

        for (int i = 0; i < dotCount; i++) {
            if (i==index){
                paint.setColor(dotFgColor);
            }else {
                paint.setColor(dotBgColor);
            }
            canvas.drawCircle(dotRadius*(2*i+1)+i*dotSpace+(viewWidth-dotTotalWidth)/2,viewHeight/2,dotRadius,paint);
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        Log.d("LoadingView", "onVisibilityChanged"+visibility);
        if (visibility==VISIBLE){
            //显示的启动动画
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessage(1);
        }else {
            //关闭动画
            handler.removeCallbacksAndMessages(null);
        }
    }

    private static class AnimHandler extends Handler{
        LoadingView loadingView;

        private AnimHandler(LoadingView loadingView) {
            this.loadingView = loadingView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            sendEmptyMessageDelayed(1,160);
            if (loadingView.index<loadingView.dotCount){
                loadingView.index++;
            }else {
                loadingView.index=0;
            }
            loadingView.postInvalidate();
        }
    }

}
