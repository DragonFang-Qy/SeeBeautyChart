package com.fangzitcl.libs.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @ClassName: UtilToast
 * @PackageName: com.fangzitcl.libs.util
 * @Acthor: Fang_QingYou
 * @Time: 2016.01.03 12:25
 */
public class UtilToast {

    public static boolean isShow = true;

    private UtilToast() {
    }
//    可以写一个基类Activity 定义一个Context ,以下方法能进一步简化成下面这种形式
//     public static void showShort( CharSequence message) {
//    if (isShow)
//            Toast.makeText(BaseActivity.mContext, message, Toast.LENGTH_SHORT).show();
//  }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 短时间显示Toast
     *
     * @param context
     * @param resid
     */
    public static void showShort(Context context, int resid) {
        if (isShow)
            Toast.makeText(context, context.getResources().getString(resid), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param resid
     */
    public static void showLong(Context context, int resid) {
        if (isShow)
            Toast.makeText(context, context.getResources().getString(resid), Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param resid
     * @param duration
     */
    public static void show(Context context, int resid, int duration) {
        if (isShow)
            Toast.makeText(context, context.getResources().getString(resid), duration).show();
    }

}
