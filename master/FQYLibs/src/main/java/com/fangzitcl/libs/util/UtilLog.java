package com.fangzitcl.libs.util;

import android.util.Log;

/**
 * @ClassName: UtilLog
 * @PackageName: com.fangzitcl.libs.util
 * @Acthor: Fang_QingYou
 * @Time: 2016.01.03 12:10
 */
public class UtilLog {

    // 可以写一个基类Activity 定义一个Tag ，便于迅速定位到类
    private static final String TAG = "Test";
    // 自动开启或者关闭， 也可以换成一个定值，进行你想要的操作，但是打包的时候记得把这里改成false，或者保持原状，避免log 泄露
    public static boolean isDebugAuto = true;

    private UtilLog() {
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebugAuto)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebugAuto)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebugAuto)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebugAuto)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebugAuto)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebugAuto)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebugAuto)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebugAuto)
            Log.i(tag, msg);
    }
}
