package com.a704084109qq.news.app;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.fangzitcl.libs.BuildConfig;
import com.fangzitcl.libs.app.FQYApplication;
import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

import java.io.File;

public class MyApplication extends FQYApplication {
    private static File cacheData = null;
    private static String filename = "CacheData";

    public static File getInstance(int position) {
        cacheData = new File(appContext.getExternalCacheDir().getPath(), filename + position);
        if (cacheData == null) {
            cacheData = new File(appContext.getCacheDir().getPath(), filename + position);

        }
        return cacheData;
    }

    @Override
    public void onCreate() {
        ApiStoreSDK.init(this, Constants.BAIDUKEY);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        super.onCreate();
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setDebugMode(true);
    }

}
