package com.fangzitcl.libs.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * &nbsp;&nbsp;包括:
 * <ol>
 * <li> 打开键盘 {@link #openKeybord(EditText, Context)} </li>
 * <li> 关闭键盘 {@link #closeKeybord(EditText, Context)} </li>
 * <li> 获取键盘的高度 {@link #getKeyboardHeight(Activity, View, CallBack)} </li>
 * <li> 获取键盘状态 {@link #hideKeyboard(Context, View)}, 该方法并不能准确的判断出键盘的状态，
 * 准确的说应该是判断当前view 是否被激活（获取焦点，edittext 获取焦点之后，键盘弹出，是一个间接的判断，所以不准确）</li>
 * </ol>
 * &nbsp;&nbsp;ps:判断键盘状态，一种比较靠谱的方法是重写布局，所以就没写,
 * 可以看一下 <a href="http://blog.csdn.net/caesardadi/article/details/8252829">Android 软键盘的显示和隐藏</a>
 *
 * @class_name: UtilKeyBoard
 * @package_name: com.fangzitcl.libs.util
 * @acthor: Fang_QingYou
 * @time: 15/12/30 下午1:50
 */
public class UtilKeyBoard {

    /**
     * 打开键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    /**
     * 获取键盘的高度
     *
     * @param activity
     * @param view
     * @param callback
     */
    public static void getKeyboardHeight(final Activity activity, final View view, final CallBack callback) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                View rootview = activity.getWindow().getDecorView(); // this = activity
                rootview.getWindowVisibleDisplayFrame(r);
                if (callback != null) {
                    callback.getKeyboardHeight(UtilScreen.getScreenHeight(activity) - r.height());
                }
            }
        });


    }

    /**
     * 获取键盘状态
     *
     * @param context
     * @param view
     * @return
     */
    private boolean hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager.isActive(view)) {

            return true;
        }
        return false;
    }

    public interface CallBack {
        void getKeyboardHeight(int height);
    }
}
