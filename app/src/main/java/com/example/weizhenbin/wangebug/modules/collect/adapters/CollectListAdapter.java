package com.example.weizhenbin.wangebug.modules.collect.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter;
import com.example.weizhenbin.wangebug.base.UrlTypeEnum;
import com.example.weizhenbin.wangebug.base.WebActivity;
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler;
import com.example.weizhenbin.wangebug.eventbus.EventCode;
import com.example.weizhenbin.wangebug.eventbus.MessageEvent;
import com.example.weizhenbin.wangebug.modules.collect.controllers.CollectController;
import com.example.weizhenbin.wangebug.modules.collect.entity.TBCollectBean;
import com.example.weizhenbin.wangebug.tools.DialogTool;
import com.example.weizhenbin.wangebug.views.remindbar.RemindBar;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static com.example.weizhenbin.wangebug.views.remindbar.RemindBar.LENGTH_SHORT;

/**
 * Created by weizhenbin on 2018/10/10.
 */
public class CollectListAdapter extends BaseSimpleAdapter<TBCollectBean,BaseViewHolder> {
    public CollectListAdapter(@Nullable final Context context, @Nullable final List<TBCollectBean> data) {
        super(context, R.layout.collect_list_item, data);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (data==null){
                    return;
                }
                TBCollectBean datasBean=   data.get(position);
                WebActivity.startActivity(context,datasBean.getUrl(),datasBean.getTitle(), UrlTypeEnum.valueOf(datasBean.getType()) );
            }
        });
        setOnItemChildLongClickListener(new OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, final View view, final int position) {
                String[] items=new String[]{mContext.getString(R.string.del_string),mContext.getString(R.string.copy_link_string)};
                DialogTool.showListAlertDialog(mContext, items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (data==null){
                            return;
                        }
                        TBCollectBean tbCollectBean=data.get(position);
                        switch (which){
                            case 0:
                                CollectController.removeCollectByTitle(tbCollectBean.getTitle());
                                EventBusHandler.post(new MessageEvent(EventCode.CANCEL_COLLECT_CODE,tbCollectBean.getTitle()));
                                break;
                            case 1:
                                copyLike(view,tbCollectBean.getUrl());
                                break;
                        }
                    }
                });
                return true;
            }
        });
    }

    private void copyLike(View v,String url) {
        ClipboardManager clipboardManager=(ClipboardManager)mContext.getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", url);
        if (clipboardManager!=null) {
            clipboardManager.setPrimaryClip(clipData);
        }
        RemindBar.make(v,mContext.getString(R.string.copy_success_string),LENGTH_SHORT).show();
    }

    @Override
    protected void convert(BaseViewHolder helper, TBCollectBean item) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle(),FROM_HTML_MODE_COMPACT));
            helper.setText(R.id.tv_desc, Html.fromHtml(item.getUrl(),FROM_HTML_MODE_COMPACT));
        }else {
            helper.setText(R.id.tv_title,Html.fromHtml(item.getTitle()));
            helper.setText(R.id.tv_desc, Html.fromHtml(item.getUrl()));
        }
        helper.addOnClickListener(R.id.ll_item);
        helper.addOnLongClickListener(R.id.ll_item);
    }
}
