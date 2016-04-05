package com.fangzitcl.libs.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * &nbsp;&nbsp;包括:
 * <ol>
 * <li> 返回屏幕宽度 {@link #getScreenWidth(Context)} </li>
 * <li> 返回屏幕高度 {@link #getScreenHeight(Context)} </li>
 * <li> 获得状态栏的高度 {@link #getStatusHeight(Context)} </li>
 * <li> 获取标题栏高度 {@link #getStatusHeight(Context)} </li>
 * <li> 获取应用视图的高度(包括标题栏,不包括状态栏) {@link #getAppView(Activity)} </li>
 * <li> 获取应用内容视图的高度(不包括标题栏,不包括状态栏) {@link #getAppContentView(Activity)} </li>
 * <li> 获取当前屏幕截图，包含状态栏 {@link #snapShotWithStatusBar(Activity)} </li>
 * <li> 获取当前屏幕截图，不包含状态栏 {@link #snapShotWithoutStatusBar(Activity)} </li>
 * </ol>
 *
 * @class_name: UtilScreen
 * @package_name: com.fangzitcl.libs.util
 * @acthor: Fang_QingYou
 * @time: 15/12/30 下午5:18
 */
public class UtilScreen {

    private UtilScreen() {

    }

    /**
     * 返回屏幕宽度
     *
     * @param context
     * @return
     * @author: Fang Qingyou
     * @date 2015年7月28日下午4:18:52
     */
    public static int getScreenWidth(Context context) {
//        DisplayMetrics metrics = new DisplayMetrics();
//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        wm.getDefaultDisplay().getMetrics(metrics);
//        return metrics.widthPixels;

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 返回屏幕高度
     *
     * @param context
     * @return
     * @author: Fang Qingyou
     * @date 2015年7月28日下午4:05:02
     */
    public static int getScreenHeight(Context context) {
//        DisplayMetrics metrics = new DisplayMetrics();
//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        wm.getDefaultDisplay().getMetrics(metrics);
//        return metrics.heightPixels;

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getHeight();

    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取标题栏高度
     * <br>ps:
     * <ul>
     * <li>不能在onCreate() 中使用此方法,否则获取到的值可能是屏幕高度值;</li>
     * <li>如果设置了无标题栏的样式,正确值为 0;</li>
     * <li>可以在点击事件中使用 或者 重写onWindowFocusChanged() 方法.</li>
     * </ul>
     *
     * @param activity
     * @return
     */
    public static int getTitleHeight(Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);

        Rect outRect1 = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect1);

        return outRect.height() - outRect1.height();
    }

    /**
     * 获取应用视图的高度(包括标题栏,不包括状态栏)
     * <br>ps:
     * <ul>
     * <li>不能在onCreate() 中使用此方法,如果设置无状态栏样式,正确值为屏幕高度;</li>
     * <li>可以在点击事件中使用 或者 重写onWindowFocusChanged() 方法.</li>
     * </ul>
     *
     * @param activity
     * @return
     */
    public static int getAppView(Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.height();
    }

    /**
     * 获取应用内容视图的高度(不包括标题栏,不包括状态栏)
     * <br>ps:
     * <ul>
     * <li>不能在onCreate() 中使用此方法,如果设置无状态栏无标题栏样式,正确值为屏幕高度;</li>
     * <li>可以在点击事件中使用 或者 重写onWindowFocusChanged() 方法.</li>
     * </ul>
     *
     * @param activity
     * @return
     */
    public static int getAppContentView(Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
        return outRect.height();
    }


    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }


    /**
     * 加密解密算法  如j = 0加密，需j = 1进行解密，反之亦然。 'a'为密钥，可随意更改
     */
    public static String convertMD5(String inStr, int j) {

        char[] a = inStr.toCharArray();
        if (j == 0) {
            for (int i = 0; i < a.length; i++) {
                a[i] = (char) (a[i] + 'a');
            }
        } else if (j == 1) {
            for (int i = 0; i < a.length; i++) {
                a[i] = (char) (a[i] - 'a');
            }
        }

        return new String(a);
    }


    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }
}
