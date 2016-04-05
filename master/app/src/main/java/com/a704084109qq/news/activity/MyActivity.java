package com.a704084109qq.news.activity;

import com.fangzitcl.libs.activity.BaseActivity;
import com.fangzitcl.libs.util.UtilSharedPreferences;
import com.fangzitcl.libs.util.UtilSnackbar;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

public abstract class MyActivity extends BaseActivity {

    public Gson mGson = new Gson();
    private UtilSharedPreferences mUtilSharedPreferences;
    private long startTime;

    public UtilSharedPreferences getInstant() {
        if (mUtilSharedPreferences == null) {
            mUtilSharedPreferences = new UtilSharedPreferences(mContext);
        }
        return mUtilSharedPreferences;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);
    }


    @Override
    public void onBackPressed() {

        if (activityManage.getActiviyTaskNum() == 1) {
            if (System.currentTimeMillis() - startTime > 0 && System.currentTimeMillis() - startTime < 3 * 1000) {
                MobclickAgent.onKillProcess(mContext);
                activityManage.removeAllActiviy();
            } else {
                startTime = System.currentTimeMillis();
                UtilSnackbar.showShort(mView, com.fangzitcl.libs.R.string.exit_again_hint);
            }
        } else {
            activityManage.removeActiviy(activityManage.getCurrentStackTopActivity());
        }

    }
}
