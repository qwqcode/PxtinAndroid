package com.pxtin.android.json;

import java.util.List;

/**
 * Pictin Search Map At www.qwqaq.com
 * Created by Zneia on 2017/3/18.
 */

public class ImageList
{
    /**
     * 关键字
     */
    private String keyword;
    public String getKeyword(){ return keyword; }
    public void setKeyword(String Keyword){ this.keyword = Keyword; }

    /**
     * 是否还需要加载更多
     */
    private boolean nextpage;
    public boolean getNextpage(){ return nextpage; }
    public void setNextpage(boolean Nextpage){ this.nextpage = Nextpage; }

    /**
     * 当前页码
     */
    private int page;
    public int getPage() { return page; }
    public void setPage(int Page) { this.page = Page; }

    /**
     * 当前偏移量
     */
    private int offset;
    public int getOffset() { return offset; }
    public void setOffset(int Offset) { this.offset = Offset; }

    /**
     * 图片总数
     */
    private int total;
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    /**
     * 图片数据
     */
    private List<ResultsImagesInfoBean> results;
    public List<ResultsImagesInfoBean> getResults(){ return results; }
    public void setResults(List<ResultsImagesInfoBean> ResultsImagesInfoBean) { this.results = ResultsImagesInfoBean; }

    public static class ResultsImagesInfoBean
    {
        /**
         * 图片ID
         */
        private int id;
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        /**
         * 图片完整描述
         */
        private String desc;
        public String getDesc(){ return desc; }
        public void setDesc(String desc){ this.desc = desc; }

        /**
         * 图片简短描述
         */
        private String cutdesc;
        public String getCutdesc(){ return cutdesc; }
        public void setCutdesc(String cutdesc){ this.cutdesc = cutdesc; }

        /**
         * 图片资源信息
         */
        private ResultsPicinfoBean picinfo;
        public ResultsPicinfoBean getPicinfo(){ return picinfo; }
        public void setPicinfo(ResultsPicinfoBean data){ this.picinfo = data; }

        public static class ResultsPicinfoBean
        {
            /**
             * 缩略图
             */
            private ThumbBean thumb;
            public ThumbBean getThumb(){ return thumb; }
            public void setThumb(ThumbBean data){ this.thumb = data; }

            public static class ThumbBean
            {
                /**
                 * 缩略图高度
                 */
                private int height;
                public void setHeight(int Height){ this.height = Height; }
                public int getHeight(){ return height; }

                /**
                 * 缩略图 URL
                 */
                private String url;
                public String getUrl(){ return url; }
                public void setUrl(String url){ this.url = url; }
            }
        }

        /**
         * 图片采集者信息
         */
        private ResultsUserinfoBean userinfo;
        public ResultsUserinfoBean getUserinfo(){ return userinfo; }
        public void setUserinfo(ResultsUserinfoBean data){ this.userinfo = data; }

        public static class ResultsUserinfoBean
        {
            /**
             * 用户名
             */
            private String name;
            public String getName(){ return name; }
            public void setName(String name){ this.name = name; }

            /**
             * 头像 Url
             */
            private String face;
            public String getFace(){ return face; }
            public void setFace(String name){ this.face = name; }
        }
    }
}