package com.example.weizhenbin.wangebug.modules.code.entity;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.weizhenbin.wangebug.base.WanAndroidBaseBean;

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/11.
 */

public class ArticleListDataBean extends WanAndroidBaseBean{

    /**
     * data : {"curPage":2,"datas":[{"apkLink":"","author":"tonnyl","chapterId":257,"chapterName":"Span","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3287,"link":"https://tonnyl.github.io/Spantastic-Text-Styling-With-Spans/","niceDate":"2018-08-20","origin":"","projectLink":"","publishTime":1534765463000,"superChapterId":168,"superChapterName":"基础知识","tags":[],"title":"探索 Android 中的 Span","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":" crazysunj","chapterId":67,"chapterName":"网络基础","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3286,"link":"https://juejin.im/post/5b717dd5f265da27e100dc8b","niceDate":"2018-08-20","origin":"","projectLink":"","publishTime":1534765423000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"玩一玩Android下载框架","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"Hohohong ","chapterId":233,"chapterName":"framework-四大组件","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3285,"link":"https://www.jianshu.com/p/e42b638944ae","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534657051000,"superChapterId":171,"superChapterName":"framework","tags":[],"title":"Android窗口机制（一）初识Android的窗口结构","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"silentleaf","chapterId":302,"chapterName":"ANR","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3284,"link":"https://www.jianshu.com/p/30c1a5ad63a3","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534656914000,"superChapterId":77,"superChapterName":"热门专题","tags":[],"title":"Android应用ANR分析","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"ZJ_Rocky","chapterId":249,"chapterName":"干货资源","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3283,"link":"https://www.jianshu.com/p/0680be542f6e","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534656819000,"superChapterId":249,"superChapterName":"干货资源","tags":[],"title":"Android高级进阶知识（这是总目录索引）","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"nanchen2251","chapterId":73,"chapterName":"面试相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3282,"link":"https://www.jianshu.com/p/041c77d4a200","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534656578000,"superChapterId":77,"superChapterName":"热门专题","tags":[],"title":"说说最近一周的面试和想法","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"咻咻ing","chapterId":318,"chapterName":"搭建博客","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3281,"link":"https://xiuxiuing.gitee.io/blog/2018/08/10/blogsetting/","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534644175000,"superChapterId":203,"superChapterName":"延伸技术","tags":[],"title":"Hexo even主题博客配置","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"咻咻ing","chapterId":318,"chapterName":"搭建博客","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3279,"link":"https://xiuxiuing.gitee.io/blog/2018/08/08/giteepage/","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534644133000,"superChapterId":203,"superChapterName":"延伸技术","tags":[],"title":"使用Gitee+Hexo搭建个人博客","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"小编","chapterId":360,"chapterName":"小编发布","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3230,"link":"http://www.wanandroid.com/blog/show/2271","niceDate":"2018-08-18","origin":"","projectLink":"","publishTime":1534570500000,"superChapterId":298,"superChapterName":"原创文章","tags":[],"title":"玩 Android 开放 API 之 Todo 清单","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"wjwang0914","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"由于之前自己就想开发一个TODO小应用，刚好看到了鸿神的玩android开放了TODO的API，就抽时间开发了一个TODO的小应用，非常感谢玩Android提供API","envelopePic":"http://www.wanandroid.com/blogimgs/02a131fe-0d71-4a8a-8e53-ac0e9cf652dd.png","fresh":false,"id":3262,"link":"http://www.wanandroid.com/blog/show/2292","niceDate":"2018-08-18","origin":"","projectLink":"https://github.com/wjwang0914/wj-todo-wanandroid/","publishTime":1534570492000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"玩 android 之 开源TODO项目  wj-todo-wanandroid","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"xfhy","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"待办事项APP,纯kotlin开发,接口是鸿洋大大提供的,[地址](http://www.wanandroid.com/blog/show/2),并且我还加入了番茄时间,高效利用好时间.\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/d7ca2faf-ccd9-4e7c-bef1-aacdb8ce1969.png","fresh":false,"id":3278,"link":"http://www.wanandroid.com/blog/show/2299","niceDate":"2018-08-18","origin":"","projectLink":"https://github.com/xfhy/TODO-list","publishTime":1534570407000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"TODO清单 纯Kotlin开发,兼容Android P","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"hyzhan43","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"初学kotlin，所以做这个 豆瓣 app 来练练手，感受了 kotlin 的魅力~\r\nApp 数据采用 豆瓣 api  （如果不小心侵权了,sorry,联系我,我把它删除了,谢谢.）\r\n由于提供 api 数据有限，暂时只实现 电影，和 图书功能。","envelopePic":"http://www.wanandroid.com/blogimgs/43130d65-f8b3-4c65-a6f8-8b0fe675432f.png","fresh":false,"id":3277,"link":"http://www.wanandroid.com/blog/show/2298","niceDate":"2018-08-18","origin":"","projectLink":"https://github.com/hyzhan43/DouBanMovie","publishTime":1534570311000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"kotlin 开发一款 豆瓣 App","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"sunhapper","chapterId":331,"chapterName":"TextView","collect":false,"courseId":13,"desc":"输入@xxx #话题#等特殊字符，实现整体删除，文字高亮等功能的自定义EditText\r\n以及帮助在TextView的图文混排中高效显示gif的工具类","envelopePic":"http://www.wanandroid.com/blogimgs/6367f3db-2180-4d5b-8f8f-86cb236af05a.png","fresh":false,"id":3275,"link":"http://www.wanandroid.com/blog/show/2296","niceDate":"2018-08-18","origin":"","projectLink":"https://github.com/sunhapper/SpEditTool","publishTime":1534568055000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=331"}],"title":"支持表情，@mention，#话题#等功能及显示gif的EditText控件--SpEditTool","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"WANZIzZ","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"一个简单的Kotlin版本Todo客户端，数据来源wanandroid（第一次分享没有写READMEo(╥﹏╥)o）","envelopePic":"http://www.wanandroid.com/blogimgs/97286574-9476-46b0-82d9-b1aa3a82ac4a.png","fresh":false,"id":3273,"link":"http://www.wanandroid.com/blog/show/2295","niceDate":"2018-08-16","origin":"","projectLink":"https://github.com/WANZIzZ/Todo","publishTime":1534412239000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Todo客户端-Kotlin","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"wanglu1209","chapterId":358,"chapterName":"项目基础功能","collect":false,"courseId":13,"desc":"一个简单仿微信朋友圈的图片查看器\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/866fb31b-6bde-48d6-8d61-0d75a2b4dd16.png","fresh":false,"id":3272,"link":"http://www.wanandroid.com/blog/show/2294","niceDate":"2018-08-16","origin":"","projectLink":"https://github.com/wanglu1209/PhotoViewer","publishTime":1534398012000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=358"}],"title":"一个简单仿微信朋友圈的图片查看器 PhotoViewer","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"kelinZhou","chapterId":400,"chapterName":"ViewPager","collect":false,"courseId":13,"desc":"基于ViewPger封装的轮播图，支持无限轮播、支持自定义动画、支持自定义时长。\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/b3ac93c6-b7c2-4987-93a7-96c15ada71e4.png","fresh":false,"id":3271,"link":"http://www.wanandroid.com/blog/show/2293","niceDate":"2018-08-16","origin":"","projectLink":"https://github.com/kelinZhou/Banner","publishTime":1534397931000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=400"}],"title":"基于ViewPger封装的轮播图 Banner","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"我没有三颗心脏","chapterId":73,"chapterName":"面试相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3270,"link":"https://www.jianshu.com/p/939b8a672070","niceDate":"2018-08-15","origin":"","projectLink":"","publishTime":1534299656000,"superChapterId":77,"superChapterName":"热门专题","tags":[],"title":"Java集合必会14问（精选面试题整理）","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"王旋子同学","chapterId":296,"chapterName":"阅读","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3269,"link":"https://www.jianshu.com/p/c11b3ce7bbf0","niceDate":"2018-08-15","origin":"","projectLink":"","publishTime":1534298816000,"superChapterId":203,"superChapterName":"延伸技术","tags":[],"title":"APP走心设计的小思考","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"mtjsoft","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"基于鸿洋大神的玩android开放API&ldquo;http://www.wanandroid.com/blog/show/2&rdquo;完成的《玩android微信小程序版》，一起来学习小程序开发吧。","envelopePic":"http://www.wanandroid.com/blogimgs/81659f72-9b18-4bc2-8e88-695c4a59c730.png","fresh":false,"id":3251,"link":"http://www.wanandroid.com/blog/show/2284","niceDate":"2018-08-13","origin":"","projectLink":"https://github.com/mtjsoft/wanandroid","publishTime":1534160726000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"玩android微信小程序版 wanandroid","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"CnPeng","chapterId":329,"chapterName":"Android 8.0","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3268,"link":"https://www.jianshu.com/p/0462a57134ed","niceDate":"2018-08-13","origin":"","projectLink":"","publishTime":1534160697000,"superChapterId":193,"superChapterName":"5.+高新技术","tags":[],"title":"Android:8.0中未知来源安装权限变更","type":0,"userId":-1,"visible":1,"zan":0}],"offset":20,"over":false,"pageCount":79,"size":20,"total":1563}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }



    public static class DataBean {
        /**
         * curPage : 2
         * datas : [{"apkLink":"","author":"tonnyl","chapterId":257,"chapterName":"Span","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3287,"link":"https://tonnyl.github.io/Spantastic-Text-Styling-With-Spans/","niceDate":"2018-08-20","origin":"","projectLink":"","publishTime":1534765463000,"superChapterId":168,"superChapterName":"基础知识","tags":[],"title":"探索 Android 中的 Span","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":" crazysunj","chapterId":67,"chapterName":"网络基础","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3286,"link":"https://juejin.im/post/5b717dd5f265da27e100dc8b","niceDate":"2018-08-20","origin":"","projectLink":"","publishTime":1534765423000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"玩一玩Android下载框架","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"Hohohong ","chapterId":233,"chapterName":"framework-四大组件","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3285,"link":"https://www.jianshu.com/p/e42b638944ae","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534657051000,"superChapterId":171,"superChapterName":"framework","tags":[],"title":"Android窗口机制（一）初识Android的窗口结构","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"silentleaf","chapterId":302,"chapterName":"ANR","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3284,"link":"https://www.jianshu.com/p/30c1a5ad63a3","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534656914000,"superChapterId":77,"superChapterName":"热门专题","tags":[],"title":"Android应用ANR分析","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"ZJ_Rocky","chapterId":249,"chapterName":"干货资源","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3283,"link":"https://www.jianshu.com/p/0680be542f6e","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534656819000,"superChapterId":249,"superChapterName":"干货资源","tags":[],"title":"Android高级进阶知识（这是总目录索引）","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"nanchen2251","chapterId":73,"chapterName":"面试相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3282,"link":"https://www.jianshu.com/p/041c77d4a200","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534656578000,"superChapterId":77,"superChapterName":"热门专题","tags":[],"title":"说说最近一周的面试和想法","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"咻咻ing","chapterId":318,"chapterName":"搭建博客","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3281,"link":"https://xiuxiuing.gitee.io/blog/2018/08/10/blogsetting/","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534644175000,"superChapterId":203,"superChapterName":"延伸技术","tags":[],"title":"Hexo even主题博客配置","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"咻咻ing","chapterId":318,"chapterName":"搭建博客","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3279,"link":"https://xiuxiuing.gitee.io/blog/2018/08/08/giteepage/","niceDate":"2018-08-19","origin":"","projectLink":"","publishTime":1534644133000,"superChapterId":203,"superChapterName":"延伸技术","tags":[],"title":"使用Gitee+Hexo搭建个人博客","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"小编","chapterId":360,"chapterName":"小编发布","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3230,"link":"http://www.wanandroid.com/blog/show/2271","niceDate":"2018-08-18","origin":"","projectLink":"","publishTime":1534570500000,"superChapterId":298,"superChapterName":"原创文章","tags":[],"title":"玩 Android 开放 API 之 Todo 清单","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"wjwang0914","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"由于之前自己就想开发一个TODO小应用，刚好看到了鸿神的玩android开放了TODO的API，就抽时间开发了一个TODO的小应用，非常感谢玩Android提供API","envelopePic":"http://www.wanandroid.com/blogimgs/02a131fe-0d71-4a8a-8e53-ac0e9cf652dd.png","fresh":false,"id":3262,"link":"http://www.wanandroid.com/blog/show/2292","niceDate":"2018-08-18","origin":"","projectLink":"https://github.com/wjwang0914/wj-todo-wanandroid/","publishTime":1534570492000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"玩 android 之 开源TODO项目  wj-todo-wanandroid","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"xfhy","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"待办事项APP,纯kotlin开发,接口是鸿洋大大提供的,[地址](http://www.wanandroid.com/blog/show/2),并且我还加入了番茄时间,高效利用好时间.\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/d7ca2faf-ccd9-4e7c-bef1-aacdb8ce1969.png","fresh":false,"id":3278,"link":"http://www.wanandroid.com/blog/show/2299","niceDate":"2018-08-18","origin":"","projectLink":"https://github.com/xfhy/TODO-list","publishTime":1534570407000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"TODO清单 纯Kotlin开发,兼容Android P","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"hyzhan43","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"初学kotlin，所以做这个 豆瓣 app 来练练手，感受了 kotlin 的魅力~\r\nApp 数据采用 豆瓣 api  （如果不小心侵权了,sorry,联系我,我把它删除了,谢谢.）\r\n由于提供 api 数据有限，暂时只实现 电影，和 图书功能。","envelopePic":"http://www.wanandroid.com/blogimgs/43130d65-f8b3-4c65-a6f8-8b0fe675432f.png","fresh":false,"id":3277,"link":"http://www.wanandroid.com/blog/show/2298","niceDate":"2018-08-18","origin":"","projectLink":"https://github.com/hyzhan43/DouBanMovie","publishTime":1534570311000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"kotlin 开发一款 豆瓣 App","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"sunhapper","chapterId":331,"chapterName":"TextView","collect":false,"courseId":13,"desc":"输入@xxx #话题#等特殊字符，实现整体删除，文字高亮等功能的自定义EditText\r\n以及帮助在TextView的图文混排中高效显示gif的工具类","envelopePic":"http://www.wanandroid.com/blogimgs/6367f3db-2180-4d5b-8f8f-86cb236af05a.png","fresh":false,"id":3275,"link":"http://www.wanandroid.com/blog/show/2296","niceDate":"2018-08-18","origin":"","projectLink":"https://github.com/sunhapper/SpEditTool","publishTime":1534568055000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=331"}],"title":"支持表情，@mention，#话题#等功能及显示gif的EditText控件--SpEditTool","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"WANZIzZ","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"一个简单的Kotlin版本Todo客户端，数据来源wanandroid（第一次分享没有写READMEo(╥﹏╥)o）","envelopePic":"http://www.wanandroid.com/blogimgs/97286574-9476-46b0-82d9-b1aa3a82ac4a.png","fresh":false,"id":3273,"link":"http://www.wanandroid.com/blog/show/2295","niceDate":"2018-08-16","origin":"","projectLink":"https://github.com/WANZIzZ/Todo","publishTime":1534412239000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"Todo客户端-Kotlin","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"wanglu1209","chapterId":358,"chapterName":"项目基础功能","collect":false,"courseId":13,"desc":"一个简单仿微信朋友圈的图片查看器\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/866fb31b-6bde-48d6-8d61-0d75a2b4dd16.png","fresh":false,"id":3272,"link":"http://www.wanandroid.com/blog/show/2294","niceDate":"2018-08-16","origin":"","projectLink":"https://github.com/wanglu1209/PhotoViewer","publishTime":1534398012000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=358"}],"title":"一个简单仿微信朋友圈的图片查看器 PhotoViewer","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"kelinZhou","chapterId":400,"chapterName":"ViewPager","collect":false,"courseId":13,"desc":"基于ViewPger封装的轮播图，支持无限轮播、支持自定义动画、支持自定义时长。\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/b3ac93c6-b7c2-4987-93a7-96c15ada71e4.png","fresh":false,"id":3271,"link":"http://www.wanandroid.com/blog/show/2293","niceDate":"2018-08-16","origin":"","projectLink":"https://github.com/kelinZhou/Banner","publishTime":1534397931000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=400"}],"title":"基于ViewPger封装的轮播图 Banner","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"我没有三颗心脏","chapterId":73,"chapterName":"面试相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3270,"link":"https://www.jianshu.com/p/939b8a672070","niceDate":"2018-08-15","origin":"","projectLink":"","publishTime":1534299656000,"superChapterId":77,"superChapterName":"热门专题","tags":[],"title":"Java集合必会14问（精选面试题整理）","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"王旋子同学","chapterId":296,"chapterName":"阅读","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3269,"link":"https://www.jianshu.com/p/c11b3ce7bbf0","niceDate":"2018-08-15","origin":"","projectLink":"","publishTime":1534298816000,"superChapterId":203,"superChapterName":"延伸技术","tags":[],"title":"APP走心设计的小思考","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"mtjsoft","chapterId":294,"chapterName":"完整项目","collect":false,"courseId":13,"desc":"基于鸿洋大神的玩android开放API&ldquo;http://www.wanandroid.com/blog/show/2&rdquo;完成的《玩android微信小程序版》，一起来学习小程序开发吧。","envelopePic":"http://www.wanandroid.com/blogimgs/81659f72-9b18-4bc2-8e88-695c4a59c730.png","fresh":false,"id":3251,"link":"http://www.wanandroid.com/blog/show/2284","niceDate":"2018-08-13","origin":"","projectLink":"https://github.com/mtjsoft/wanandroid","publishTime":1534160726000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=294"}],"title":"玩android微信小程序版 wanandroid","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"CnPeng","chapterId":329,"chapterName":"Android 8.0","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":3268,"link":"https://www.jianshu.com/p/0462a57134ed","niceDate":"2018-08-13","origin":"","projectLink":"","publishTime":1534160697000,"superChapterId":193,"superChapterName":"5.+高新技术","tags":[],"title":"Android:8.0中未知来源安装权限变更","type":0,"userId":-1,"visible":1,"zan":0}]
         * offset : 20
         * over : false
         * pageCount : 79
         * size : 20
         * total : 1563
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean implements MultiItemEntity{
            /**
             * apkLink :
             * author : tonnyl
             * chapterId : 257
             * chapterName : Span
             * collect : false
             * courseId : 13
             * desc :
             * envelopePic :
             * fresh : false
             * id : 3287
             * link : https://tonnyl.github.io/Spantastic-Text-Styling-With-Spans/
             * niceDate : 2018-08-20
             * origin :
             * projectLink :
             * publishTime : 1534765463000
             * superChapterId : 168
             * superChapterName : 基础知识
             * tags : []
             * title : 探索 Android 中的 Span
             * type : 0
             * userId : -1
             * visible : 1
             * zan : 0
             */

            private String apkLink;
            private String author;
            private int chapterId;
            private String chapterName;
            private boolean collect;
            private int courseId;
            private String desc;
            private String envelopePic;
            private boolean fresh;
            private int id;
            private String link;
            private String niceDate;
            private String origin;
            private String projectLink;
            private long publishTime;
            private int superChapterId;
            private String superChapterName;
            private String title;
            private int type;
            private int userId;
            private int visible;
            private int zan;
            private List<?> tags;

            public final static int HAS_PIC=1;
            public final static int NO_PIC=0;

            public String getApkLink() {
                return apkLink;
            }

            public void setApkLink(String apkLink) {
                this.apkLink = apkLink;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getChapterId() {
                return chapterId;
            }

            public void setChapterId(int chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public boolean isCollect() {
                return collect;
            }

            public void setCollect(boolean collect) {
                this.collect = collect;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getEnvelopePic() {
                return envelopePic;
            }

            public void setEnvelopePic(String envelopePic) {
                this.envelopePic = envelopePic;
            }

            public boolean isFresh() {
                return fresh;
            }

            public void setFresh(boolean fresh) {
                this.fresh = fresh;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public String getProjectLink() {
                return projectLink;
            }

            public void setProjectLink(String projectLink) {
                this.projectLink = projectLink;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public int getSuperChapterId() {
                return superChapterId;
            }

            public void setSuperChapterId(int superChapterId) {
                this.superChapterId = superChapterId;
            }

            public String getSuperChapterName() {
                return superChapterName;
            }

            public void setSuperChapterName(String superChapterName) {
                this.superChapterName = superChapterName;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }

            public List<?> getTags() {
                return tags;
            }

            public void setTags(List<?> tags) {
                this.tags = tags;
            }

            @Override
            public int getItemType() {

                if (TextUtils.isEmpty(envelopePic)){
                    return NO_PIC;
                }else {
                    return HAS_PIC;
                }
            }
        }
    }
}
