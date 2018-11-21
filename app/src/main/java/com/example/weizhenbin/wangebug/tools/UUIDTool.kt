package com.example.weizhenbin.wangebug.tools

import java.util.*

/**
 * Created by weizhenbin on 2018/9/21.
 */

object UUIDTool {
    val uuid: String
        get() = UUID.randomUUID().toString().replace("-".toRegex(), "")
}
