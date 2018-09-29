package com.example.weizhenbin.wangebug.views.emptyview;

import android.view.View;

/**
 * Created by weizhenbin on 2018/9/27.
 */

public interface IEmptyViewProxy {
     void setEmptyText(String text);

     void setActionText(String text);

     void setAction(View.OnClickListener clickListener);
     void setEventVisibility(int visibility);

     void loading();
     void loading(boolean isShowProgressBar);

     void emptyData();

     void emptyData(boolean showAction);

     void setProgressBarVisibility(int visibility);
}
