package com.example.weizhenbin.wangebug.base;

import java.io.Serializable;

/**
 * Created by weizhenbin on 2018/10/10.
 */
public enum UrlTypeEnum implements Serializable{
    unknown(), code(1),news(2);

    private int typeValue;

     UrlTypeEnum(int typeValue) {
      this.typeValue=typeValue;
    }
    UrlTypeEnum() {
    }
    public int getTypeValue() {
        return typeValue;
    }

    public static UrlTypeEnum valueOf(int value) {
        switch (value) {
            case 1:
                return UrlTypeEnum.code;
            case 2:
                return UrlTypeEnum.news;
            default:
                return UrlTypeEnum.unknown;
        }
    }



}
