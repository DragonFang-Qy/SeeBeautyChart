package com.fangzitcl.libs.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.fangzitcl.libs.R;
import com.fangzitcl.libs.util.UtilSnackbar;

public class NetWorkReceiver extends BroadcastReceiver {
    public static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    NetworkInfo.State wifiState = null;
    NetworkInfo.State mobileState = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (ACTION.equals(intent.getAction())) {
            //获取手机的连接服务管理器，这里是连接管理器类
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

            if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED == mobileState) {
                UtilSnackbar.showShort(R.string.mobile_phone_NetWorkHint);
            } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED == wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                UtilSnackbar.showShort(R.string.wifi_NetWorkHint);
            } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                UtilSnackbar.showLong(R.string.notNetWorkHint);
            }
        }
    }

}

