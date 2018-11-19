package com.example.weizhenbin.wangebug.modules.code.entity

import com.example.weizhenbin.wangebug.base.WanAndroidBaseBean

import java.io.Serializable

/**
 * Created by weizhenbin on 2018/8/30.
 */

class SystemTreeDataBean : WanAndroidBaseBean() {

    var data: List<DataBean>? = null

    class DataBean : Serializable {
        /**
         * children : [{"children":[],"courseId":13,"id":60,"name":"Android Studio相关","order":1000,"parentChapterId":150,"visible":1},{"children":[],"courseId":13,"id":169,"name":"gradle","order":1001,"parentChapterId":150,"visible":1},{"children":[],"courseId":13,"id":269,"name":"官方发布","order":1002,"parentChapterId":150,"visible":1}]
         * courseId : 13
         * id : 150
         * name : 开发环境
         * order : 1
         * parentChapterId : 0
         * visible : 1
         */

        var courseId: Int = 0
        var id: Int = 0
        var name: String? = null
        var order: Int = 0
        var parentChapterId: Int = 0
        var visible: Int = 0
        var children: List<ChildrenBean>? = null

        class ChildrenBean : Serializable {
            /**
             * children : []
             * courseId : 13
             * id : 60
             * name : Android Studio相关
             * order : 1000
             * parentChapterId : 150
             * visible : 1
             */

            var courseId: Int = 0
            var id: Int = 0
            var name: String? = null
            var order: Int = 0
            var parentChapterId: Int = 0
            var visible: Int = 0

        }
    }
}
