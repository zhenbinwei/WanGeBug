package com.example.weizhenbin.wangebug.modules.recreation.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by weizhenbin on 2018/8/13.
 */

public class JokeContentlistBaseBean implements MultiItemEntity {
    private int type;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
