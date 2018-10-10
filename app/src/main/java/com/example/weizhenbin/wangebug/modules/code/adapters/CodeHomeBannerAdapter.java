package com.example.weizhenbin.wangebug.modules.code.adapters;

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

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/27.
 */

public class CodeHomeBannerAdapter extends AutoScrollerAdapter {

    private List<BannerDataBean.DataBean> data;
    public CodeHomeBannerAdapter(List<BannerDataBean.DataBean> data) {
        this.data=data;
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
        ImageView imageView=new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (getReadCount()>0) {
            final int newPosition = position % getReadCount();
            ImageLoader.getImageLoader().imageLoader(container.getContext(),imageView,data.get(newPosition).getImagePath(), DefImageConfig.smallImage());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BannerDataBean.DataBean dataBean= data.get(newPosition);
                    WebActivity.startActivity(container.getContext(),dataBean.getUrl(),dataBean.getTitle(), UrlTypeEnum.code);
                }
            });
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
