package com.example.weizhenbin.wangebug.views.nestlayout;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.views.TitleBar;

/**
 * Created by weizhenbin on 2018/9/21.
 */

public class NestLayout extends FrameLayout implements NestedScrollingParent2{

    IRollChange iRollChange;
    NestedScrollingParentHelper nestedScrollingParentHelper;

    TitleBar titleBar;

    int titleBarHeighet;
    public NestLayout(@NonNull Context context) {
        this(context,null);
    }

    public NestLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NestLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        nestedScrollingParentHelper=new NestedScrollingParentHelper(this);
        titleBarHeighet=getResources().getDimensionPixelSize(R.dimen.title_bar_height);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //找出所有符合条件的view
        //滑动的时候进行重新派遣指定事件
        titleBar= (TitleBar) getTargetView(this,TitleBar.class);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (titleBar!=null) {
            setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
            int childWidthSize = getMeasuredWidth();
            int childHeightSize = getMeasuredHeight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
            /**按照比例改变高度值*/
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize + titleBarHeighet, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //找到第一个符合条件的view
    private View getTargetView(ViewGroup viewGroup,Class aClass){
        if (viewGroup==null){
            return null;
        }
         int size=viewGroup.getChildCount();
        for (int i = 0; i < size; i++) {
            View v=viewGroup.getChildAt(i);
            if (v.getClass().equals(aClass)){
                return v;
            }else if(v instanceof ViewGroup){
              return this.getTargetView((ViewGroup) v,aClass);
            }
        }
        return null;
    }



    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return  (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
         nestedScrollingParentHelper.onNestedScrollAccepted(child,target,axes,type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        nestedScrollingParentHelper.onStopNestedScroll(target,type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
      //  Log.d("NestLayout", "onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
      //  Log.d("NestLayout", "onNestedPreScroll");
        if (titleBar!=null){
            //dy<0  向下拉
            if (dy<0&&getScrollY()>0){
                if (getScrollY()+dy<0){
                    scrollBy(0, -getScrollY());
                    consumed[1] = -getScrollY();
                }else {
                    scrollBy(0, dy);
                    consumed[1] = dy;
                }
            }
            //往上滑
            if (dy>0&&getScrollY()< titleBarHeighet){

                if (getScrollY()+dy>titleBarHeighet){
                    scrollBy(0, titleBarHeighet-getScrollY());
                    consumed[1] = titleBarHeighet-getScrollY();
                }else {
                    scrollBy(0, dy);
                    consumed[1] = dy;
                }
            }
            float alpha=1-(getScrollY()/titleBarHeighet);
            if (alpha<0.4){
                alpha=0;
            }
            titleBar.getLlTitleBar().setAlpha(alpha);
        }
        if (iRollChange!=null){
            iRollChange.onRollChange(dy);
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }


    public void setiRollChange(IRollChange iRollChange) {
        this.iRollChange = iRollChange;
    }
}
