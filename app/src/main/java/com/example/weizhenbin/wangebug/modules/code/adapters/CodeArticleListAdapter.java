package com.example.weizhenbin.wangebug.modules.code.adapters;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseMultipleAdapter;
import com.example.weizhenbin.wangebug.base.UrlTypeEnum;
import com.example.weizhenbin.wangebug.base.WebActivity;
import com.example.weizhenbin.wangebug.image.DefImageConfig;
import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean;

import java.util.List;

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean.DataBean.DatasBean.HAS_PIC;
import static com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean.DataBean.DatasBean.NO_PIC;

/**
 * Created by weizhenbin on 18/8/26.
 */

public class CodeArticleListAdapter extends BaseMultipleAdapter<ArticleListDataBean.DataBean.DatasBean,BaseViewHolder> {
    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CodeArticleListAdapter(final Context context, final List<ArticleListDataBean.DataBean.DatasBean> data) {
        super(context,data);
        this.context=context;
        addItemType(HAS_PIC, R.layout.code_article_list_pic_item);
        addItemType(NO_PIC, R.layout.code_article_list_item);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleListDataBean.DataBean.DatasBean datasBean=   data.get(position);
                WebActivity.Companion.startActivity(context,datasBean.getLink(),datasBean.getTitle(), UrlTypeEnum.CODE);
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleListDataBean.DataBean.DatasBean item) {
        switch (item.getItemType()){
            case HAS_PIC:
                helper.setText(R.id.tv_author,item.getAuthor());
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle(),FROM_HTML_MODE_COMPACT));
                }else {
                    helper.setText(R.id.tv_title,Html.fromHtml(item.getTitle()));
                }
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    helper.setText(R.id.tv_chapterName, Html.fromHtml(item.getChapterName(),FROM_HTML_MODE_COMPACT));
                }else {
                    helper.setText(R.id.tv_chapterName,Html.fromHtml(item.getChapterName()));
                }
                helper.setText(R.id.tv_niceDate,item.getNiceDate());
                ImageView imageView=helper.getView(R.id.iv_envelopePic);
                if (TextUtils.isEmpty(item.getEnvelopePic())){
                    imageView.setVisibility(View.GONE);
                }else {
                    imageView.setVisibility(View.VISIBLE);
                    ImageLoader.getImageLoader().imageLoader(context, imageView, item.getEnvelopePic(), DefImageConfig.smallImage());
                }
                helper.addOnClickListener(R.id.ll_item);
                helper.addOnLongClickListener(R.id.ll_item);
                break;
            case NO_PIC:
                helper.setText(R.id.tv_author,item.getAuthor());
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle(),FROM_HTML_MODE_COMPACT));
                }else {
                    helper.setText(R.id.tv_title,Html.fromHtml(item.getTitle()));
                }
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    helper.setText(R.id.tv_chapterName, Html.fromHtml(item.getChapterName(),FROM_HTML_MODE_COMPACT));
                }else {
                    helper.setText(R.id.tv_chapterName,Html.fromHtml(item.getChapterName()));
                }
                helper.setText(R.id.tv_niceDate,item.getNiceDate());
                helper.addOnClickListener(R.id.ll_item);
                helper.addOnLongClickListener(R.id.ll_item);
                break;
        }
    }
}
