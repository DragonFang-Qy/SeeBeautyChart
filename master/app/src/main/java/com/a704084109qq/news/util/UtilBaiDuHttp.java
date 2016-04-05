package com.a704084109qq.news.util;

import android.text.TextUtils;
import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;

import java.util.HashMap;
import java.util.Map;

public class UtilBaiDuHttp {

    private static final String NUMTAG = "num";  // 文章返回数量
    private static final String PAGETAG = "page"; // 翻页，输出数量跟随num参数
    private static final String RANDTAG = "rand"; // 是否随机，1表示随机 默认随机
    private static final String WORDTAG = "word"; // 检索关键词

    private static final String NUM = "20";  // 文章返回数量

    /**
     * 获得参数map
     *
     * @param page 翻页，不传默认为0，
     * @return
     */
    public static Map<String, String> getParameter(String page) {
        Map<String, String> map = new HashMap<>();
        map.put(NUMTAG, NUM);
        if (!TextUtils.isEmpty(page.trim())) {
            map.put(PAGETAG, page);
        } else {
            map.put(PAGETAG, "0");
        }
        return map;
    }

    /**
     * 获得参数map
     *
     * @param page 翻页，不传默认为0，
     * @param rand 是否随机，1表示随机，默认为1
     * @param word 检索关键词
     * @return
     */
    public static Map<String, String> getParameter(String page, String rand, String word) {
        Map<String, String> map = new HashMap<>();
        map.put(NUMTAG, NUM);
        if (!TextUtils.isEmpty(page.trim())) {
            map.put(PAGETAG, page);
        } else {
            map.put(PAGETAG, "0");
        }
        map.put(RANDTAG, rand);
        map.put(WORDTAG, word);
        return map;
    }

    public static void httpGet(String url, Map<String, String> map, ApiCallBack callBack) {

        Parameters para = new Parameters();
        para.putAll(map);

        ApiStoreSDK.execute(url, ApiStoreSDK.GET, para, callBack);
    }


}
