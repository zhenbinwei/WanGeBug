package com.example.weizhenbin.wangebug.modules.code.entity

import com.example.weizhenbin.wangebug.base.WanAndroidBaseBean

/**
 * Created by weizhenbin on 2018/8/31.
 */

class SearchHotKeyDataBean : WanAndroidBaseBean() {

    var data: List<DataBean>? = null

    class DataBean {
        /**
         * id : 6
         * link :
         * name : 面试
         * order : 1
         * visible : 1
         */

        var id: Int = 0
        var link: String? = null
        var name: String? = null
        var order: Int = 0
        var visible: Int = 0
    }
}
