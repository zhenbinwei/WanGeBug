package com.example.weizhenbin.wangebug.modules.recreation.adapters;

import android.text.Html;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.modules.recreation.entity.JokeContentlistBaseBean;
import com.example.weizhenbin.wangebug.modules.recreation.entity.TextJokeBean;

import java.util.List;

import static android.text.Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE;

/**
 * Created by weizhenbin on 2018/8/13.
 */

public class JokeListAdapter extends BaseMultiItemQuickAdapter<JokeContentlistBaseBean,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public JokeListAdapter(List data) {
        super(data);
        addItemType(JokeContentlistBaseBean.TEXT, R.layout.joke_text_item);
    }


    @Override
    protected void convert(BaseViewHolder helper, JokeContentlistBaseBean item) {
           switch (item.getItemType()){
               case JokeContentlistBaseBean.TEXT:
                   if(item instanceof TextJokeBean.ContentlistBean){

                       helper.setText(R.id.tv_title, Html.fromHtml(((TextJokeBean.ContentlistBean) item).getTitle()));
                       helper.setText(R.id.tv_content,Html.fromHtml(((TextJokeBean.ContentlistBean) item).getText()));
                   }

                   break;
           }
    }
}
