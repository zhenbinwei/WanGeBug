package com.example.weizhenbin.wangebug.views;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by weizhenbin on 2018/3/28.
 */

public class SlideExitTouch implements View.OnTouchListener {
    private float moveY = 0;
    private float downY = 0;
    private float firstDownY=0;
    private int touchSlop;
    private IStateListener stateListener;
    private GestureDetector mGestureDetector;
    public SlideExitTouch(Context context) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getPointerCount() > 1) {
            return false;
        }
        if(v instanceof TouchImageView){
           /* if(((TouchImageView) v).isZoomed()||((TouchImageView) v).canVerticalDrag()){
                return false;
            }*/
            if(((TouchImageView) v).isZoomed()){
                return false;
            }
        }
        if(mGestureDetector==null){
            mGestureDetector = new GestureDetector(v.getContext(), new GestureListener(v));
        }
        if(!(v instanceof TouchImageView)) {
            //长按 点击和onTouch 冲突问题  借助GestureDetector来解决 TouchImageView已经带有mGestureDetector如果不排除 会回调两次
            mGestureDetector.onTouchEvent(event);
        }
        if (event.getPointerCount() > 1) {
            return false;
        }
        if(v instanceof TouchImageView){
            if(((TouchImageView) v).isZoomed()){
                return false;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downY = event.getRawY();
            firstDownY=event.getRawY();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if(v instanceof TouchImageView){
                moveY = event.getRawY();
                v.setY(v.getY() + (moveY - downY));
                downY = moveY;
                ((TouchImageView) v).setCanDrag(false);

                if(v.getY()>0&& ((TouchImageView) v).canScrollVertical(-1)){
                    v.setY(0);
                    ((TouchImageView) v).setCanDrag(true);
                }else
                if(v.getY()<0&&((TouchImageView) v).canScrollVertical(1)){
                    v.setY(0);
                    ((TouchImageView) v).setCanDrag(true);
                }
            }else {
                moveY = event.getRawY();
                v.setY(v.getY() + (moveY - downY));
                downY = moveY;
            }
            if (stateListener != null&& Math.abs(event.getRawY()-firstDownY)>touchSlop) {
                stateListener.onMove((int) v.getY());
            }
        }
        if(event.getAction()== MotionEvent.ACTION_UP){
            if (Math.abs(v.getY()) > 250) {
                if (stateListener != null) {
                    stateListener.onFinish();
                }
            } else {
                v.setY(0);
                if (stateListener != null) {
                    stateListener.onUp();
                }
            }
        }
        return true;
    }

    public void setStateListener(IStateListener stateListener) {
        this.stateListener = stateListener;
    }

    public interface IStateListener {
        void onMove(int offset);

        void onFinish();

        void onUp();
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
