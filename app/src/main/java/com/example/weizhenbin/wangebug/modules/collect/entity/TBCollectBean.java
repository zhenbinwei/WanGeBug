package com.example.weizhenbin.wangebug.modules.collect.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by weizhenbin on 2018/10/10.
 */
@Entity
public class TBCollectBean {
    @Id
    long id;

    private String title;

    private  String url;

    private int type; //技术1 资讯2

    private long collectTime;

    private String key;//uuid;

    public TBCollectBean(long id, String title, String url, int type, long collectTime, String key) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.type = type;
        this.collectTime = collectTime;
        this.key = key;
    }

    public TBCollectBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(long collectTime) {
        this.collectTime = collectTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
