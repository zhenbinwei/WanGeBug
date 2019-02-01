package com.example.weizhenbin.wangebug.modules.recreation.uis

import android.support.v7.widget.PagerSnapHelper
import android.view.View
import com.example.weizhenbin.wangebug.R
import com.example.weizhenbin.wangebug.base.BaseFragment
import com.example.weizhenbin.wangebug.base.DataResult
import com.example.weizhenbin.wangebug.modules.recreation.adapters.YiYuanVideoListAdapter
import com.example.weizhenbin.wangebug.modules.recreation.controllers.JokeController
import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanBSBDJBean
import com.example.weizhenbin.wangebug.views.LinearRecyclerView



/**
 * Created by weizhenbin on 2018/12/10.
 */
class YiYuanVideoFragment : BaseFragment() {
    var mPagerSnapHelper = PagerSnapHelper()
    private var rvDatas:LinearRecyclerView?=null
    private var dataAdapter:YiYuanVideoListAdapter?=null
    override val contentViewLayoutId: Int
        get() = R.layout.fm_yiyuan_video_list

    override fun initFragment(view: View?) {
       val datas: List<String> = listOf("","","","","","","","","","","","","","","","")

        rvDatas=view?.findViewById(R.id.rv_data_list)
        dataAdapter= YiYuanVideoListAdapter(context,datas)
        rvDatas?.adapter=dataAdapter
        mPagerSnapHelper.attachToRecyclerView(rvDatas)
    }

    override var pageTitle: String?
        get() = "视频"
        set(value) {}

    override fun loadData() {
       JokeController.getYiYuanPicData("41",1,object:DataResult<YiYuanBSBDJBean>{
           override fun onStart() {
           }

           override fun onError(throwable: Throwable) {
           }

           override fun onSuccess(t: YiYuanBSBDJBean?) {
           }
       })
    }
}