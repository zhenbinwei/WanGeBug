package com.example.weizhenbin.wangebug.modules.code.entity

import android.text.TextUtils

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.example.weizhenbin.wangebug.base.WanAndroidBaseBean

/**
 * Created by weizhenbin on 2018/8/11.
 */

class ArticleListDataBean : WanAndroidBaseBean() {


    var data: DataBean? = null


    class DataBean {


        var curPage: Int = 0
        var offset: Int = 0
        var isOver: Boolean = false
        var pageCount: Int = 0
        var size: Int = 0
        var total: Int = 0
        var datas: List<DatasBean>? = null

        class DatasBean : MultiItemEntity {


            var apkLink: String? = null
            var author: String? = null
            var chapterId: Int = 0
            var chapterName: String? = null
            var isCollect: Boolean = false
            var courseId: Int = 0
            var desc: String? = null
            var envelopePic: String? = null
            var isFresh: Boolean = false
            var id: Int = 0
            var link: String? = null
            var niceDate: String? = null
            var origin: String? = null
            var projectLink: String? = null
            var publishTime: Long = 0
            var superChapterId: Int = 0
            var superChapterName: String? = null
            var title: String? = null
            var type: Int = 0
            var userId: Int = 0
            var visible: Int = 0
            var zan: Int = 0
            var tags: List<*>? = null

            override fun getItemType(): Int {

                return if (TextUtils.isEmpty(envelopePic)) {
                    NO_PIC
                } else {
                    HAS_PIC
                }
            }

            companion object {

                const val HAS_PIC = 1
                const val NO_PIC = 0
            }
        }
    }
}
