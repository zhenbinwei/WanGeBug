package com.example.weizhenbin.wangebug.base;

import java.io.Serializable;

/**
 * Created by weizhenbin on 2018/10/10.
 */
public enum UrlTypeEnum implements Serializable{
    code(1),news(2);

    private int typeValue;

    UrlTypeEnum(int typeValue) {
      this.typeValue=typeValue;
    }

    public int getTypeValue() {
        return typeValue;
    }
}
