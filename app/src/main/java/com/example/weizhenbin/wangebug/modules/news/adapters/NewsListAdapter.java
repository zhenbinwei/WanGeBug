package com.example.weizhenbin.wangebug.modules.news.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseMultipleAdapter;
import com.example.weizhenbin.wangebug.base.UrlTypeEnum;
import com.example.weizhenbin.wangebug.base.WebActivity;
import com.example.weizhenbin.wangebug.image.DefImageConfig;
import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean;
import com.example.weizhenbin.wangebug.tools.PhoneTool;
import com.example.weizhenbin.wangebug.views.RatioImageView;

import java.util.List;

import static com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.MULTI_PIC;
import static com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.NO_PIC;
import static com.example.weizhenbin.wangebug.modules.news.entity.YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean.SINGLE_PIC;

/**
 * Created by weizhenbin on 2018/8/23.
 */

public class NewsListAdapter extends BaseMultipleAdapter<YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewsListAdapter(final Context context, final List<YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> data) {
        super(context,data);
        addItemType(SINGLE_PIC, R.layout.news_single_pic_item);
        addItemType(MULTI_PIC, R.layout.news_multi_pic_item);
        addItemType(NO_PIC, R.layout.news_not_pic_item);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean=data.get(position);

                WebActivity.startActivity(context,contentlistBean.getLink(),contentlistBean.getTitle(), UrlTypeEnum.news);
            }
        });
    }

    @Override
    protected void convert(BaseViewHolder helper, YiYuanNewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean item) {
         switch (item.getItemType()){
             case SINGLE_PIC:
                 helper.setText(R.id.tv_title,item.getTitle());
                 helper.setText(R.id.tv_channelName,item.getChannelName());
                 helper.setText(R.id.tv_pub_time,item.getPubDate());
                 RatioImageView ratioImageView =helper.getView(R.id.iv_pic);
                 String url;
                 if (item.getImageurls()!=null&&item.getImageurls().size()>0){
                     url=item.getImageurls().get(0).getUrl();
                 }else {
                     url=null;
                 }
                 if (TextUtils.isEmpty(url)){
                     ratioImageView.setVisibility(View.GONE);
                 }else {
                     ratioImageView.setVisibility(View.VISIBLE);
                     ImageLoader.getImageLoader().imageLoader(mContext, ratioImageView, url, DefImageConfig.smallImage());
                 }
                 helper.addOnClickListener(R.id.ll_item);
                 break;
             case MULTI_PIC:
                 helper.setText(R.id.tv_title,item.getTitle());
                 helper.setText(R.id.tv_channelName,item.getChannelName());
                 helper.setText(R.id.tv_pub_time,item.getPubDate());
                 LinearLayout linearLayout=helper.getView(R.id.ll_images);
                 linearLayout.removeAllViews();
                 if (item.getImageurls()!=null){
                     linearLayout.setVisibility(View.VISIBLE);
                     int size=item.getImageurls().size();
                     for (int i = 0; i < size; i++) {
                        if (i<3){
                            String url2=item.getImageurls().get(i).getUrl();
                            RatioImageView imageView=new RatioImageView(mContext);
                            imageView.setRatio(0.7f);
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.weight=1;
                            if (size<3){
                                if (i < 1) {
                                    layoutParams.rightMargin = PhoneTool.dip2px( 8);
                                }
                            }else {
                                if (i < 2) {
                                    layoutParams.rightMargin = PhoneTool.dip2px( 8);
                                }
                            }
                            ImageLoader.getImageLoader().imageLoader(mContext, imageView, url2,DefImageConfig.smallImage());
                            linearLayout.addView(imageView,layoutParams);
                        }
                     }

                 }else {
                     linearLayout.setVisibility(View.GONE);
                 }
                 helper.addOnClickListener(R.id.ll_item);
                 break;
             case NO_PIC:
                 helper.setText(R.id.tv_title,item.getTitle());
                 helper.setText(R.id.tv_channelName,item.getChannelName());
                 helper.setText(R.id.tv_pub_time,item.getPubDate());
                 helper.addOnClickListener(R.id.ll_item);
                 break;
         }
    }
}
