package com.example.weizhenbin.wangebug.modules.todo.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by weizhenbin on 2018/10/8.
 * 提醒映射
 */
@Entity
public class TBAlarmMapBean {
    @Id
    long id;

    int number;

    String key;//uuid

    public TBAlarmMapBean(long id, int number, String key) {
        this.id = id;
        this.number = number;
        this.key = key;
    }

    public TBAlarmMapBean(int number, String key) {
        this.number = number;
        this.key = key;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getId() {
        return id;
    }
}
