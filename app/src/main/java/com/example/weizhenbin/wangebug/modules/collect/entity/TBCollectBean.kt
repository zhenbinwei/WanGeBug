package com.example.weizhenbin.wangebug.modules.collect.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by weizhenbin on 2018/10/10.
 */
@Entity
class TBCollectBean {
    @Id
    var id: Long = 0

    var title: String? = null

    var url: String? = null

    var type: Int = 0 //技术1 资讯2

    var collectTime: Long = 0

    var key: String? = null//uuid;

    constructor(id: Long, title: String, url: String, type: Int, collectTime: Long, key: String) {
        this.id = id
        this.title = title
        this.url = url
        this.type = type
        this.collectTime = collectTime
        this.key = key
    }

    constructor()
}
