package com.example.weizhenbin.wangebug.modules.news.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.weizhenbin.wangebug.modules.recreation.entity.YiYuanBaseBean;

import java.util.List;

/**
 * Created by weizhenbin on 2018/8/22.
 */

public class YiYuanNewsBean  extends YiYuanBaseBean{



    private ShowapiResBodyBean showapi_res_body;

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {


        private PagebeanBean pagebean;
        private int ret_code;

        public PagebeanBean getPagebean() {
            return pagebean;
        }

        public void setPagebean(PagebeanBean pagebean) {
            this.pagebean = pagebean;
        }

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public static class PagebeanBean {


            private int allNum;
            private int maxResult;
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

            public static class ContentlistBean implements MultiItemEntity{

                public static final int SINGLE_PIC=1;
                public static final int MULTI_PIC=2;

                private String id;
                private String channelId;
                private boolean havePic;
                private String pubDate;
                private String title;
                private String source;
                private String channelName;
                private String desc;
                private String link;
                private List<String> allList;
                private List<ImageurlsBean> imageurls;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getChannelId() {
                    return channelId;
                }

                public void setChannelId(String channelId) {
                    this.channelId = channelId;
                }

                public boolean isHavePic() {
                    return havePic;
                }

                public void setHavePic(boolean havePic) {
                    this.havePic = havePic;
                }

                public String getPubDate() {
                    return pubDate;
                }

                public void setPubDate(String pubDate) {
                    this.pubDate = pubDate;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public String getChannelName() {
                    return channelName;
                }

                public void setChannelName(String channelName) {
                    this.channelName = channelName;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }

                public List<String> getAllList() {
                    return allList;
                }

                public void setAllList(List<String> allList) {
                    this.allList = allList;
                }

                public List<ImageurlsBean> getImageurls() {
                    return imageurls;
                }

                public void setImageurls(List<ImageurlsBean> imageurls) {
                    this.imageurls = imageurls;
                }

                @Override
                public int getItemType() {
                    if (imageurls!=null){
                        if (imageurls.size()<=1){
                            return SINGLE_PIC;
                        }else {
                            return  MULTI_PIC;
                        }
                    }else {
                        return SINGLE_PIC;
                    }
                }

                public static class ImageurlsBean {
                    /**
                     * url : http://n.sinaimg.cn/news/crawl/612/w372h240/20180822/VoIj-hhzsnec1993034.jpg
                     * height : 0
                     * width : 0
                     */

                    private String url;
                    private int height;
                    private int width;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }
                }
            }
        }
    }
}
