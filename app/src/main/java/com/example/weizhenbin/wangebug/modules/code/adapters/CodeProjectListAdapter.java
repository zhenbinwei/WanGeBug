package com.example.weizhenbin.wangebug.modules.code.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.WebActivity;
import com.example.weizhenbin.wangebug.image.DefImageConfig;
import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.modules.code.entity.ProjectListDataBean;

import java.util.List;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

/**
 * Created by weizhenbin on 2018/8/28.
 */

public class CodeProjectListAdapter extends BaseQuickAdapter<ProjectListDataBean.DataBean.DatasBean,BaseViewHolder> {

    private Context context;

    public CodeProjectListAdapter(final Context context, @Nullable final List<ProjectListDataBean.DataBean.DatasBean> data) {
        super(R.layout.project_list_item,data);
        this.context=context;
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (data!=null){
                    WebActivity.startActivity(context,data.get(position).getLink());
                   // WebActivity.requestPermission(context,"https://github.com/caiyonglong/Notepad/issues");
                }
            }
        });
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
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            helper.setText(R.id.tv_title,Html.fromHtml(item.getTitle(),FROM_HTML_MODE_COMPACT));
            helper.setText(R.id.tv_desc, Html.fromHtml(item.getDesc(),FROM_HTML_MODE_COMPACT));
        }else {
            helper.setText(R.id.tv_title,Html.fromHtml(item.getTitle()));
            helper.setText(R.id.tv_desc, Html.fromHtml(item.getDesc()));
        }
        helper.setText(R.id.tv_chapterName,item.getChapterName());
        helper.setText(R.id.tv_niceDate,item.getNiceDate());
        helper.addOnClickListener(R.id.ll_item);

    }
}
