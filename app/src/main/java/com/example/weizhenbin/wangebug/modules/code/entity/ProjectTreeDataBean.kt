package com.example.weizhenbin.wangebug.modules.code.entity

import com.example.weizhenbin.wangebug.base.WanAndroidBaseBean

/**
 * Created by weizhenbin on 2018/8/28.
 */

class ProjectTreeDataBean : WanAndroidBaseBean() {


    var data: List<DataBean>? = null

    class DataBean {
        /**
         * children : []
         * courseId : 13
         * id : 294
         * name : 完整项目
         * order : 145000
         * parentChapterId : 293
         * visible : 0
         */

        var courseId: Int = 0
        var id: Int = 0
        var name: String? = null
        var order: Int = 0
        var parentChapterId: Int = 0
        var visible: Int = 0
        var children: List<*>? = null
    }
}
