package com.example.weizhenbin.wangebug.eventbus;

/**
 * Created by weizhenbin on 2018/9/26.
 */

public class MessageEvent {
    public int code;
    public Object msg;

    public MessageEvent(int code, Object msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "code=" + code +
                ", msg=" + msg +
                '}';
    }
}
