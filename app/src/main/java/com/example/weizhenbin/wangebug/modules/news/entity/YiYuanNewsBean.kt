package com.example.weizhenbin.wangebug.modules.news.entity

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.example.weizhenbin.wangebug.base.YiYuanBaseBean

/**
 * Created by weizhenbin on 2018/8/22.
 */

class YiYuanNewsBean : YiYuanBaseBean() {


    var showapi_res_body: ShowapiResBodyBean? = null

    class ShowapiResBodyBean {


        var pagebean: PagebeanBean? = null
        var ret_code: Int = 0

        class PagebeanBean {


            var allNum: Int = 0
            var maxResult: Int = 0
            var allPages: Int = 0
            var currentPage: Int = 0
            var contentlist: List<ContentlistBean>? = null

            val isLastPage: Boolean
                get() = currentPage >= allPages

            class ContentlistBean : MultiItemEntity {

                var id: String? = null
                var channelId: String? = null
                var isHavePic: Boolean = false
                var pubDate: String? = null
                var title: String? = null
                var source: String? = null
                var channelName: String? = null
                var desc: String? = null
                var link: String? = null
                var allList: List<String>? = null
                var imageurls: List<ImageurlsBean>? = null

                override fun getItemType(): Int {
                    return if (imageurls != null) {
                        if (imageurls!!.size < 1) {
                            NO_PIC
                        } else if (imageurls!!.size < 2) {
                            SINGLE_PIC
                        } else {
                            MULTI_PIC
                        }
                    } else {
                        NO_PIC
                    }
                }

                class ImageurlsBean {
                    /**
                     * url : http://n.sinaimg.cn/news/crawl/612/w372h240/20180822/VoIj-hhzsnec1993034.jpg
                     * height : 0
                     * width : 0
                     */

                    var url: String? = null
                    var height: Int = 0
                    var width: Int = 0
                }

                companion object {

                    val SINGLE_PIC = 1
                    val MULTI_PIC = 2
                    val NO_PIC = 0
                }
            }
        }
    }
}
