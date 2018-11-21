package com.example.weizhenbin.wangebug.tools

import android.text.TextUtils

/**
 * Created by weizhenbin on 2018/8/22.
 */

object CommonTool {
    fun isGif(url: String?): Boolean {
        return if (TextUtils.isEmpty(url)) {
            false
        } else url?.toLowerCase()?.contains(".gif".toLowerCase())?:false
    }
}
