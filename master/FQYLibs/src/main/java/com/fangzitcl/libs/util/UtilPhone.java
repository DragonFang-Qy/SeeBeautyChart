package com.fangzitcl.libs.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

/**
 * &nbsp;&nbsp;包括:
 * <ol>
 * <li> 获得SIM卡IMEI {@link #getSimIMEI(Context)} </li>
 * <li> 获取手机IMEI {@link #getIMEI(Activity)} </li>
 * <li> 获取手机号码 {@link #getPhoneNumber(Activity)} </li>
 * <li> 获取手机设备唯一ID {@link #getDeviceUniqueID(Activity)} </li>
 * </ol>
 * &nbsp;&nbsp;ps：下面还有一些属性值供参考
 *
 * @class_name: UtilPhone
 * @package_name: com.fangzitcl.libs.util
 * @acthor: Fang_QingYou
 * @time: 15/12/31 上午10:29
 */
public class UtilPhone {
/**
 *   <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 *   部分方法对于双卡手机不能使用
 */

    /**
     * 获得SIM卡 IMEI
     *
     * @param context
     * @return
     */
    public static String getSimIMEI(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getSubscriberId();
    }

    /**
     * 是否存在 NavigationBar
     *
     * @param activity
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context activity) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    /**
     * 得到NavigationBar 高度,0 代表没有 NavigationBar
     *
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight(Activity activity) {

        if (checkDeviceHasNavigationBar(activity)) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height",
                    "dimen", "android");
            //获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } else {
            return 0;
        }
    }

    /**
     * 获取手机IMEI
     *
     * @param activity
     * @return
     */
    public String getIMEI(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 获取手机号码
     *
     * @param activity
     * @return
     */
    public String getPhoneNumber(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    /**
     * 获取手机设备唯一ID
     *
     * @param activity
     * @return
     */
    public String getDeviceUniqueID(Activity activity) {
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    /**
     * 获取手机服务商信息
     */
    public String getProvidersName(Activity activity) {
        String ProvidersName = "N/A";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) activity
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String IMSI = telephonyManager.getSubscriberId();
            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            System.out.println(IMSI);
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                ProvidersName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                ProvidersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                ProvidersName = "中国电信";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ProvidersName;
    }

    //  TODO  http://www.thinksaas.cn/group/topic/203040/ 关于android各种双卡手机获取imei，imsi的处理（mtk，展讯，高通等）
    //  TODO  http://www.oschina.net/code/snippet_778617_37270 目前最全的获取IMSI方法

//    String board = Build.BOARD;// 主板
//    String band = Build.BRAND;// android系统定制商
//    String abi = Build.CPU_ABI;// cpu指令集
//    String aaa = Build.DEVICE;// 设备参数
//    String dis = Build.DISPLAY;// 显示屏参数
//    String f = Build.FINGERPRINT;// 硬件名称
//    String h = Build.HOST;
//    String i = Build.ID;// 修订版本列表
//    String m = Build.MANUFACTURER;// 硬件制造商
//    String mo = Build.MODEL;// 版本
//    String p = Build.PRODUCT;// 手机制造商
//    String t = Build.TAGS;// 描述String aaa=Build的标签
//    long ti = Build.TIME;
//    String ty = Build.TYPE;// String aaa=Builder类型
//    String u = Build.USER;
//
//    // 当前开发代号
//    String co = Build.VERSION.CODENAME;
//    // 源码控制版本号
//    String inc = Build.VERSION.INCREMENTAL;
//    // 版本字符串
//    String re = Build.VERSION.RELEASE;
//    // 版本号
//    String sdk = Build.VERSION.SDK;
//    // 版本号
//    int in = Build.VERSION.SDK_INT;
//
//// Build.VERSION.SDK_INT可与switch搭配用
//    switch (Build.VERSION.SDK_INT) {
//        case Build.VERSION_CODES.BASE:
//            // 1.0
//            break;
//        case Build.VERSION_CODES.BASE_1_1:
//            // 1.1
//            break;
//        case Build.VERSION_CODES.CUPCAKE:
//            // 1.5
//            break;
//        case Build.VERSION_CODES.CUR_DEVELOPMENT:
//            // current dev version
//            break;
//        case Build.VERSION_CODES.DONUT:
//            // 1.6
//            break;
//        case Build.VERSION_CODES.ECLAIR:
//            // 2.0
//            break;
//        case Build.VERSION_CODES.ECLAIR_0_1:
//            // 2.0.1
//            break;
//        case Build.VERSION_CODES.ECLAIR_MR1:
//            // 2.1
//            break;
//    }
}
