package com.example.weizhenbin.wangebug.modules.recreation.adapters;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.image.IImageLoader;
import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.modules.recreation.entity.JokeContentlistBaseBean;
import com.example.weizhenbin.wangebug.modules.recreation.entity.PicJokeBean;
import com.example.weizhenbin.wangebug.modules.recreation.entity.TextJokeBean;

import java.util.List;

import static android.R.attr.data;
import static android.text.Html.FROM_HTML_MODE_COMPACT;

/**
 * Created by weizhenbin on 2018/8/13.
 */

public class JokeListAdapter extends BaseMultiItemQuickAdapter<JokeContentlistBaseBean,BaseViewHolder> {

    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public JokeListAdapter(Context context,List data) {
        super(data);
        this.context=context;
        addItemType(JokeContentlistBaseBean.TEXT, R.layout.joke_text_item);
        addItemType(JokeContentlistBaseBean.PIC,R.layout.joke_pic_item);
    }


    @Override
    protected void convert(BaseViewHolder helper, JokeContentlistBaseBean item) {
           switch (item.getItemType()){
               case JokeContentlistBaseBean.TEXT:
                   if(item instanceof TextJokeBean.ContentlistBean){

                       if (Build.VERSION.SDK_INT>Build.VERSION_CODES.N) {
                           helper.setText(R.id.tv_title, Html.fromHtml(((TextJokeBean.ContentlistBean) item).getTitle(), FROM_HTML_MODE_COMPACT));
                           helper.setText(R.id.tv_content, Html.fromHtml(((TextJokeBean.ContentlistBean) item).getText(), FROM_HTML_MODE_COMPACT));
                       }else {
                           helper.setText(R.id.tv_title, Html.fromHtml(((TextJokeBean.ContentlistBean) item).getTitle()));
                           helper.setText(R.id.tv_content, Html.fromHtml(((TextJokeBean.ContentlistBean) item).getText()));
                       }
                   }
                   break;
               case JokeContentlistBaseBean.PIC:
                   if(item instanceof PicJokeBean.ContentlistBean){

                     ImageView imageView= helper.getView(R.id.iv_pic);

                       ImageLoader.getImageLoader().imageLoader(context,imageView,((PicJokeBean.ContentlistBean) item).getImg());

                   }
                   break;
           }
    }
}
