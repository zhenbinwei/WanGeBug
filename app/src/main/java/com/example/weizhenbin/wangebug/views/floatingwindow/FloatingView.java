package com.example.weizhenbin.wangebug.views.floatingwindow;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by weizhenbin on 2018/9/12.
 */

public class FloatingView extends RelativeLayout {


    private Path pathRect,pathCircle;
    private Paint paint;

    private int circleX;
    private int circleY;
    private int circleRadius=5;

    private int defRadius;

    private ValueAnimator animator;
    public FloatingView(Context context) {
        this(context,null);
    }

    public FloatingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init();
    }

    private void init() {
        pathRect=new Path();
        pathCircle=new Path();
        paint=new Paint();
        paint.setColor(Color.BLACK);
      //  paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setAntiAlias(true);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (circleX==0&&circleY==0){
            circleX=getWidth()/2;
            circleY=getHeight()/2;
            defRadius=getWidth()/2;
            circleRadius=defRadius;
            Log.d("FloatingView", "初始化");
        }
        /**
         * 取路径区域重叠的部分
         * */
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        pathRect.reset();
        pathRect.addRect(0,0,getWidth(),getHeight(), Path.Direction.CW);
        pathCircle.reset();
        pathCircle.addCircle(circleX,circleY,circleRadius,Path.Direction.CW);
        pathRect.op(pathCircle, Path.Op.XOR);
        canvas.drawPath(pathRect,paint);

    }

    /**
     * 计算半径最大值
     * */

    private int radiusMax(){
        int w=getWidth();
        int h=getHeight();
       // return (int) (Math.sqrt(w*w+h*h)-Math.sqrt(circleX*circleX+circleY*circleY))+10;
        return (int) (Math.sqrt(w*w+h*h));
    }

    private void zoom(Animator.AnimatorListener listener, int... values){
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator = null;
        }
        animator=ValueAnimator.ofInt(values);
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value= (int) animation.getAnimatedValue();
                circleRadius=value;
                invalidate();
            }
        });
        if (listener!=null) {
            animator.addListener(listener);
        }
        animator.start();
    }

    public void setCircleX(int circleX) {
      //  this.circleX = circleX-defRadius;
        Log.d("FloatingView", "this.circleX:" + this.circleX);
    }

    /**
     * 放大
     * */
    public void zoomIn(Animator.AnimatorListener listener){
        zoom(listener,defRadius,radiusMax());
    }
    /**
     * 缩小
     * */
    public void zoomOut(Animator.AnimatorListener listener){
        zoom(listener,radiusMax(),defRadius);
    }


}
