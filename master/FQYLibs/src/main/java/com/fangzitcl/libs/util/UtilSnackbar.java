package com.fangzitcl.libs.util;

import android.support.design.widget.Snackbar;
import android.view.View;
import com.fangzitcl.libs.activity.BaseActivity;

/**
 * @ClassName: UtilSnackbar
 * @PackageName: com.fangzitcl.libs.util
 * @Acthor: Fang_QingYou
 * @Time: 2016.01.03 12:36
 */
public class UtilSnackbar {

    public static boolean isShow = true; // 全局控制，有利有弊
    private static UtilSnackbar mSnackbar;

    //  请在build.gradle 添加  compile 'com.android.support:design:23.1.1'
    private UtilSnackbar() {
    }
//    可以写一个基类Activity 定义一个 view ,以下方法能进一步简化成下面这种形式
//     public static void showShort( CharSequence message) {
//    if (isShow)
//           Snackbar.make(BaseActivity.mView, message,  Snackbar.LENGTH_SHORT).show();
//  }

    public static UtilSnackbar getInstance() {
        if (mSnackbar == null) { // 单例模式 懒汉模式
            mSnackbar = new UtilSnackbar();
        }
        return mSnackbar;
    }

    /**
     * 短时间显示 Snackbar
     *
     * @param view
     * @param message
     */
    public static void showShort(View view, CharSequence message) {
        if (isShow)
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示 Snackbar
     *
     * @param message
     */
    public static void showShort(CharSequence message) {

        if (BaseActivity.mView != null) {
            showShort(BaseActivity.mView, message);
        } else {
            new NullPointerException("UtilSnackbar showShort(message) View is null, you don't extends BaseActivity");
        }

    }

    /**
     * 短时间显示 Snackbar
     *
     * @param view
     * @param resid
     */
    public static void showShort(View view, int resid) {
        if (isShow)
            Snackbar.make(view, view.getResources().getString(resid), Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示 Snackbar
     *
     * @param resid
     */
    public static void showShort(int resid) {

        if (BaseActivity.mView != null) {
            showShort(BaseActivity.mView, resid);
        } else {
            new NullPointerException("UtilSnackbar showShort(int resid) View is null, you don't extends BaseActivity");
        }

    }

    /**
     * 长时间显示 Snackbar
     *
     * @param view
     * @param message
     */
    public static void showLong(View view, CharSequence message) {
        if (isShow) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * 长时间显示 Snackbar
     *
     * @param message
     */
    public static void showLong(CharSequence message) {

        if (BaseActivity.mView != null) {
            showLong(BaseActivity.mView, message);
        } else {
            new NullPointerException("UtilSnackbar showLong(message) View is null, you don't extends BaseActivity");
        }

    }

    /**
     * 长时间显示 Snackbar
     *
     * @param view
     * @param resid
     */
    public static void showLong(View view, int resid) {
        if (isShow) {
            Snackbar.make(view, view.getResources().getString(resid), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * 长时间显示 Snackbar
     *
     * @param resid
     */
    public static void showLong(int resid) {
        if (BaseActivity.mView != null) {
            showLong(BaseActivity.mView, resid);
        } else {
            new NullPointerException("UtilSnackbar showLong(resid) View is null, you don't extends BaseActivity");
        }

    }

    /**
     * 自定义显示 Snackbar时间
     *
     * @param view
     * @param message
     * @param duration
     */
    public static void show(View view, CharSequence message, int duration) {
        if (isShow) {
            Snackbar.make(view, message, duration).show();
        }
    }

    /**
     * 自定义显示 Snackbar时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        if (BaseActivity.mView != null) {
            show(BaseActivity.mView, message, duration);
        } else {
            new NullPointerException("UtilSnackbar showLong(resid) View is null, you don't extends BaseActivity");
        }

    }

    /**
     * 自定义显示 Snackbar时间
     *
     * @param view
     * @param resid
     * @param duration
     */
    public static void show(View view, int resid, int duration) {
        if (isShow) {
            Snackbar.make(view, view.getResources().getString(resid), duration).show();
        }
    }

    /**
     * 自定义显示 Snackbar时间
     *
     * @param resid
     * @param duration
     */
    public static void show(int resid, int duration) {
        if (BaseActivity.mView != null) {
            show(BaseActivity.mView, resid, duration);
        } else {
            new NullPointerException("UtilSnackbar showLong(resid) View is null, you don't extends BaseActivity");
        }
    }


}
