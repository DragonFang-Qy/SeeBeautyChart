package com.a704084109qq.news.util;

import com.baidu.apistore.sdk.ApiCallBack;
import com.fangzitcl.libs.util.UtilSnackbar;

public class BaiDuCallBack extends ApiCallBack {

    private static final int STATUSCODE_1 = 1;
    private static final int STATUSCODE_3 = 3;
    private static final int STATUSCODE_4 = 4;

    public BaiDuCallBack() {
        super();
    }

    @Override
    public void onSuccess(int i, String s) {
        super.onSuccess(i, s);
    }

    @Override
    public void onError(int statusCode, String responseString, Exception e) {
        super.onError(statusCode, responseString, e);

        if (statusCode == STATUSCODE_1) {
            UtilSnackbar.showLong(com.fangzitcl.libs.R.string.notNetWorkHint);
        } else if (statusCode == STATUSCODE_3) {
            UtilSnackbar.showLong(com.fangzitcl.libs.R.string.callProgrammer);
        } else if (statusCode == STATUSCODE_4) {
            UtilSnackbar.showLong(com.fangzitcl.libs.R.string.callProgrammer);
        } else {
            UtilSnackbar.showLong(com.fangzitcl.libs.R.string.callProgrammer);
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
    }
}
