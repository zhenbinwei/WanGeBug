package com.example.weizhenbin.wangebug.modules.code.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.weizhenbin.wangebug.base.UrlTypeEnum;
import com.example.weizhenbin.wangebug.base.WebActivity;
import com.example.weizhenbin.wangebug.image.DefImageConfig;
import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.modules.code.entity.BannerDataBean;
import com.example.weizhenbin.wangebug.views.autoscrolllayout.AutoScrollerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhenbin on 2018/8/27.
 */

public class CodeHomeBannerAdapter extends AutoScrollerAdapter {

    private List<BannerDataBean.DataBean> data;
    private List<ImageView> imageViews=new ArrayList<>();
    private Context context;
    public CodeHomeBannerAdapter(Context context,List<BannerDataBean.DataBean> data) {
        this.data=data;
        this.context=context;
        initViews(data);
    }

    private void initViews(List<BannerDataBean.DataBean> data){
        if (data==null||data.isEmpty()){
            return;
        }
        imageViews.clear();
        for (int i = 0; i < data.size(); i++) {
            ImageView imageView=new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
    }


    @Override
    public int getReadCount() {
        if (data!=null){
            return data.size();
        }
        return 0;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        ImageView imageView = null;
        if (getReadCount()>0) {
            final int newPosition = position % getReadCount();
            imageView=imageViews.get(newPosition);
            ImageLoader.getImageLoader().imageLoader(container.getContext(),imageView,data.get(newPosition).getImagePath(), DefImageConfig.smallImage());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BannerDataBean.DataBean dataBean= data.get(newPosition);
                    WebActivity.startActivity(container.getContext(),dataBean.getUrl(),dataBean.getTitle(), UrlTypeEnum.code);
                }
            });
        }
        if (imageView==null){
            imageView=new ImageView(context);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
