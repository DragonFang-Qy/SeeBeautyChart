package com.fangzitcl.libs.util;

import android.content.Context;

/**
 * &nbsp;&nbsp;包括:
 * <ol>
 * <li> dp 转 px {@link #dp2px(Context, float)} </li>
 * <li> px 转 dp {@link #px2dp(Context, float)} </li>
 * <li> sp 转 px {@link #sp2px(Context, float)} </li>
 * <li> px 转 sp {@link #px2sp(Context, float)} </li>
 * </ol>
 *
 * @class_name: UtilDensity
 * @package_name: com.fangzitcl.libs.util
 * @acthor: Fang_QingYou
 * @time: 15/12/30 上午11:46
 */
public class UtilDensity {

    private UtilDensity() {

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue 需要转换的dp 值
     * @return int 类型的像素值
     */
    public static int dp2px(Context context, float dpValue) {
        // 得到系统的独立像素密度
        float scale = context.getResources().getDisplayMetrics().density;
        // 0.5f 四舍五入的左右 效率比 math 的四舍五入效率高
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px 的单位 转成为 dp(像素)
     *
     * @param context 上下文
     * @param pxValue 需要转换的dp 值
     * @return int 类型的像素值
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
