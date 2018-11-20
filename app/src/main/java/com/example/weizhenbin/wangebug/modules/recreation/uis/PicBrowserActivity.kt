package com.example.weizhenbin.wangebug.modules.recreation.uis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.WindowManager
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseActivity
import com.example.weizhenbin.wangebug.views.PhotoViewPager
import java.util.*

/**
 * Created by weizhenbin on 2018/8/20.
 */

class PicBrowserActivity : BaseActivity() {

     lateinit var vpBrowser: PhotoViewPager

     var pics: MutableList<String> = ArrayList()


     var index: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_pic_browser)
        intent?.let {
            pics.addAll(it.getStringArrayListExtra("picList"))
            index = it.getIntExtra("index", 0)
        }
        vpBrowser = findViewById(R.id.vp_browser)
        vpBrowser.adapter = BrowserViewPageAdapter(supportFragmentManager)
        if (index >= 0 && index < pics.size) {
            vpBrowser.currentItem = index
        }
    }


    private inner class BrowserViewPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return PicBrowserFragment.getFragment(pics[position])
        }

        override fun getCount(): Int {
            return pics.size
        }
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.activity_open_out, R.anim.activity_close_out)
    }

    companion object {


        fun startBrowserActivity(context: Context, pics: ArrayList<String>, index: Int) {
            val intent = Intent(context, PicBrowserActivity::class.java)
            intent.putStringArrayListExtra("picList", pics)
            intent.putExtra("index", index)
            context.startActivity(intent)
            if (context is Activity) {
                context.overridePendingTransition(R.anim.activity_open_in, R.anim.activity_close_in)
            }
        }
    }
}
