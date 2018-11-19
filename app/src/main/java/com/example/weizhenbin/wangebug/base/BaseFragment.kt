package com.example.weizhenbin.wangebug.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by weizhenbin on 18/8/12.
 */

abstract class BaseFragment : Fragment() {


    //Fragment的View加载完毕的标记
    private var isViewCreated = false

    //Fragment对用户可见的标记
    private var isUIVisible = false


    private var rootView: View? = null

    protected abstract val contentViewLayoutId: Int

   open var pageTitle: String? = ""
       get() = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (null != rootView) {
            val parent = rootView!!.parent as ViewGroup
            parent?.removeView(rootView)
        } else {
            rootView = inflater.inflate(contentViewLayoutId, null)
            initFragment(rootView)
        }

        return rootView
    }

    abstract fun initFragment(view: View?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        lazyLoad()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true
            lazyLoad()
        } else {
            isUIVisible = false
        }
    }

    private fun lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData()
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false
            isUIVisible = false
        }
    }


    /***
     * 仅适用viewpager 加载数据
     */
     abstract fun loadData()

}
