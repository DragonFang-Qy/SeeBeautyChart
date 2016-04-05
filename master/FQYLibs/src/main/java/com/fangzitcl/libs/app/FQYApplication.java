package com.fangzitcl.libs.app;


import android.app.Application;
import android.content.Context;
import com.fangzitcl.libs.manage.ActivityManager;

public class FQYApplication extends Application {

    public static Context appContext;
    protected ActivityManager activityManage;

    @Override
    public void onCreate() {
        super.onCreate();

        activityManage = ActivityManager.getInstance();
        appContext = getApplicationContext();
    }

    public ActivityManager getActivityManage() {
        return activityManage;
    }

}
