package com.example.weizhenbin.wangebug.modules.recreation.uis

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView

import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.image.ImageConfig
import com.example.weizhenbin.wangebug.image.ImageLoadListenerAdapter
import com.example.weizhenbin.wangebug.image.ImageLoader
import com.example.weizhenbin.wangebug.tools.BitmapTool
import com.example.weizhenbin.wangebug.tools.CommonTool
import com.example.weizhenbin.wangebug.views.SlideExitTouch
import com.example.weizhenbin.wangebug.views.TouchImageView

/**
 * Created by weizhenbin on 2018/8/20.
 */

class PicBrowserFragment : BaseFragment() {


     var picUrl: String? = null
    private var maxTexture = 0
    lateinit var slideExitTouch: SlideExitTouch
    internal var view: View? = null


    override val contentViewLayoutId: Int
        get() = R.layout.fm_pic_browser

    override fun loadData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        maxTexture = BitmapTool.glesTextureLimitEqualAboveLollipop
        if (arguments != null) {
            picUrl = arguments!!.getString("Url")
        }
    }

    private fun initSlideExitTouch() {
        slideExitTouch = SlideExitTouch(context)
        slideExitTouch.setStateListener(object : SlideExitTouch.IStateListener {
            override fun onMove(offset: Int) {
                val alpha = Math.abs(offset)
                if (alpha <= 765) {
                    view!!.background.mutate().alpha = 255 - alpha / 3
                } else {
                    view!!.background.mutate().alpha = 0
                }
            }

            override fun onFinish() {
                view!!.background.mutate().alpha = 0
                if (activity != null) {
                    activity!!.finish()
                }
            }

            override fun onUp() {
                view!!.background.mutate().alpha = 255
            }
        })
    }

    override fun initFragment(view: View?) {
        this.view = view
        val touchImageView = view!!.findViewById<TouchImageView>(R.id.tiv_pic)
        val ivGif = view.findViewById<ImageView>(R.id.iv_gif)
        if (CommonTool.isGif(picUrl)) {
            ivGif.visibility = View.VISIBLE
            touchImageView.visibility = View.GONE
            ImageLoader.getImageLoader().imageLoader(context, ivGif, picUrl, ImageConfig.Builder().setGif(true).build())
        } else {
            ivGif.visibility = View.GONE
            touchImageView.visibility = View.VISIBLE
            ImageLoader.getImageLoader().loadBitmap(context, picUrl, object : ImageLoadListenerAdapter() {
                override fun onLoadSuccess(bitmap: Bitmap, url: String) {
                    touchImageView.scaleType = ImageView.ScaleType.FIT_XY
                    touchImageView.setImageBitmap(BitmapTool.compressFromMaxSize(bitmap, maxTexture))
                }
            })
        }
        initSlideExitTouch()
        touchImageView.setOnTouchListener(slideExitTouch)
        ivGif.setOnTouchListener(slideExitTouch)
    }

    companion object {

        fun getFragment(picUrl: String): PicBrowserFragment {
            val picBrowserFragment = PicBrowserFragment()
            val bundle = Bundle()
            bundle.putString("Url", picUrl)
            picBrowserFragment.arguments = bundle
            return picBrowserFragment
        }
    }


}
