package com.fangzitcl.libs.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @ClassName: UtilUI
 * @PackageName: com.fangzitcl.libs.util
 * @Acthor: Fang_QingYou
 * @Time: 2016.01.05 18:40
 */
public class UtilUI {

    private UtilUI() {
    }

    // 修改整个界面所有控件的字体
    public static void changeFonts(ViewGroup root, String path, Activity act) {
        // path是字体路径
        Typeface tf = Typeface.createFromAsset(act.getAssets(), path);
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(tf);
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(tf);
            } else if (v instanceof ViewGroup) {
                changeFonts((ViewGroup) v, path, act);
            }
        }
    }

    // 修改整个界面所有控件的字体大小
    public static void changeTextSize(ViewGroup root, int size, Activity act) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTextSize(size);
            } else if (v instanceof Button) {
                ((Button) v).setTextSize(size);
            } else if (v instanceof EditText) {
                ((EditText) v).setTextSize(size);
            } else if (v instanceof ViewGroup) {
                changeTextSize((ViewGroup) v, size, act);
            }
        }
    }

    // 不改变控件位置，修改控件大小
    public static void changeWH(View v, int W, int H) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) v.getLayoutParams();
        params.width = W;
        params.height = H;
        v.setLayoutParams(params);
    }

    // 修改控件的高
    public static void changeH(View v, int H) {
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) v.getLayoutParams();
        params.height = H;
        v.setLayoutParams(params);
    }

    // 修改整个界面所有控件的背景
    public static void changeBackground(ViewGroup root, int resid) {
        // path是字体路径
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setBackgroundResource(resid);
            } else if (v instanceof Button) {
                ((Button) v).setBackgroundResource(resid);
            } else if (v instanceof EditText) {
                ((EditText) v).setBackgroundResource(resid);
            } else if (v instanceof ViewGroup) {
                changeBackground((ViewGroup) v, resid);
            }
        }
    }
}
