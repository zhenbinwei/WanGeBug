package com.example.weizhenbin.wangebug.tools

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Created by weizhenbin on 2018/2/6.
 */

object StatusTool {
    private var STATUS_BAR_HEIGHT: Int = 0


    /**----------------------魅族---------------------------- */

    private var mSetStatusBarColorIcon: Method? = null
    private var mSetStatusBarDarkIcon: Method? = null
    private var mStatusBarColorFiled: Field? = null
    private var SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 0

    /**仅使用小米 */
    fun setStatusBarDarkMode(darkmode: Boolean, activity: Activity) {
        val clazz = activity.window.javaClass
        try {
            var darkModeFlag = 0
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            extraFlagField.invoke(activity.window, if (darkmode) darkModeFlag else 0, darkModeFlag)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    init {
        try {
            mSetStatusBarColorIcon = Activity::class.java.getMethod("setStatusBarDarkIcon", Int::class.javaPrimitiveType)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }

        try {
            mSetStatusBarDarkIcon = Activity::class.java.getMethod("setStatusBarDarkIcon", Boolean::class.javaPrimitiveType)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }

        try {
            mStatusBarColorFiled = WindowManager.LayoutParams::class.java.getField("statusBarColor")
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        try {
            val field = View::class.java.getField("SYSTEM_UI_FLAG_LIGHT_STATUS_BAR")
            SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = field.getInt(null)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 魅族专用
     * 判断颜色是否偏黑色
     *
     * @param color 颜色
     * @param level 级别
     * @return
     */
    fun isBlackColor(color: Int, level: Int): Boolean {
        val grey = toGrey(color)
        return grey < level
    }

    /**
     *
     * 魅族专用
     *
     * 颜色转换成灰度值
     *
     * @param rgb 颜色
     * @return　灰度值
     */
    fun toGrey(rgb: Int): Int {
        val blue = rgb and 0x000000FF
        val green = rgb and 0x0000FF00 shr 8
        val red = rgb and 0x00FF0000 shr 16
        return red * 38 + green * 75 + blue * 15 shr 7
    }

    /**
     * 魅族专用
     *
     * 设置状态栏字体图标颜色
     *
     * @param activity 当前activity
     * @param color    颜色
     */
    fun setStatusBarDarkIcon(activity: Activity, color: Int) {
        if (mSetStatusBarColorIcon != null) {
            try {
                mSetStatusBarColorIcon!!.invoke(activity, color)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            val whiteColor = isBlackColor(color, 50)
            if (mStatusBarColorFiled != null) {
                setStatusBarDarkIcon(activity, whiteColor, whiteColor)
                setStatusBarDarkIcon(activity.window, color)
            } else {
                setStatusBarDarkIcon(activity, whiteColor)
            }
        }
    }

    /**
     * 魅族专用
     *
     * 设置状态栏字体图标颜色(只限全屏非activity情况)
     *
     * @param window 当前窗口
     * @param color  颜色
     */
    fun setStatusBarDarkIcon(window: Window, color: Int) {
        try {
            setStatusBarColor(window, color)
            if (Build.VERSION.SDK_INT > 22) {
                setStatusBarDarkIcon(window.decorView, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 魅族专用
     *
     * 设置状态栏字体图标颜色
     *
     * @param activity 当前activity
     * @param dark     是否深色 true为深色 false 为白色
     */
    fun setStatusBarDarkIcon(activity: Activity, dark: Boolean) {
        setStatusBarDarkIcon(activity, dark, true)
    }

    /***
     * 魅族专用
     */
    private fun changeMeizuFlag(winParams: WindowManager.LayoutParams, flagName: String, on: Boolean): Boolean {
        try {
            val f = winParams.javaClass.getDeclaredField(flagName)
            f.isAccessible = true
            val bits = f.getInt(winParams)
            val f2 = winParams.javaClass.getDeclaredField("meizuFlags")
            f2.isAccessible = true
            var meizuFlags = f2.getInt(winParams)
            val oldFlags = meizuFlags
            meizuFlags= if (on) {
                 meizuFlags or bits
            } else {
                 meizuFlags and bits.inv()
            }
            if (oldFlags != meizuFlags) {
                f2.setInt(winParams, meizuFlags)
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * 魅族专用
     *
     * 设置状态栏颜色
     *
     * @param view
     * @param dark
     */
    private fun setStatusBarDarkIcon(view: View, dark: Boolean) {
        val oldVis = view.systemUiVisibility
        var newVis = oldVis
        if (dark) {
            newVis = newVis or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            newVis = newVis and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
        if (newVis != oldVis) {
            view.systemUiVisibility = newVis
        }
    }

    /**
     * 魅族专用
     *
     * 设置状态栏颜色
     *
     * @param window
     * @param color
     */
    private fun setStatusBarColor(window: Window, color: Int) {
        val winParams = window.attributes
        if (mStatusBarColorFiled != null) {
            try {
                val oldColor = mStatusBarColorFiled!!.getInt(winParams)
                if (oldColor != color) {
                    mStatusBarColorFiled!!.set(winParams, color)
                    window.attributes = winParams
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 魅族专用
     *
     * 设置状态栏字体图标颜色(只限全屏非activity情况)
     *
     * @param window 当前窗口
     * @param dark   是否深色 true为深色 false 为白色
     */
    fun setStatusBarDarkIcon(window: Window, dark: Boolean) {
        if (Build.VERSION.SDK_INT < 23) {
            changeMeizuFlag(window.attributes, "MEIZU_FLAG_DARK_STATUS_BAR_ICON", dark)
        } else {
            val decorView = window.decorView
            if (decorView != null) {
                setStatusBarDarkIcon(decorView, dark)
                setStatusBarColor(window, 0)
            }
        }
    }

    /***
     * 魅族专用
     */
    private fun setStatusBarDarkIcon(activity: Activity, dark: Boolean, flag: Boolean) {
        if (mSetStatusBarDarkIcon != null) {
            try {
                mSetStatusBarDarkIcon!!.invoke(activity, dark)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            if (flag) {
                setStatusBarDarkIcon(activity.window, dark)
            }
        }
    }

    /**
     * oppo手机
     */
    fun setOPPOLightStatusBarIcon(window: Window?, lightMode: Boolean) {
        val SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010
        if (window == null) {
            return
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        var vis = window.decorView.systemUiVisibility

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (lightMode) {

                vis = vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            } else {

                vis = vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()

            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            if (lightMode) {

                vis = vis or SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT

            } else {

                vis = vis and SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT.inv()

            }

        }

        window.decorView.systemUiVisibility = vis

    }

    fun getStatusBarHeight(context: Context): Int {
        if (STATUS_BAR_HEIGHT <= 0) {
            try {
                val c = Class.forName("com.android.internal.R\$dimen")
                val obj = c.newInstance()
                val field = c.getField("status_bar_height")
                val x = Integer.parseInt(field.get(obj).toString())
                STATUS_BAR_HEIGHT = context.resources.getDimensionPixelSize(x)
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

        }
        return STATUS_BAR_HEIGHT
    }

}
