package com.example.weizhenbin.wangebug.modules.recreation.entity;

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/15.
 */

public class PicJokeBean {


    /**
     * allNum : 2109
     * contentlist : [{"title":"搞笑图片第2366期","id":"5b72770a6e366106504fc306","img":"https://www.zbjuran.com/uploads/allimg/180814/1341022P9-0.jpg","ct":"2018-08-14 14:30:34.624","type":2},{"title":"搞笑图片第2367期","id":"5b726ff96e366106504fbe53","img":"https://www.zbjuran.com/uploads/allimg/180814/1344035O5-0.jpg","ct":"2018-08-14 14:00:25.756","type":2},{"title":"搞笑图片第2370期","id":"5b726ff96e366106504fbe52","img":"https://www.zbjuran.com/uploads/allimg/180814/134Q54Q0-0.jpg","ct":"2018-08-14 14:00:25.754","type":2},{"title":"搞笑图片第2369期","id":"5b726ff96e366106504fbe51","img":"https://www.zbjuran.com/uploads/allimg/180814/13464J021-0.jpg","ct":"2018-08-14 14:00:25.754","type":2},{"title":"搞笑图片第2368期","id":"5b726ff96e366106504fbe50","img":"https://www.zbjuran.com/uploads/allimg/180814/1345134196-0.jpg","ct":"2018-08-14 14:00:25.754","type":2},{"title":"上车以后一脸懵逼","id":"5b6498266e3697afd634925b","img":"http://sc2.hao123img.com/data/3_7510aaf6dc1082265f1b56d25611068d_430","ct":"2018-08-04 02:00:06.804","type":2},{"title":"还有这种操作？？？？？？","id":"5b6346a66e3697afd6338aa1","img":"http://sc1.hao123img.com/data/2018-06-29/3_7e43ac6465e9b7c5a8fc62a18ad76f41_0","ct":"2018-08-03 02:00:06.426","type":2},{"title":"这样戴安全帽会不会更安全呢？","id":"5b6346a66e3697afd6338aa0","img":"http://sc2.hao123img.com/data/2018-07-29/3_2e77f4c7a95806d474134382d314b69c_430","ct":"2018-08-03 02:00:06.420","type":2},{"title":"高端大气上档次的澡盆！！","id":"5b5f52266e3697afd630bc85","img":"http://sc0.hao123img.com/data/2018-07-28/3_aaf969599f645b48e68cf0efd1ae5a15_430","ct":"2018-07-31 02:00:06.049","type":2},{"title":"手榴蛋！","id":"5b5caf266e36a66ad6573892","img":"http://sc3.hao123img.com/data/2018-07-25/3_299433b863ce0f93672ba27603acb06f_430","ct":"2018-07-29 02:00:06.606","type":2},{"title":"一不小心发现了康康的原型\u2026\u2026","id":"5b58baa56e36ece049985567","img":"http://sc3.hao123img.com/data/2018-07-23/3_971ef324a143f67c4242c9869afed917_0","ct":"2018-07-26 02:00:05.952","type":2},{"title":"你还怕她跑了么","id":"5b5617a66e36ece0499655de","img":"http://sc3.hao123img.com/data/2018-07-20/3_a023eb4f0573ce6b6273b2d9c30bd83f_430","ct":"2018-07-24 02:00:06.086","type":2},{"title":"女儿说给小伙伴洗澡敷面膜，把我吓坏了","id":"5b4f80266e36c2b5e20a53fc","img":"http://sc3.hao123img.com/data/2018-07-18/3_53fd4dd703d9d0b5efc5ae06b5d51150_430","ct":"2018-07-19 02:00:06.384","type":2},{"title":"现在的熊孩子知道的太多了","id":"5b4a3a266e362f86412d7a27","img":"http://sc4.hao123img.com/data/2018-07-14/3_829181c5b973f5215fe16ede9dedc4cc_430","ct":"2018-07-15 02:00:06.707","type":2},{"title":"这个哪里有卖，好想要啊","id":"5b48e8a76e362f86412ca2a1","img":"http://sc3.hao123img.com/data/2018-07-12/3_f78dc7613efdde5ab856142efda8edb8_430","ct":"2018-07-14 02:00:07.932","type":2},{"title":"搞笑图片第2358期","id":"5b4629d66e362f86412b1fc3","img":"https://www.zbjuran.com/uploads/allimg/180703/1306343560-0.jpg","ct":"2018-07-12 00:01:26.611","type":2},{"title":"搞笑图片第2360期","id":"5b4629d66e362f86412b1fc2","img":"https://www.zbjuran.com/uploads/allimg/180703/130Z25525-0.jpg","ct":"2018-07-12 00:01:26.611","type":2},{"title":"搞笑图片第2359期","id":"5b4629d66e362f86412b1fc1","img":"https://www.zbjuran.com/uploads/allimg/180703/130H3EK-0.jpg","ct":"2018-07-12 00:01:26.610","type":2},{"title":"搞笑图片第2357期","id":"5b4629d66e362f86412b1fc0","img":"https://www.zbjuran.com/uploads/allimg/180703/1305155U3-0.jpg","ct":"2018-07-12 00:01:26.610","type":2},{"title":"搞笑图片第2356期","id":"5b4629d66e362f86412b1fbf","img":"https://www.zbjuran.com/uploads/allimg/180703/13034940J-0.jpg","ct":"2018-07-12 00:01:26.610","type":2}]
     * maxResult : 20
     * ret_code : 0
     * allPages : 106
     * currentPage : 1
     */

    private int allNum;
    private int maxResult;
    private int ret_code;
    private int allPages;
    private int currentPage;
    private List<ContentlistBean> contentlist;

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<ContentlistBean> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<ContentlistBean> contentlist) {
        this.contentlist = contentlist;
    }

    public static class ContentlistBean extends JokeContentlistBaseBean{
        /**
         * title : 搞笑图片第2366期
         * id : 5b72770a6e366106504fc306
         * img : https://www.zbjuran.com/uploads/allimg/180814/1341022P9-0.jpg
         * ct : 2018-08-14 14:30:34.624
         * type : 2
         */

        private String title;
        private String id;
        private String img;
        private String ct;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCt() {
            return ct;
        }

        public void setCt(String ct) {
            this.ct = ct;
        }

    }
}
