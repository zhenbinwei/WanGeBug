package com.example.weizhenbin.wangebug.modules.news.entity

import com.example.weizhenbin.wangebug.base.YiYuanBaseBean

/**
 * Created by weizhenbin on 2018/8/23.
 */

class YiYuanNewsChannelBean : YiYuanBaseBean() {


    var showapi_res_body: ShowapiResBodyBean? = null

    class ShowapiResBodyBean {

        var totalNum: Int = 0
        var ret_code: Int = 0
        var channelList: List<ChannelListBean>? = null

        class ChannelListBean {
            /**
             * channelId : 5572a108b3cdc86cf39001cd
             * name : 国内焦点
             */

            var channelId: String? = null
            var name: String? = null
        }
    }
}
