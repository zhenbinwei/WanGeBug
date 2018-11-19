package com.example.weizhenbin.wangebug.modules.code.uis

import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.ViewPageAdapter
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler
import com.example.weizhenbin.wangebug.eventbus.EventCode
import com.example.weizhenbin.wangebug.eventbus.MessageEvent
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool
import com.example.weizhenbin.wangebug.views.TitleBar
import com.example.weizhenbin.wangebug.views.nestlayout.IRollChange
import com.example.weizhenbin.wangebug.views.nestlayout.NestLayout
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * Created by weizhenbin on 2018/8/6.
 */

class CodeFragment : BaseFragment() {
    lateinit var tbTitle: TitleBar
    lateinit var bnvNavigation: BottomNavigationView
    var modeFragments: MutableList<BaseFragment> = ArrayList()
    lateinit var vpCodeMode: ViewPager
    lateinit var viewPageAdapter: ViewPageAdapter
    lateinit var nlContent: NestLayout
    private var mShowAction: TranslateAnimation? = null
    private var mHiddenAction: TranslateAnimation? = null

    override val contentViewLayoutId: Int
        get() = R.layout.fm_code

    override fun initFragment(view: View?) {
        initViews(view!!)
        initEvent()
        if (modeFragments.isEmpty()) {
            addModes()
        }
        setModes()
        initAnim()
        EventBusHandler.register(this)
        nlContent.post { hideTab(PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_HIDE_TAB, true)) }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBusHandler.unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        Log.d("CodeFragment", "event:$event")
        if (event.code == EventCode.HIDE_TAB_CODE) {
            hideTab(event.msg != null && event.msg === java.lang.Boolean.valueOf(true))
        }
    }


    private fun hideTab(hide: Boolean) {
        if (hide) {
            nlContent.setPadding(0, 0, 0, 0)
        } else {
            nlContent.setPadding(0, 0, 0, bnvNavigation.height)
            bnvNavigation.visibility = View.VISIBLE
        }
    }


    private fun initAnim() {
        mShowAction = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f)
        mShowAction!!.duration = 200
        mHiddenAction = TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f)
        mHiddenAction!!.duration = 200
    }

    override fun loadData() {

    }

    private fun setModes() {
        viewPageAdapter = ViewPageAdapter(childFragmentManager, modeFragments)
        vpCodeMode.adapter = viewPageAdapter
        vpCodeMode.offscreenPageLimit = 2
    }

    private fun addModes() {
        modeFragments.add(CodeHomeFragment.fragment)
        modeFragments.add(CodeSystemFragment.fragment)
        modeFragments.add(CodeProjectFragment.fragment)
    }

    private fun initViews(view: View) {
        tbTitle = view.findViewById(R.id.tb_title)
        vpCodeMode = view.findViewById(R.id.vp_code_mode)
        bnvNavigation = view.findViewById(R.id.bnv_navigation)
        nlContent = view.findViewById(R.id.nl_content)
    }

    private fun initEvent() {
        tbTitle.setLeftOnClickListener {
            if (activity is IOpenMenuHandler) {
                (activity as IOpenMenuHandler).openMenu()
            }
        }
        tbTitle.setRightOnClickListener { CodeSearchActivity.startActivity(context!!) }
        bnvNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> vpCodeMode.setCurrentItem(0, false)
                R.id.action_system -> vpCodeMode.setCurrentItem(1, false)
                R.id.action_project -> vpCodeMode.setCurrentItem(2, false)
            }
            true
        }
        vpCodeMode.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bnvNavigation.selectedItemId = R.id.action_home
                    1 -> bnvNavigation.selectedItemId = R.id.action_system
                    2 -> bnvNavigation.selectedItemId = R.id.action_project
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        nlContent.setiRollChange(IRollChange { dy ->
            if (!PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_HIDE_TAB, true)) {
                return@IRollChange
            }
            if (Math.abs(dy) >= 4) {
                if (dy > 0) {
                    //隐藏
                    if (bnvNavigation.visibility == View.VISIBLE) {
                        bnvNavigation.startAnimation(mHiddenAction)
                        bnvNavigation.visibility = View.GONE
                    }
                } else if (dy < 0) {
                    //显示
                    if (bnvNavigation.visibility == View.GONE) {
                        bnvNavigation.startAnimation(mShowAction)
                        bnvNavigation.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}
