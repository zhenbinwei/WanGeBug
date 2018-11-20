package com.example.weizhenbin.wangebug.modules.todo.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by weizhenbin on 2018/10/8.
 * 提醒映射
 */
@Entity
class TBAlarmMapBean {
    @Id
    var id: Long = 0

    var number: Int = 0

    var key: String//uuid

    constructor(id: Long, number: Int, key: String) {
        this.id = id
        this.number = number
        this.key = key
    }

    constructor(number: Int, key: String) {
        this.number = number
        this.key = key
    }
}
