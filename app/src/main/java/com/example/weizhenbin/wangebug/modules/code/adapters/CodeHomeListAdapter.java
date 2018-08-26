package com.example.weizhenbin.wangebug.modules.code.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.image.DefImageConfig;
import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.modules.code.entity.ArticleListDataBean;

import java.util.List;

/**
 * Created by weizhenbin on 18/8/26.
 */

public class CodeHomeListAdapter extends BaseQuickAdapter<ArticleListDataBean.DataBean.DatasBean,BaseViewHolder> {
    Context context;
    public CodeHomeListAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<ArticleListDataBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleListDataBean.DataBean.DatasBean item) {

        helper.setText(R.id.tv_author,item.getAuthor());
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_chapterName,item.getChapterName());
        helper.setText(R.id.tv_niceDate,item.getNiceDate());
        ImageView imageView=helper.getView(R.id.iv_envelopePic);
        if (TextUtils.isEmpty(item.getEnvelopePic())){
         imageView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.VISIBLE);
            ImageLoader.getImageLoader().imageLoader(context, imageView, item.getEnvelopePic(), DefImageConfig.smallImage());
        }
    }
}
