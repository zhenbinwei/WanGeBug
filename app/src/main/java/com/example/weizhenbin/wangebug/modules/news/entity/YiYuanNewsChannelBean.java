package com.example.weizhenbin.wangebug.modules.news.entity;

import com.example.weizhenbin.wangebug.base.YiYuanBaseBean;

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/23.
 */

public class YiYuanNewsChannelBean extends YiYuanBaseBean {


    private ShowapiResBodyBean showapi_res_body;

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {

        private int totalNum;
        private int ret_code;
        private List<ChannelListBean> channelList;

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public List<ChannelListBean> getChannelList() {
            return channelList;
        }

        public void setChannelList(List<ChannelListBean> channelList) {
            this.channelList = channelList;
        }

        public static class ChannelListBean {
            /**
             * channelId : 5572a108b3cdc86cf39001cd
             * name : 国内焦点
             */

            private String channelId;
            private String name;

            public String getChannelId() {
                return channelId;
            }

            public void setChannelId(String channelId) {
                this.channelId = channelId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
