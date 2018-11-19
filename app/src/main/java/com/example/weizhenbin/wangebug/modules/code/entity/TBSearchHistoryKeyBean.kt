package com.example.weizhenbin.wangebug.modules.code.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by weizhenbin on 2018/10/8.
 */
@Entity
class TBSearchHistoryKeyBean {
    @Id
    var id: Long = 0

    var key: String

    constructor(id: Long, key: String) {
        this.id = id
        this.key = key
    }

    constructor(key: String) {
        this.key = key
    }
}
