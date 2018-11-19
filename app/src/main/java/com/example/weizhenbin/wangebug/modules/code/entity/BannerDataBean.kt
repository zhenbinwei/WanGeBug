package com.example.weizhenbin.wangebug.modules.code.entity

import com.example.weizhenbin.wangebug.base.WanAndroidBaseBean

/**
 * Created by weizhenbin on 2018/8/24.
 */

class BannerDataBean : WanAndroidBaseBean() {

    var data: List<DataBean>? = null

    class DataBean {
        /**
         * desc : 通过聊天机器人打造智能化工作流
         * id : 17
         * imagePath : http://www.wanandroid.com/blogimgs/dd6017a9-f79b-45e3-ae1b-5719a17b0cac.png
         * isVisible : 1
         * order : 0
         * title : 通过聊天机器人打造智能化工作流
         * type : 0
         * url : https://bearychat.com?utm_source=wanandroid&amp;utm_medium=banner&amp;utm_campaign=pc
         */

        var desc: String? = null
        var id: Int = 0
        var imagePath: String? = null
        var isVisible: Int = 0
        var order: Int = 0
        var title: String? = null
        var type: Int = 0
        var url: String? = null
    }
}
