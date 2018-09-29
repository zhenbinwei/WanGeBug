package com.example.weizhenbin.wangebug.modules.recreation.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseSimpleAdapter;
import com.example.weizhenbin.wangebug.image.ImageConfig;
import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanPicBean;
import com.example.weizhenbin.wangebug.modules.recreation.uis.PicBrowserActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/17.
 */

public class YiYuanDataListAdapter  extends BaseSimpleAdapter<YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean,BaseViewHolder> {
    private ImageConfig imageConfig;
    public YiYuanDataListAdapter(final Context context, @LayoutRes int layoutResId, @Nullable List<YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data) {
        super(context,layoutResId, data);
        imageConfig=new ImageConfig.Builder().setHeight(600).setWidth(600).build();
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PicBrowserActivity.startBrowserActivity(context,getPicList(),position);
            }
        });
    }

    @Override
    protected void convert(final BaseViewHolder helper, final YiYuanPicBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean item) {
        ImageView ivPic= helper.getView(R.id.iv_pic);
        ImageLoader.getImageLoader().imageLoader(mContext,ivPic,item.getImage0(),imageConfig);

        String text=item.getText().replace("\n","").trim();

        helper.setText(R.id.tv_title,text);
        helper.addOnClickListener(R.id.iv_pic);
    }



    private ArrayList<String> getPicList(){
        ArrayList<String> pics=new ArrayList<>();
        int size= getData().size();
        for (int i = 0; i < size; i++) {
            pics.add(getData().get(i).getImage0());
        }
        return pics;
    }


}
