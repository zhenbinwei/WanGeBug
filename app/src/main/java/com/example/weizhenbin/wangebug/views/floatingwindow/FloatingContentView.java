package com.example.weizhenbin.wangebug.views.floatingwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

/**
 * Created by weizhenbin on 18/9/16.
 */

public class FloatingContentView extends FrameLayout {
    public FloatingContentView(Context context) {
        super(context);
    }

    public FloatingContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_UP) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                    || event.getKeyCode() == KeyEvent.KEYCODE_SETTINGS) {
                if (mOnKeyListener != null) {
                    mOnKeyListener.onKey(this, KeyEvent.KEYCODE_BACK, event);
                    return true;
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    OnKeyListener mOnKeyListener = null;

    @Override
    public void setOnKeyListener(OnKeyListener l) {
        mOnKeyListener = l;

        super.setOnKeyListener(l);
    }
}
