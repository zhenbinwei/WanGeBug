package com.example.weizhenbin.wangebug.views.floatingwindow;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by weizhenbin on 2018/9/12.
 *
 * 背景放大效果
 */

public class FloatingView extends RelativeLayout {

    private Path pathRect,pathCircle;
    private Paint paint;

    private int defRadius;

    private int circleX=defRadius;
    private int circleY=defRadius;
    private int circleRadius=defRadius;

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
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint.setAntiAlias(true);
       // setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(0,0,this.getWidth(),this.getHeight(),null,Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
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
        if (circleX<w/2&&circleY<h/2){
            //第一象限
            return (int) (Math.sqrt((w-circleX)*(w-circleX)+(h-circleY)*(h-circleY)));
        }else if (circleX>=w/2&&circleY<h/2){
            //第二象限
            return (int) (Math.sqrt((circleX)*(circleX)+(h-circleY)*(h-circleY)));
        }else if (circleX<w/2&&circleY>=h/2){
            //第三象限
            return (int) (Math.sqrt((w-circleX)*(w-circleX)+(circleY)*(circleY)));
        }else {
            //第四象限
            return (int) (Math.sqrt((circleX)*(circleX)+(circleY)*(circleY)));
        }
    }

    private void zoom(Animator.AnimatorListener listener, int... values){
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator = null;
        }
        animator=ValueAnimator.ofInt(values);
        animator.setDuration(600);
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


    public void setCircleRadius(int circleRadius) {
        if (circleRadius==this.circleRadius){
            return;
        }
        this.circleRadius = circleRadius;
        invalidate();
    }

    /**
     * 放大
     * */
    public void zoomIn(int circleX,int circleY,int defRadius,Animator.AnimatorListener listener){
        this.circleY=circleY;
        this.circleX=circleX;
        this.defRadius=defRadius;
        zoom(listener,defRadius,radiusMax());
    }
    /**
     * 缩小
     * */
    public void zoomOut(int circleX,int circleY,int defRadius,Animator.AnimatorListener listener){
        this.circleY=circleY;
        this.circleX=circleX;
        this.defRadius=defRadius;
        zoom(listener,radiusMax(),defRadius);
    }

    public void zoomOut(Animator.AnimatorListener listener){
        zoom(listener,radiusMax(),defRadius);
    }
}
