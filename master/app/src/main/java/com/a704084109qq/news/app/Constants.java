package com.a704084109qq.news.app;

public class Constants {

    public static final String BAIDUKEY = "f3f1bc2da98a10b02218290391ff1237";

    public static final long OVERDUETIME = 6 * 60 * 60 * 1000;
    public static final long S = 7 * 24 * 60 * 60 * 1000;

    public static final String CACHEFLAG = "CacheFlag";
    public static final String PAGEFLAG = "PageFlag";
    // 美女图片,体育新闻,科技新闻,社会新闻,国际新闻,娱乐花边,奇闻趣事,苹果新闻,生活健康,微信热门精选
    public static final String[] urlArray = {
            Constants.BEAUTYPICTURE,
            Constants.SPORTSNEWS,
            Constants.SCIENCETECHNOLOGYNEWS,
            Constants.SOCIALNEWS,
            Constants.WORLDNEWS,
            Constants.AMUSEMENTNEWS,
            Constants.ANECDOTENEWS,
            Constants.APPLENEWS,
            Constants.HEALTHYLIFE,
            Constants.WECHATHOT,
    };
    //    "Beauty picture", "sports", "science and technology news", "social news", "world news",
//            "Amusement" and "anecdote", "apple news", "healthy life", "WeChat popular choice"
    // 有米                                cb1ca1821b480172
    public static final String YMID = "cb1ca1821b480172";
    public static final String YMSECRET = "1664edb9c71341bd";
    // 讯飞
    public static final String XFID = "56fcbdbf";
    public static final String XFUnitId = "95A0C40137A95A2D0CA54175E73D5649";

    private static final String BAIDUAPISTOREB = "http://apis.baidu.com/txapi";
    public static final String BEAUTYPICTURE = BAIDUAPISTOREB + "/mvtp/meinv";
    public static final String SPORTSNEWS = BAIDUAPISTOREB + "/tiyu/tiyu";
    public static final String SCIENCETECHNOLOGYNEWS = BAIDUAPISTOREB + "/keji/keji";
    public static final String SOCIALNEWS = BAIDUAPISTOREB + "/social/social";
    public static final String WORLDNEWS = BAIDUAPISTOREB + "/world/world";
    public static final String AMUSEMENTNEWS = BAIDUAPISTOREB + "/huabian/newtop";
    public static final String ANECDOTENEWS = BAIDUAPISTOREB + "/qiwen/qiwen";
    public static final String APPLENEWS = BAIDUAPISTOREB + "/apple/apple";
    public static final String HEALTHYLIFE = BAIDUAPISTOREB + "/health/health";
    public static final String WECHATHOT = BAIDUAPISTOREB + "/weixin/wxhot";


}
