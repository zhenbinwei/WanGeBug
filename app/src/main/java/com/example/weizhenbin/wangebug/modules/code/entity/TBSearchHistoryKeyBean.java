package com.example.weizhenbin.wangebug.modules.code.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by weizhenbin on 2018/10/8.
 */
@Entity
public class TBSearchHistoryKeyBean {
    @Id
    long id;

    String key;

    public TBSearchHistoryKeyBean(long id, String key) {
        this.id = id;
        this.key = key;
    }

    public TBSearchHistoryKeyBean(String key) {
        this.key = key;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
