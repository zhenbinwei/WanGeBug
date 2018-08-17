package com.example.weizhenbin.wangebug.modules.recreation.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanPicBean;

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/17.
 */

public class YiYuanDataListAdapter  extends BaseQuickAdapter<YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean,BaseViewHolder>{
    private Context context;
    public YiYuanDataListAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean item) {
        ImageView ivPic= helper.getView(R.id.iv_pic);
        ImageLoader.getImageLoader().imageLoader(context,ivPic,item.getImage0());
    }
}
