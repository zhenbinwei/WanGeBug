package com.example.weizhenbin.wangebug.views.floatingwindow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.App;
import com.example.weizhenbin.wangebug.tools.PhoneTool;
import com.example.weizhenbin.wangebug.tools.SoftKeyboardTool;
import com.example.weizhenbin.wangebug.tools.StatusTool;

/**
 * Created by weizhenbin on 2018/9/12.
 * 悬浮窗
 */

public class FloatingWindow implements View.OnTouchListener{

    private WindowManager windowManager;
    private WindowManager.LayoutParams controlLayoutParams;//活动控制层
    private WindowManager.LayoutParams baseLayoutParams;//基础层 底层
    private ViewGroup contentView;
    private FrameLayout baseView;//基础层


    private ImageView controller;//控制器 透明 用于接受触摸事件 浮窗位置
    private FloatingView fvView;

    /**
     * 悬浮窗大小
     * */
    private int windowMiniWidth =PhoneTool.dip2px(48);

    private int windowMiniHeight =PhoneTool.dip2px(48);


    /**
     * 按下相对View的坐标
     * */
    private int downViewX;
    private int downViewY;

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

    /**迷你状态*/
    private boolean isMini=true;

    private Context context;
    public FloatingWindow() {
        context= App.app.getApplicationContext();
        initWindowManager(context);
        baseView=new FloatingContentView(context);
        contentView= (ViewGroup) LayoutInflater.from(context).inflate(R.layout.floating_window_layout,baseView,false);
        baseView.addView(contentView);
        controller=new ImageView(context);
        controller.setImageResource(R.drawable.add);
        controller.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        controller.setBackgroundResource(R.drawable.round_bg_primary);
        fvView=contentView.findViewById(R.id.fv_view);
        screenW= PhoneTool.getScreenWidth();
        screenH= PhoneTool.getScreenHeight();
        statusBarHeight=StatusTool.getStatusBarHeight(context);
        initEvent();
    }


    /**
     * 添加实际可操作的布局
     * */
    public void addRealContentView(View view){
        if (view!=null&&view.getParent()==null) {
            this.contentView.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    /**
     * 设置可缩放布局大小
     * */
    public void setContentViewLayoutParams(FrameLayout.LayoutParams layoutParams){
        contentView.setLayoutParams(layoutParams);
    }

    /**
     * 设置悬浮窗大小
     * */
    public void  setMiniWindowSize(int width,int height){
        controlLayoutParams.height=height;
        controlLayoutParams.width=width;
        windowMiniHeight=height;
        windowMiniWidth=width;
        windowManager.updateViewLayout(controller,controlLayoutParams);
    }

    /**
     * 设置悬浮窗背景
     * */
    public void setMiniWindowBackground(Drawable background){
        controller.setBackground(background);
    }
    /**
     * 设置悬浮窗图标
     * */
    public void setMiniWindowIcon(int resId){
        controller.setImageResource(resId);
    }
    /**
     * 设置悬浮窗图标
     * */
    public void setMiniWindowIcon(Drawable drawable){
        controller.setImageDrawable(drawable);
    }
    private void initEvent() {
        controller.setOnTouchListener(this);
        controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMini){
                    viewZoomOut();
                }else {

                    if (controlLayoutParams.y > 0||(controlLayoutParams.x>0&&controlLayoutParams.x<screenW-windowMiniWidth)) {
                        PointF end;
                        if ((controlLayoutParams.x+windowMiniWidth/2)>screenW/2){
                            end=new PointF(screenW-windowMiniWidth,0);
                        }else {
                            end=new PointF(0,0);
                        }
                        scroll(new PointF(controlLayoutParams.x, controlLayoutParams.y), end, new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                viewZoomIn();
                            }
                        });
                    } else {
                        viewZoomIn();
                    }
                }
            }
        });
        baseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMini){
                    viewZoomOut();
                }
            }
        });
        baseView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (!isMini){
                        viewZoomOut();
                    }

                    return true;
                }
                return false;
            }
        });

    }

    /**
     * 扩展动画展开
     * */
    private void viewZoomIn() {
        AnimatorListenerAdapter animatorListenerAdapter=new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                baseLayoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                controlLayoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                windowManager.updateViewLayout(baseView,baseLayoutParams);
                windowManager.updateViewLayout(controller,controlLayoutParams);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                controller.setVisibility(View.INVISIBLE);
            }
        };
        if (isMini){
        isMini=false;
        fvView.zoomIn(controlLayoutParams.x+windowMiniWidth/2 , controlLayoutParams.y+windowMiniHeight/2, windowMiniWidth/2, animatorListenerAdapter);
        }
    }

    private void viewZoomOut(){
        SoftKeyboardTool.hideSoftKeyboard(context,baseView);
        if (!isMini){
            windowManager.updateViewLayout(controller,controlLayoutParams);
            fvView.zoomOut(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isMini=true;
                    baseLayoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                    controlLayoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                    windowManager.updateViewLayout(baseView,baseLayoutParams);
                    windowManager.updateViewLayout(controller,controlLayoutParams);
                    controller.setVisibility(View.VISIBLE);
                    fvView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fvView.setCircleRadius(0);
                        }
                    },100);
                }
            });
        }
    }


    public void addFloatingWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)){
                Log.d("FloatingWindow", "没有悬浮窗权限");
                return;
            }
        }
        if (!isAddView) {
                isAddView = true;
                windowManager.addView(baseView,baseLayoutParams);
                windowManager.addView(controller, controlLayoutParams);
        }

    }

    public void removeFloatingWindow(){
        if (isAddView) {
            isAddView = false;
            windowManager.removeView(baseView);
            windowManager.removeView(controller);
        }
    }



    private void initWindowManager(Context context) {
        windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        controlLayoutParams =new WindowManager.LayoutParams();
        baseLayoutParams=new WindowManager.LayoutParams();
        /**
         * 8.0以上 没有授权会直接闪退 8.0以下部分手机没有授权 home切换到桌面 悬浮窗会消失 系统会提示禁止了弹窗 应用内能提示
         * */
        if (Build.VERSION.SDK_INT >= 26) {
            controlLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            baseLayoutParams.type=WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            controlLayoutParams.type=WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            baseLayoutParams.type=WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        //基础层初始状态不接收触摸事件
        baseLayoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        controlLayoutParams.flags= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        controlLayoutParams.format= PixelFormat.TRANSLUCENT;
        baseLayoutParams.format= PixelFormat.TRANSLUCENT;
        controlLayoutParams.width= windowMiniWidth;
        controlLayoutParams.height= windowMiniHeight;
        baseLayoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        baseLayoutParams.height= WindowManager.LayoutParams.MATCH_PARENT;
        controlLayoutParams.gravity= Gravity.TOP | Gravity.START;
        baseLayoutParams.gravity= Gravity.TOP | Gravity.START;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

       if(mGestureDetector==null){
            mGestureDetector = new GestureDetector(v.getContext(), new GestureListener(v));
        }
        //长按 点击和onTouch 冲突问题  借助GestureDetector来解决
        mGestureDetector.onTouchEvent(event);
        if (!isMini){
            return true;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downViewX= (int) event.getX();
                downViewY= (int) event.getY();
                fvView.setCircleRadius(0);
                return true;
            case MotionEvent.ACTION_MOVE:
                controlLayoutParams.y= (int) event.getRawY()- statusBarHeight-downViewY;
                controlLayoutParams.x=(int) event.getRawX()-downViewX;
                windowManager.updateViewLayout(controller,controlLayoutParams);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float w= controlLayoutParams.width;
                float h= controlLayoutParams.height;
                if (controlLayoutParams.x<0){
                    controlLayoutParams.x=0;
                }else if (controlLayoutParams.x>screenW-controlLayoutParams.width){
                    controlLayoutParams.x=screenW-controlLayoutParams.width;
                }
                if (controlLayoutParams.y<0){
                    controlLayoutParams.y=0;
                }else if (controlLayoutParams.y>screenH - statusBarHeight-controlLayoutParams.height){
                    controlLayoutParams.y=screenH - statusBarHeight-controlLayoutParams.height;
                }

                float x = controlLayoutParams.x+w/2;  //用中心点来决定位置
                float y = controlLayoutParams.y+h/2;

                if (x >=screenW / 2 && y <= (screenH+statusBarHeight) / 2) {
                    //第一象限
                    if (screenW - x > y) {
                        scroll(new PointF(controlLayoutParams.x, controlLayoutParams.y), new PointF(controlLayoutParams.x, 0));
                    } else {
                        scroll(new PointF(controlLayoutParams.x, controlLayoutParams.y), new PointF(screenW-controlLayoutParams.width, controlLayoutParams.y));
                    }
                }else
                if (x < screenW / 2 && y <  (screenH+statusBarHeight)  / 2) {
                    //第二象限
                    if (x > y) {
                        scroll(new PointF(controlLayoutParams.x, controlLayoutParams.y), new PointF(controlLayoutParams.x, 0));
                    } else {
                        scroll(new PointF(controlLayoutParams.x, controlLayoutParams.y), new PointF(0, controlLayoutParams.y));
                    }
                }else

                if (x <= screenW / 2 && y >=  (screenH+statusBarHeight)  / 2) {
                    //第三象限
                    if (x > screenH - y) {
                        scroll(new PointF(controlLayoutParams.x, controlLayoutParams.y), new PointF(controlLayoutParams.x, screenH - statusBarHeight-controlLayoutParams.height));
                    } else {
                        scroll(new PointF(controlLayoutParams.x, controlLayoutParams.y), new PointF(0, controlLayoutParams.y));
                    }
                }else
                if (x > screenW / 2 && y >  (screenH+statusBarHeight)  / 2) {
                    //第四象限
                    if (screenW - x > screenH - y) {
                        scroll(new PointF(controlLayoutParams.x, controlLayoutParams.y), new PointF(controlLayoutParams.x, screenH - statusBarHeight-controlLayoutParams.height));
                    } else {
                        scroll(new PointF(controlLayoutParams.x, controlLayoutParams.y), new PointF(screenW-controlLayoutParams.width, controlLayoutParams.y));
                    }
                }
                break;

        }
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




    /**
     * 使用属性动画 实现缓慢回弹效果
     */
    private void scroll(PointF start, PointF end) {
       scroll(start,end,null);
    }
    /**
     * 使用属性动画 实现缓慢回弹效果
     */
    private void scroll(PointF start, PointF end,Animator.AnimatorListener listener) {
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
                controlLayoutParams.x = (int) (point.x);
                controlLayoutParams.y = (int) (point.y);
                windowManager.updateViewLayout(controller, controlLayoutParams);

            }
        });
        if (listener!=null) {
            animator.addListener(listener);
        }
        animator.start();
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private View view;

        private GestureListener(View view) {
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
