package com.example.weizhenbin.wangebug.views.floatingwindow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.os.Build;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.tools.PhoneTool;
import com.example.weizhenbin.wangebug.tools.StatusTool;

import static android.view.ViewAnimationUtils.createCircularReveal;

/**
 * Created by weizhenbin on 2018/9/12.
 * 悬浮窗
 */

public class FloatingWindow implements View.OnTouchListener{

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private View contentView;
    private FloatingView fvView;

    /**
     * 默认大小
     * */
    private int windowDefWidth=200;

    private int windowDefHeight=200;

    /**
     * 默认位置
     * */
    private int defX=0;
    private int defY=0;

    /**
     * 修改后的位置
     * */
    private int updateX=0;
    private int updateY=0;


    /**
     * 按下相对View的坐标
     * */
    private int downViewX;
    private int downViewY;
    /**
     * 按下相对屏幕的坐标
     * */
    private int downScreenX;
    private int downScreenY;

    
    /**
     * 屏幕宽高
     * */
    private int screenW;
    private int screenH;

    /**
     * 状态栏高度
     * */
    private int statusBarHeight;


    /**
     * 属性动画 用于回弹效果
     */
    private ValueAnimator animator;

    private GestureDetector mGestureDetector;

    private boolean isAddView=false;

    /**
     * 悬浮窗大小
     * */
    private Size size;

    /**迷你状态*/
    private boolean isMini=true;


    public FloatingWindow(Context context) {
        initWindowManager(context);
        contentView=View.inflate(context, R.layout.floating_window_layout,null);
        fvView=contentView.findViewById(R.id.fv_view);
        contentView.setOnTouchListener(this);
        screenW= PhoneTool.getScreenWidth();
        screenH= PhoneTool.getScreenHeight();
        statusBarHeight=StatusTool.getStatusBarHeight(context);
       /* contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (isMini){
                  fvView.setCircleX(layoutParams.x);
                  zoomIn();
              }else {
                  fvView.setCircleX(defX);
                  zoomOut();
              }
            }
        });*/
        fvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FloatingWindow", "点击");
            }
        });
    }


    /**
     * 放大
     * */
    private void zoomIn(){
        if (isMini){
            isMini=false;
            Size end=new Size(screenW/2,400);
           // layoutParams.width=end.width;
           // layoutParams.height=end.height;

            scale(new Size(layoutParams.width,layoutParams.height),end);

           // layoutParams.y=70;
            windowManager.updateViewLayout(contentView, layoutParams);
            fvView.post(new Runnable() {
                @Override
                public void run() {
                   // fvView.zoomIn(null);
                }
            });
        }
    }
    /**
     * 缩小
     * */
    private void zoomOut(){
        if (!isMini){
            isMini=true;
            Size end=new Size(windowDefWidth,windowDefHeight);
            layoutParams.width=end.width;
            layoutParams.height=end.height;
            windowManager.updateViewLayout(contentView, layoutParams);
            fvView.post(new Runnable() {
                @Override
                public void run() {
                   /* fvView.zoomOut(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            Size end=new Size(windowDefWidth,windowDefHeight);
                            layoutParams.width=end.width;
                            layoutParams.height=end.height;
                            windowManager.updateViewLayout(contentView, layoutParams);
                        }
                    });*/

                }
            });
        }
    }


    public void showFloatingWindow(){
        if (!isAddView) {
            isAddView = true;
            windowManager.addView(contentView, layoutParams);
        }
    }

    public void removeFloatingWindow(){
        if (isAddView) {
            isAddView = false;
            windowManager.removeView(contentView);
        }
    }



    private void initWindowManager(Context context) {
        windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        layoutParams=new WindowManager.LayoutParams();
        size=new Size(windowDefWidth,windowDefHeight);
        /**
         * 8.0以上 没有授权会直接闪退 8.0以下部分手机没有授权 home切换到桌面 悬浮窗会消失 系统会提示禁止了弹窗 应用内能提示
         * */
        if (Build.VERSION.SDK_INT >= 26) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            layoutParams.type=WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        layoutParams.flags=WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.format= PixelFormat.TRANSLUCENT;
      //  layoutParams.width=size.width;
      //  layoutParams.height=size.height;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.y=defY;
        layoutParams.x=defX;
        layoutParams.gravity= Gravity.TOP | Gravity.START;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

     /*   if(mGestureDetector==null){
            mGestureDetector = new GestureDetector(v.getContext(), new GestureListener(v));
        }
            //长按 点击和onTouch 冲突问题  借助GestureDetector来解决 TouchImageView已经带有mGestureDetector如果不排除 会回调两次
        Log.d("FloatingWindow", "mGestureDetector.onTouchEvent(event):" + mGestureDetector.onTouchEvent(event));

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                downViewX= (int) event.getX();
                downViewY= (int) event.getY();
                downScreenX= (int) event.getRawX();
                downScreenY= (int) event.getRawY();
                return true;
            case MotionEvent.ACTION_MOVE:
                layoutParams.y= (int) event.getRawY()- statusBarHeight-downViewY;
                layoutParams.x=(int) event.getRawX()-downViewX;
                updateX = layoutParams.x;
                updateY = layoutParams.y;
                windowManager.updateViewLayout(contentView,layoutParams);
                break;
            case MotionEvent.ACTION_UP:
               *//* float x = event.getRawX();
                float y = event.getRawY() - statusBarHeight;
                if (x > screenW / 2 && y < screenH / 2) {
                    //第一象限
                    if (screenW - x > y) {
                        scroll(new PointF(layoutParams.x, layoutParams.y), new PointF(layoutParams.x, 0));
                    } else {
                        scroll(new PointF(layoutParams.x, layoutParams.y), new PointF(screenW, layoutParams.y));
                    }
                }
                if (x < screenW / 2 && y < screenH / 2) {
                    //第二象限
                    if (x > y) {
                        scroll(new PointF(layoutParams.x, layoutParams.y), new PointF(layoutParams.x, 0));
                    } else {
                        scroll(new PointF(layoutParams.x, layoutParams.y), new PointF(0, layoutParams.y));
                    }
                }

                if (x < screenW / 2 && y > screenH / 2) {
                    //第三象限
                    if (x > screenH - y) {
                        scroll(new PointF(layoutParams.x, layoutParams.y), new PointF(layoutParams.x, screenH + statusBarHeight));
                    } else {
                        scroll(new PointF(layoutParams.x, layoutParams.y), new PointF(0, layoutParams.y));
                    }
                }
                if (x > screenW / 2 && y > screenH / 2) {
                    //第四象限
                    if (screenW - x > screenH - y) {
                        scroll(new PointF(layoutParams.x, layoutParams.y), new PointF(layoutParams.x, screenH + statusBarHeight));
                    } else {
                        scroll(new PointF(layoutParams.x, layoutParams.y), new PointF(screenW, layoutParams.y));
                    }
                }*//*
                break;

        }*/
        return false;
    }

    private class PointTypeEvaluator implements TypeEvaluator<PointF> {

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            startValue.x = startValue.x + fraction * (endValue.x - startValue.x);
            startValue.y = startValue.y + fraction * (endValue.y - startValue.y);
            return startValue;
        }
    }

    private class SizeTypeEvaluator implements TypeEvaluator<Size> {
        @Override
        public Size evaluate(float fraction, Size startValue, Size endValue) {

            startValue.width = (int) (startValue.width + fraction * (endValue.width - startValue.width));
            startValue.height = (int) (startValue.height + fraction * (endValue.height - startValue.height));
            return startValue;
        }
    }


    private class Size{
        int width;
        int height;

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }


    /**
     * 使用属性动画 实现缓慢回弹效果
     */
    private void scroll(PointF start, PointF end) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator = null;
        }
        animator = ValueAnimator.ofObject(new PointTypeEvaluator(), start, end);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                layoutParams.x = (int) (point.x);
                layoutParams.y = (int) (point.y);
                updateX = layoutParams.x;
                updateY = layoutParams.y;
                windowManager.updateViewLayout(contentView, layoutParams);
            }
        });
        animator.start();
    }

    /**
     * 使用属性动画 实现缩放
     */
    private void scale(Size start, Size end) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator = null;
        }
        animator = ValueAnimator.ofObject(new SizeTypeEvaluator(), start, end);
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Size size = (Size) animation.getAnimatedValue();
                layoutParams.width=size.width;
                layoutParams.height=size.height;
                windowManager.updateViewLayout(contentView, layoutParams);
            }
        });
        animator.start();
    }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private View view;

        public GestureListener(View view) {
            this.view = view;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return view != null && view.performClick();
        }

        @Override
        public void onLongPress(MotionEvent e)
        {
            if(view==null){
                return;
            }
            view.performLongClick();
        }
    }

}
