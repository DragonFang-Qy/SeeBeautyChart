package com.fangzitcl.libs.manage;

import android.app.Activity;

import com.fangzitcl.libs.activity.BaseActivity;
import com.fangzitcl.libs.util.UtilLog;

import java.util.ArrayList;


/**
 * 自定义 activity 管理器
 *
 * @version V1.0
 * @Title: ActivityManager.java
 * @Package com.fqy.fqylibs.manage
 * @author: Fang Qingyou
 * @date 2015年7月11日下午5:51:55
 */
public class ActivityManager {

    private static final int initIndex = -1; // 下标初始值
    private static ActivityManager manager;
    private ArrayList<Activity> activityList = new ArrayList();
    private Activity mActivity;
    private int currentIndex = initIndex;  // 当前索引位置
    private int size = 0; // 栈的大小

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if (manager == null) { // 单例模式 懒汉模式
            manager = new ActivityManager();
        }
        return manager;
    }

    /**
     * 得到栈顶Activity
     *
     * @return
     */
    public Activity getCurrentStackTopActivity() {
        if (currentIndex > initIndex) {
            getActivity(currentIndex);
            return mActivity;
        } else {
            new NullPointerException("Activity Stack is null");
            return null;
        }
    }


    /**
     * 入栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            activityList.add(activity);
            upDateSC();

            UtilLog.d(" 入栈 " + activity.toString());
        } else {
            new NullPointerException(" ActivityManager.addActivity() Activity not null");
        }
    }

    /**
     * 移除栈顶Activity（Arraylist 最后一个下标）
     */
    public void removeTopActiviyt() {
        if (currentIndex > initIndex) {
            getActivity(currentIndex);
            remove(mActivity);

            UtilLog.d(" 出栈 " + mActivity.toString());
        } else {
            new NullPointerException("Activity Stack is null");
        }
    }


    /**
     * 退出程序
     */
    public void removeAllActiviy() {
        while (currentIndex > initIndex) {
            getActivity(currentIndex);
            remove(mActivity);
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 保留指定的Activity
     *
     * @param activity
     */
    public void removeAllActiviyExceptOne(Activity activity) {
        while (currentIndex > initIndex) {
            getActivity(currentIndex);
            if (activity != mActivity) {
                remove(mActivity);
            } else if (activity == mActivity && size == 1) {
                break;
            }
        }
    }

    /**
     * 当前栈内有多少个Activity
     *
     * @return
     */
    public int getActiviyTaskNum() {
        return size;
    }

    /**
     * 得到Activity栈,按照入栈顺序由先到后返回
     *
     * @return
     */
    public ArrayList<Activity> getActiviyTask() {
        return activityList;
    }

    /**
     * 移除指定Activity
     *
     * @param activity
     */
    public void removeActiviy(Activity activity) {
        if (activity != null) {
            remove(activity);
        } else {
            new NullPointerException(" ActivityManager.removeActiviy() Activity not null");
        }
    }

    /**
     * 出栈
     */
    private void remove(Activity activity) {
        activity.finish();
        activityList.remove(activity);
        upDateSC();
    }

    /**
     * 更新数据  size currentIndex
     */
    private void upDateSC() {
        size = activityList.size();
        currentIndex = size - 1;
        ((BaseActivity) manager.getCurrentStackTopActivity()).mView = manager.getCurrentStackTopActivity().getCurrentFocus();
    }


    /**
     * 得到Activity
     */
    private void getActivity(int index) {
        mActivity = activityList.get(index);
    }


}