package com.fangzitcl.libs.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import java.util.List;

import static java.lang.System.exit;

/**
 * &nbsp;&nbsp;包括:
 * <ol>
 * <li> 获取应用程序名称 {@link #getAppName(Context)} </li>
 * <li> 获取应用程序版本名称信息 {@link #getVersionName(Context)} </li>
 * <li> 获取应用程序版本代号信息 {@link #getVersionCode(Context)} </li>
 * <li> 获取本应用程序包名 {@link #getPackageName(Context)} </li>
 * <li> 获取正在运行的应用 {@link #getRunningProcess(Context)} </li>
 * <li> 获取本应用Icon {@link #getIcon(Context)} </li>
 * <li> 退出应用程序 {@link #exitMyApp()} </li>
 * </ol>
 *
 * @class_name: UtilApp
 * @package_name: com.fangzitcl.libs.util
 * @acthor: Fang_QingYou
 * @time: 15/12/30 上午10:51
 */
public class UtilApp {

    private List<ApplicationInfo> appList;

    private UtilApp() {

    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本代号信息
     *
     * @param context
     * @return 当前应用的版本代号
     */
    public static String getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return String.valueOf(packageInfo.versionCode);

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本应用程序包名
     *
     * @param context
     * @return 当前应用的版本代号
     */
    public static String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return String.valueOf(packageInfo.packageName);

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取正在运行的应用
     *
     * @param context
     * @return
     */
    public static List<RunningAppProcessInfo> getRunningProcess(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取正在运行的应用
        List<RunningAppProcessInfo> run = am.getRunningAppProcesses();


        return run;
    }

    /**
     * 获取本应用Icon
     *
     * @param context
     * @return
     */
    public static Drawable getIcon(Context context) {

        PackageManager pm = (PackageManager) context.getPackageManager();

        try {
            return pm.getApplicationIcon(getPackageName(context));
        } catch (NameNotFoundException e) {

            e.printStackTrace();
            return null;
        }


    }

    /**
     * 退出应用程序
     */
    public static void exitMyApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        exit(0);// 0 代表正常退出
    }


    /**
     * 获取应用安装时间
     */
    public static long getFirstInstallTime(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.firstInstallTime;
        } catch (NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return 0;
    }

    public static void getPermission(Activity context, String permission, int requestCode) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    requestCode);
// 配合 Activity 的onRequestPermissionsResult 使用

//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
//                    Manifest.permission.READ_CONTACTS)) {
// }
        }


    }


}
