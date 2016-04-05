package com.fangzitcl.libs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.ButterKnife;
import com.fangzitcl.libs.app.FQYApplication;
import com.fangzitcl.libs.manage.ActivityManager;


/**
 * @version V1.0
 * @Title: BaseActivity.java
 * @Package com.fqy.fqylibs.activity
 * @author: Fang Qingyou
 * @date 2015年7月11日下午4:37:38
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 基类(Activity)所使用的TAG标签
     */
    public static String TAG = null;
    public static Activity mContext;
    public static View mView = null;
    protected ActivityManager activityManage;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        // 去除标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mContext = this;
        // 用于确定当前界面是属于哪个活动(Activity), 让新加入开发的人快速锁定所在的界面,不得擅自移除.
        this.TAG = getClass().getSimpleName();

        // 获得 Activity栈对象
        activityManage = ((FQYApplication) this.getApplication())
                .getActivityManage();
        // 压栈
        activityManage.addActivity(this);

        mView = LayoutInflater.from(mContext).inflate(setViewResID(), null);
        setContentView(mView);
        ButterKnife.bind(this);
        initView();
        initListener();
        getHttpData();


    }

    /**
     * 设置布局 id
     *
     * @Package com.fqy.fqylibs.activity
     * @Description: TODO 设置布局 id (规范化，避免遗忘)
     * @author: Fang Qingyou
     * @date 2015年5月28日下午6:03:49
     * @version V1.0
     */
    public abstract int setViewResID();

    /**
     * @Package com.fqy.fqylibs.activity
     * @Description: TODO 寻找控件，初始化控件(规范化，避免遗忘)
     * @author: Fang Qingyou
     * @date 2015年5月28日下午6:03:49
     * @version V1.0
     */
    public abstract void initView();

    /**
     * @Package com.fqy.fqylibs.activity
     * @Description: TODO 设置监听 (规范化，避免遗忘)
     * @author: Fang Qingyou
     * @date 2015年5月28日下午6:04:26
     * @version V1.0
     */
    public abstract void initListener();

    /**
     * 获得网路数据
     *
     * @author: Fang Qingyou
     * @date 2015年6月26日下午2:22:45
     */
    public abstract void getHttpData();

    @Override
    protected void onResume() {
        super.onResume();
        // TODO 刷新数据
        this.getHttpData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }


}
