package com.example.weizhenbin.wangebug.views.behaviors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.weizhenbin.wangebug.views.TitleBar;

/**
 * Created by weizhenbin on 2018/9/3.
 */

public class TitleBarBehavior extends CoordinatorLayout.Behavior<TitleBar> {


    private float deltaY;

    public TitleBarBehavior() {
        Log.d("TitleBarBehavior", "TitleBarBehavior");
    }

    public TitleBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("TitleBarBehavior", "TitleBarBehavior");
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TitleBar child, View dependency) {
        Log.d("TitleBarBehavior", "layoutDependsOn"+(dependency instanceof NestedScrollView));
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TitleBar child, View dependency) {
        Log.d("TitleBarBehavior", "onDependentViewChanged");
        if (deltaY == 0) {
            deltaY = dependency.getY() - child.getHeight();
        }

        float dy = dependency.getY() - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        float y = -(dy / deltaY) * child.getHeight();
        child.setTranslationY(y);
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TitleBar child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        Log.d("TitleBarBehavior", "onNestedPreScroll");
    }

    /* @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (deltaY == 0) {
            deltaY = dependency.getY() - child.getHeight();
        }

        float dy = dependency.getY() - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        float y = -(dy / deltaY) * child.getHeight();
        child.setTranslationY(y);

        return true;
    }*/
}
