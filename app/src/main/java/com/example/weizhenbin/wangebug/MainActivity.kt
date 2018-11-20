package com.example.weizhenbin.wangebug

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import com.example.weizhenbin.wangebug.base.BaseActivity
import com.example.weizhenbin.wangebug.interfaces.IOpenMenuHandler
import com.example.weizhenbin.wangebug.modules.code.uis.CodeFragment
import com.example.weizhenbin.wangebug.modules.collect.uis.CollectListActivity
import com.example.weizhenbin.wangebug.modules.news.uis.NewsFragment
import com.example.weizhenbin.wangebug.modules.recreation.uis.RecreationFragment
import com.example.weizhenbin.wangebug.modules.settings.SettingsActivity
import com.example.weizhenbin.wangebug.modules.todo.uis.TodoListActivity
import com.example.weizhenbin.wangebug.tools.permission.PermissionTool
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool
import com.example.weizhenbin.wangebug.views.floatingwindow.TodoFloatingWindowManager
import com.example.weizhenbin.wangebug.views.remindbar.RemindBar

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, IOpenMenuHandler {

     var currentModule = CODE_MODULE

    lateinit var drawer: DrawerLayout
    lateinit var fragmentManager: FragmentManager
     var newsFragment: NewsFragment? = null
     var codeFragment: CodeFragment? = null
     var recreationFragment: RecreationFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //测试
        drawer = findViewById(R.id.drawer_layout)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //将侧边栏顶部延伸至status bar
            drawer.fitsSystemWindows = true
            //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
            drawer.clipToPadding = false
        }
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        initFragment(savedInstanceState)
        openFloatingWindow()
    }


    /**
     * 判断权限 开启悬浮窗
     */
    private fun openFloatingWindow() {
        if (PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, false)) {
            if (PermissionTool.checkWindowPermission(this@MainActivity)) {
                TodoFloatingWindowManager.showFloatingWindow()
            } else {
                RemindBar.make(drawer, getString(R.string.no_floating_window_permission_remind_string), RemindBar.LENGTH_LONG)
                        .setAction(getString(R.string.open_string)) {
                            PermissionTool.with(this@MainActivity).setiFloattingWindowPermissionGrantResult {
                                if (PermissionTool.checkWindowPermission(this@MainActivity)) {
                                    PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, true)
                                    TodoFloatingWindowManager.showFloatingWindow()
                                } else {
                                    PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, false)
                                    TodoFloatingWindowManager.hideFloatingWindow()
                                }
                            }.requestFloattingWindowPermission()
                        }.show()
            }
        }
    }


    private fun initFragment(savedInstanceState: Bundle?) {
        fragmentManager = supportFragmentManager
        if (savedInstanceState != null) {
            newsFragment = fragmentManager.findFragmentByTag(NewsFragment::class.java.simpleName) as NewsFragment
            codeFragment = fragmentManager.findFragmentByTag(CodeFragment::class.java.simpleName) as CodeFragment
            recreationFragment = fragmentManager.findFragmentByTag(RecreationFragment::class.java.simpleName) as RecreationFragment
        }
        if (newsFragment == null) {
            newsFragment = NewsFragment()
        }
        if (codeFragment == null) {
            codeFragment = CodeFragment()
        }
        if (recreationFragment == null) {
            recreationFragment = RecreationFragment()
        }
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        hideAllFragment()
        if (savedInstanceState != null) {
            currentModule = savedInstanceState.getInt("currentModule")
            when (currentModule) {
                RECREATION_MODULE -> {
                    if (!recreationFragment!!.isAdded) {
                        transaction.add(R.id.fl_content, recreationFragment, RecreationFragment::class.java.simpleName)
                    }
                    transaction.show(recreationFragment)
                    transaction.commit()
                }
                NEWS_MODULE -> {
                    if (!newsFragment!!.isAdded) {
                        transaction.add(R.id.fl_content, newsFragment, NewsFragment::class.java.simpleName)
                    }
                    transaction.show(newsFragment)
                    transaction.commit()
                }
                else -> {
                    if (!codeFragment!!.isAdded) {
                        transaction.add(R.id.fl_content, codeFragment, CodeFragment::class.java.simpleName)
                    }
                    transaction.show(codeFragment)
                    transaction.commit()
                }
            }

        } else {
            if (!codeFragment!!.isAdded) {
                transaction.add(R.id.fl_content, codeFragment, CodeFragment::class.java.simpleName)
            }
            transaction.show(codeFragment)
            transaction.commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("currentModule", currentModule)
        super.onSaveInstanceState(outState)
    }

    private fun hideAllFragment() {
        val transaction = fragmentManager.beginTransaction()
        if (codeFragment != null && codeFragment!!.isAdded) {
            transaction.hide(codeFragment)
        }
        if (newsFragment != null && newsFragment!!.isAdded) {
            transaction.hide(newsFragment)
        }
        if (recreationFragment != null && recreationFragment!!.isAdded) {
            transaction.hide(recreationFragment)
        }
        transaction.commit()
    }

    private fun showFragment(fragment: Fragment?) {
        if (fragment == null) {
            return
        }
        val transaction = fragmentManager.beginTransaction()
        hideAllFragment()
        if (!fragment.isAdded) {
            transaction.add(R.id.fl_content, fragment, fragment.javaClass.simpleName)
        }
        transaction.show(fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            val i = Intent(Intent.ACTION_MAIN)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.addCategory(Intent.CATEGORY_HOME)
            startActivity(i)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_news -> {
                showFragment(newsFragment)
                currentModule = NEWS_MODULE
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_code -> {
                showFragment(codeFragment)
                currentModule = CODE_MODULE
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_recreation -> {
                showFragment(recreationFragment)
                currentModule = RECREATION_MODULE
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.nav_todo -> {
                TodoListActivity.startActivity(this@MainActivity)
                drawer.postDelayed({ drawer.closeDrawer(GravityCompat.START, false) }, 500)
            }
            R.id.nav_settings -> {
                SettingsActivity.startActivity(this@MainActivity)
                drawer.postDelayed({ drawer.closeDrawer(GravityCompat.START, false) }, 500)
            }
            R.id.nav_collect -> {
                CollectListActivity.startActivity(this@MainActivity)
                drawer.postDelayed({ drawer.closeDrawer(GravityCompat.START, false) }, 500)
            }
        }
        return true
    }

    override fun openMenu() {
        drawer.openDrawer(GravityCompat.START)
    }

    companion object {

        private const val CODE_MODULE = 1
        private const val NEWS_MODULE = 2
        private const val RECREATION_MODULE = 3
    }
}
