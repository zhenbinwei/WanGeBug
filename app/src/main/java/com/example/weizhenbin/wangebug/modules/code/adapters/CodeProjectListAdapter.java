package com.example.weizhenbin.wangebug.modules.code.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.image.DefImageConfig;
import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.modules.code.entity.ProjectListDataBean;

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/28.
 */

public class CodeProjectListAdapter extends BaseQuickAdapter<ProjectListDataBean.DataBean.DatasBean,BaseViewHolder> {

    private Context context;

    public CodeProjectListAdapter(Context context,@Nullable List<ProjectListDataBean.DataBean.DatasBean> data) {
        super(R.layout.project_list_item,data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListDataBean.DataBean.DatasBean item) {
        ImageView imageView=helper.getView(R.id.iv_pic);
        if (TextUtils.isEmpty(item.getEnvelopePic())){
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.VISIBLE);
            ImageLoader.getImageLoader().imageLoader(context,imageView,item.getEnvelopePic(), DefImageConfig.smallImageLong());
        }
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_desc,item.getDesc());
        helper.setText(R.id.tv_chapterName,item.getChapterName());
        helper.setText(R.id.tv_niceDate,item.getNiceDate());
    }
}
