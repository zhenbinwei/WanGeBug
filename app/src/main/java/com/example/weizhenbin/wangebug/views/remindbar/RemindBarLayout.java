package com.example.weizhenbin.wangebug.views.remindbar;

import android.animation.LayoutTransition;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.App;

/**
 * Created by weizhenbin on 2018/9/18.
 */

public final class RemindBarLayout {

    private static final  int KEY_REMOVW_VIEW = 10;

    private View remindBarLayout;
    private ViewGroup parent;

    private TextView tvMsg;
    private TextView tvAction;

    private String msgText;
    private String actionText;

    private int duration=RemindBar.LENGTH_LONG;
    private RemindBarHandler handler;


    public RemindBarLayout(View view) {
        parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException("No suitable parent found from the given view. "
                    + "Please provide a valid view.");
        }
        parent.setLayoutTransition(new LayoutTransition());
        remindBarLayout = LayoutInflater.from(App.app.getApplicationContext()).inflate(R.layout.remind_bar_layout, parent, false);
        tvMsg = remindBarLayout.findViewById(R.id.tv_msg);
        tvAction = remindBarLayout.findViewById(R.id.tv_action);
        handler=new RemindBarHandler(this);
    }

    public RemindBarLayout setBackgroundColor(int color) {
        remindBarLayout.setBackgroundColor(color);
        return this;
    }

    public RemindBarLayout setMsgTextColor(int color) {
        tvMsg.setTextColor(color);
        return this;
    }




    public RemindBarLayout setActionTextColor(int color) {
        tvAction.setTextColor(color);
        return this;
    }

    public RemindBarLayout setActionBackGround(Drawable background) {
        tvAction.setBackground(background);
        return this;
    }

    public RemindBarLayout setMsgText(String msgText) {
        this.msgText = msgText;
        return this;
    }

    public RemindBarLayout setActionText(String actionText) {
        this.actionText = actionText;
        return this;
    }

    public RemindBarLayout setMarginBottom(int px) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) remindBarLayout.getLayoutParams();
        params.bottomMargin = px;
        return this;
    }

   public RemindBarLayout setDuration(int duration){
       this.duration=duration;
       return this;
   }


    public RemindBarLayout setAction(CharSequence text, final View.OnClickListener listener){
        if (TextUtils.isEmpty(text) || listener == null) {
            tvAction.setVisibility(View.GONE);
            tvAction.setOnClickListener(null);
        } else {
            tvAction.setVisibility(View.VISIBLE);
            tvAction.setText(text);
            tvAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                    // Now dismiss the Snackbar
                    remove();
                }
            });
        }
        return this;
    }



    public void show() {
        //-1 表示没有添加过
        int index = parent.indexOfChild(remindBarLayout);
        if (index != -1) {
            parent.removeView(remindBarLayout);
            handler.removeCallbacksAndMessages(null);
        }
        tvMsg.setText(msgText);
        parent.addView(remindBarLayout);
        handler.sendEmptyMessageDelayed(KEY_REMOVW_VIEW,duration);
    }


    public void remove() {
        int index = parent.indexOfChild(remindBarLayout);
        if (index != -1) {
            parent.removeView(remindBarLayout);
            handler.removeCallbacksAndMessages(null);
        }
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return (ViewGroup) view;
                } else {
                    // It's not the content view but we'll use it as our fallback
                    fallback = (ViewGroup) view;
                }
            }

            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
        return fallback;
    }


    private static class RemindBarHandler extends Handler{
        private RemindBarLayout remindBarLayout;

        private RemindBarHandler(RemindBarLayout remindBarLayout) {
            this.remindBarLayout = remindBarLayout;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==KEY_REMOVW_VIEW){
                remindBarLayout.remove();
                this.removeCallbacksAndMessages(null);
            }
        }
    }
}
