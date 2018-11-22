package com.example.weizhenbin.wangebug.views.remindbar

import android.view.View

/**
 * Created by weizhenbin on 2018/9/19.
 */

class RemindBar private constructor() {

    private var barLayout: RemindBarLayout? = null

    companion object {
       const val LENGTH_SHORT = 1500

       const val LENGTH_LONG = 3000

        private var remindBar: RemindBar? = null

        fun make(view: View, msg: String, duration: Int): RemindBarLayout? {
            if (remindBar == null) {
                synchronized(RemindBar::class.java) {
                    if (remindBar == null) {
                        remindBar = RemindBar()
                    }
                }
            }
            if (remindBar!!.barLayout == null) {
                remindBar!!.barLayout = RemindBarLayout(view)
            }
            remindBar!!.barLayout!!.setDuration(duration)
            remindBar!!.barLayout!!.setMsgText(msg)
            return remindBar!!.barLayout
        }
    }


}
