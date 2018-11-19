package com.example.weizhenbin.wangebug.modules.recreation.uis;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.weizhenbin.wangebug.R;
import com.example.weizhenbin.wangebug.base.BaseFragment;
import com.example.weizhenbin.wangebug.image.ImageConfig;
import com.example.weizhenbin.wangebug.image.ImageLoadListenerAdapter;
import com.example.weizhenbin.wangebug.image.ImageLoader;
import com.example.weizhenbin.wangebug.tools.BitmapTool;
import com.example.weizhenbin.wangebug.tools.CommonTool;
import com.example.weizhenbin.wangebug.views.SlideExitTouch;
import com.example.weizhenbin.wangebug.views.TouchImageView;

/**
 * Created by weizhenbin on 2018/8/20.
 */

public class PicBrowserFragment extends BaseFragment {

    @Override
    public void loadData() {

    }


    String picUrl;
    private int maxTexture=0;
    SlideExitTouch slideExitTouch;
    View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        maxTexture= BitmapTool.getGLESTextureLimitEqualAboveLollipop();
        if (getArguments()!=null) {
            picUrl = getArguments().getString("Url");
        }
    }

    public static PicBrowserFragment getFragment(String picUrl){
        PicBrowserFragment picBrowserFragment=new PicBrowserFragment();
        Bundle bundle=new Bundle();
        bundle.putString("Url",picUrl);
        picBrowserFragment.setArguments(bundle);
        return picBrowserFragment;
    }
    private void initSlideExitTouch(){
        slideExitTouch=new SlideExitTouch(getContext());
        slideExitTouch.setStateListener(new SlideExitTouch.IStateListener() {
            @Override
            public void onMove(int offset) {
                int alpha=Math.abs(offset);
                if(alpha<=765) {
                    view.getBackground().mutate().setAlpha(255-alpha/3);
                }else {
                    view.getBackground().mutate().setAlpha(0);
                }
            }

            @Override
            public void onFinish() {
                view.getBackground().mutate().setAlpha(0);
                if (getActivity()!=null) {
                    getActivity().finish();
                }
            }

            @Override
            public void onUp() {
                view.getBackground().mutate().setAlpha(255);
            }
        });
    }


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fm_pic_browser;
    }

    @Override
    public void initFragment(View view) {
        this.view=view;
        final TouchImageView touchImageView=view.findViewById(R.id.tiv_pic);
        ImageView ivGif=view.findViewById(R.id.iv_gif);
        if (CommonTool.isGif(picUrl)){
            ivGif.setVisibility(View.VISIBLE);
            touchImageView.setVisibility(View.GONE);
            ImageLoader.getImageLoader().imageLoader(getContext(),ivGif,picUrl,new ImageConfig.Builder().setGif(true).build());
        }else {
            ivGif.setVisibility(View.GONE);
            touchImageView.setVisibility(View.VISIBLE);
            ImageLoader.getImageLoader().loadBitmap(getContext(), picUrl, new ImageLoadListenerAdapter() {
                @Override
                public void onLoadSuccess(Bitmap bitmap, String url) {
                    touchImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    touchImageView.setImageBitmap(BitmapTool.compressFromMaxSize(bitmap, maxTexture));
                }
            });
        }
        initSlideExitTouch();
        touchImageView.setOnTouchListener(slideExitTouch);
        ivGif.setOnTouchListener(slideExitTouch);
    }


}
