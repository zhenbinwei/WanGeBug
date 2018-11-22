package com.example.weizhenbin.wangebug.modules.settings

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.AppCompatCheckBox
import android.text.format.Formatter
import android.view.View
import android.widget.TextView
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseActivity
import com.example.weizhenbin.wangebug.eventbus.EventBusHandler
import com.example.weizhenbin.wangebug.eventbus.EventCode
import com.example.weizhenbin.wangebug.eventbus.MessageEvent
import com.example.weizhenbin.wangebug.tools.DialogTool
import com.example.weizhenbin.wangebug.tools.permission.IFloattingWindowPermissionGrantResult
import com.example.weizhenbin.wangebug.tools.permission.PermissionTool
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesConfig
import com.example.weizhenbin.wangebug.tools.preferences.PreferencesTool
import com.example.weizhenbin.wangebug.views.TitleBar
import com.example.weizhenbin.wangebug.views.floatingwindow.TodoFloatingWindowManager
import com.tencent.bugly.beta.Beta


/**
 * Created by weizhenbin on 2018/9/18.
 */

class SettingsActivity : BaseActivity(), View.OnClickListener {


    private lateinit var cbTodoOpen: AppCompatCheckBox
    private lateinit var cbHideTabOpen: AppCompatCheckBox
    private lateinit var cbTodoNotificationSoundOpen: AppCompatCheckBox
    lateinit var tbTitle: TitleBar
    lateinit var tvCleanCache: TextView
    lateinit var tvCheckVersion: TextView
    lateinit var tvAbout: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initViews()
        initEvent()
        initStatus()
        initData()
    }

    private fun initData() {
        initVersionInfo()
    }

    /**
     * 初始化版本信息
     */
    private fun initVersionInfo() {
        val upgradeInfo = Beta.getUpgradeInfo() ?: return
        val sizeStr = Formatter.formatShortFileSize(this@SettingsActivity, upgradeInfo.fileSize)
        tvCheckVersion.text = getString(R.string.new_version_string, sizeStr)
    }

    /**
     * 初始化状态
     */
    private fun initStatus() {
        cbTodoOpen.isChecked = PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, false)
        cbHideTabOpen.isChecked = PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_HIDE_TAB, true)
        cbTodoNotificationSoundOpen.isChecked = PreferencesTool.getBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND, false)
    }

    private fun initEvent() {
        cbTodoOpen.setOnClickListener(this)
        cbHideTabOpen.setOnClickListener(this)
        tbTitle.setLeftOnClickListener { finish() }
        tvCleanCache.setOnClickListener(this)
        cbTodoNotificationSoundOpen.setOnClickListener(this)
        tvCheckVersion.setOnClickListener(this)
        tvAbout.setOnClickListener(this)
    }

    private fun initViews() {
        cbTodoOpen = findViewById(R.id.cb_todo_open)
        cbHideTabOpen = findViewById(R.id.cb_hide_tab_open)
        tbTitle = findViewById(R.id.tb_title)
        tvCleanCache = findViewById(R.id.tv_clean_cache)
        cbTodoNotificationSoundOpen = findViewById(R.id.cb_todo_notification_sound_open)
        tvCheckVersion = findViewById(R.id.tv_check_version)
        tvAbout = findViewById(R.id.tv_about)
    }

    override fun onClick(v: View) {
        if (v === cbTodoOpen) {
            if (cbTodoOpen.isChecked) {
                openFloatingWindow()
            } else {
                closeFloatingWindow()
            }
        } else if (v === cbHideTabOpen) {
            if (cbHideTabOpen.isChecked) {
                openHideTab()
            } else {
                closeHideTab()
            }
        } else if (v === tvCleanCache) {

        } else if (v === cbTodoNotificationSoundOpen) {
            if (cbTodoNotificationSoundOpen.isChecked) {
                PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND, true)
            } else {
                PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_NOTIFICATION_SOUND, false)
            }
        } else if (v === tvCheckVersion) {
            Beta.checkUpgrade()
        } else if (v === tvAbout) {
            AboutActivity.startActivity(this)
            /*  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getWindow().setWindowAnimations(R.style.WindowFadeInOutAnim);
            recreate();*/
        }
    }

    private fun closeHideTab() {
        PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_HIDE_TAB, false)
        cbHideTabOpen.isChecked = false
        EventBusHandler.post(MessageEvent(EventCode.HIDE_TAB_CODE, false))
    }

    private fun openHideTab() {
        PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_HIDE_TAB, true)
        cbHideTabOpen.isChecked = true
        EventBusHandler.post(MessageEvent(EventCode.HIDE_TAB_CODE, true))
    }


    private fun closeFloatingWindow() {
        cbTodoOpen.isChecked = false
        PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, false)
        TodoFloatingWindowManager.manager.hideFloatingWindow()
    }

    private fun openFloatingWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0以上必须开启悬浮窗权限
            if (PermissionTool.checkWindowPermission(this@SettingsActivity)) {
                cbTodoOpen.isChecked = true
                PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, true)
                TodoFloatingWindowManager.manager.showFloatingWindow()
            } else {
                //引导权限申请
                DialogTool.showAlertDialog(this@SettingsActivity, getString(R.string.permission_request_string), getString(R.string.floating_window_permission_request_describe_string), getString(R.string.open_string), { _, _ ->
                    PermissionTool.with(this@SettingsActivity).setiFloatingWindowPermissionGrantResult(object : IFloattingWindowPermissionGrantResult{
                        override fun onGrantResult(isGrant: Boolean) {
                                if (PermissionTool.checkWindowPermission(this@SettingsActivity)) {
                                    cbTodoOpen.isChecked = true
                                    PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, true)
                                    TodoFloatingWindowManager.manager.showFloatingWindow()
                                } else {
                                    cbTodoOpen.isChecked = false
                                    PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, false)
                                    TodoFloatingWindowManager.manager.hideFloatingWindow()
                                }
                        }
                    }) .requestFloatingWindowPermission()
                }, getString(R.string.cancel_string), { _, _ ->
                    cbTodoOpen.isChecked = false
                    PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, false)
                    TodoFloatingWindowManager.manager.hideFloatingWindow()
                }, false)
            }
        } else {
            cbTodoOpen.isChecked = true
            PreferencesTool.putBoolean(PreferencesConfig.KEY_OPEN_FLOATING_WINDOW, true)
            TodoFloatingWindowManager.manager.showFloatingWindow()
        }
    }

    companion object {


        fun startActivity(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }


}
