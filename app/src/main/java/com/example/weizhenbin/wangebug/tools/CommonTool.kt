package com.example.weizhenbin.wangebug.tools

import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by weizhenbin on 2018/8/22.
 */

object CommonTool {
    fun isGif(url: String?): Boolean {
        return if (TextUtils.isEmpty(url)) {
            false
        } else url?.toLowerCase()?.contains(".gif".toLowerCase())?:false
    }

    fun durationFormat(duration:Long):String{
        val formatter= if (duration>1000*60*60) {
              SimpleDateFormat("HH:mm:ss")
        }else{
              SimpleDateFormat("mm:ss")
        }
        formatter.timeZone = TimeZone.getTimeZone("GMT+00:00")
        return formatter.format(duration)
    }
}
