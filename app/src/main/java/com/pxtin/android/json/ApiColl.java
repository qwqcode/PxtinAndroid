package com.pxtin.android.json;
import java.util.List;

/**
 * Created by Zneia on 2017/4/23.
 */

public class ApiColl
{
    private boolean nextPageExists;
    public boolean getNextPageExists() { return nextPageExists; }

    private List<coll> coll;
    public List<coll> getColl(){ return coll; }

    public static class coll
    {
        private int id;
        private int pic_id;
        private int tin_id;
        private String desc;
        private String tag;
        private int user_id;
        private int created_at;
        private String link;
        private String shortDesc;
        public int getId(){ return id; }
        public String getShortDesc(){ return shortDesc; }

        private picInfo picInfo;
        public picInfo getPicInfo(){ return picInfo; }
        public static class picInfo {
            private String fileName;
            private double width;
            private double height;
            private String mime;
            private thumb thumb;
            public static class thumb {
                private String url;
                public String getUrl(){ return url; }
            }

            public double getWidth(){ return width; }
            public double getHeight(){ return height; }
            public thumb getThumb(){ return thumb; }
        }

        private userInfo userInfo;
        public userInfo getUserInfo(){ return userInfo; }
        public static class userInfo {
            private int id;
            private String name;
            private String link;
            private String avatar;

            public String getName(){ return name; }
            public String getAvatar(){ return avatar; }
        }

        private tinInfo tinInfo;
        public tinInfo getTinInfo(){ return tinInfo; }
        public static class tinInfo {
            private String name;
            private String link;
        }
    }
}
