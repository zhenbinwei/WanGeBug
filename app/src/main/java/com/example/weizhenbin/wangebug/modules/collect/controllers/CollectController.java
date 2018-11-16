package com.example.weizhenbin.wangebug.modules.collect.controllers;

import android.text.TextUtils;

import com.example.weizhenbin.wangebug.base.App;
import com.example.weizhenbin.wangebug.modules.collect.entity.TBCollectBean;
import com.example.weizhenbin.wangebug.modules.collect.entity.TBCollectBean_;

import java.util.List;

import io.objectbox.Box;

/**
 * Created by weizhenbin on 2018/10/10.
 */
public class CollectController {




    public static void addCollect(TBCollectBean tbCollectBean){
        Box<TBCollectBean> tbCollectBeanBox = App.Companion.getApp().getBoxStore().boxFor(TBCollectBean.class);
        tbCollectBeanBox.put(tbCollectBean);
    }


    public static void removeCollect(long id){
        Box<TBCollectBean> tbCollectBeanBox = App.Companion.getApp().getBoxStore().boxFor(TBCollectBean.class);
        tbCollectBeanBox.remove(id);
    }

    public static void removeCollectByTitle(String title){
        Box<TBCollectBean> tbCollectBeanBox = App.Companion.getApp().getBoxStore().boxFor(TBCollectBean.class);
        tbCollectBeanBox.query().equal(TBCollectBean_.title,title).build().remove();
    }
    public static List<TBCollectBean> getCollectList(int page,int pageCount){
        Box<TBCollectBean> tbCollectBeanBox = App.Companion.getApp().getBoxStore().boxFor(TBCollectBean.class);
        return tbCollectBeanBox.query().orderDesc(TBCollectBean_.id).build().find((page-1)*pageCount,pageCount);
    }


    /**
     * 通过标题查询
     * */
    public static boolean isExistByTitle(String title){
        if (TextUtils.isEmpty(title)){
            return false;
        }
        Box<TBCollectBean> tbCollectBeanBox = App.Companion.getApp().getBoxStore().boxFor(TBCollectBean.class);
        TBCollectBean tbCollectBean= tbCollectBeanBox.query().equal(TBCollectBean_.title,title).build().findFirst();
        if (tbCollectBean!=null){
            return true;
        }
        return false;
    }

}
