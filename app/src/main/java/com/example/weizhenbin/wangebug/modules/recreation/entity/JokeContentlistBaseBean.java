package com.example.weizhenbin.wangebug.modules.recreation.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by weizhenbin on 2018/8/13.
 */

public class JokeContentlistBaseBean implements MultiItemEntity {

    public final static int TEXT=1;
    public final static int PIC=2;
    public final static int GIF=3;


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
