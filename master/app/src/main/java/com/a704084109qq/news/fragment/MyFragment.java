package com.a704084109qq.news.fragment;

import android.os.Bundle;

import com.a704084109qq.news.activity.MyActivity;
import com.a704084109qq.news.app.Constants;
import com.a704084109qq.news.app.MyApplication;
import com.fangzitcl.libs.fragment.BaseFragment;
import com.fangzitcl.libs.util.UtilLog;
import com.fangzitcl.libs.util.UtilSharedPreferences;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.io.*;

public abstract class MyFragment extends BaseFragment {

    private static final String ENCODING = "UTF-8";
    public static int position;
    static BeautyPicFragment mFragment = null;
    public Gson mGson = new Gson();
    Boolean bool;
    UtilSharedPreferences mUtilSharedPreferences;

    public static BeautyPicFragment getInstants(int pos, boolean flag) {
        position = pos;
        mFragment = new BeautyPicFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(BeautyPicFragment.ARGUMENTKAY, flag);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    protected void initView() {
    }

    public boolean saveObject(String ser) {

        File file = MyApplication.getInstance(position);

        UtilLog.e(" saveObject " + file.getAbsolutePath());
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, ENCODING);
            bw = new BufferedWriter(osw);
            bw.write(ser.toString());
            bw.flush();
            mUtilSharedPreferences.put(Constants.CACHEFLAG + position, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                bw.close();
            } catch (Exception e) {
            }
            try {
                osw.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    public String readObject() {
        File file = MyApplication.getInstance(position);
        UtilLog.e(" saveObject " + file.getAbsolutePath());
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, ENCODING);
            br = new BufferedReader(isr);

            String Data = "";
            String str = "";
            while ((str = br.readLine()) != null) {
                Data += str;
            }

            return Data;
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
            }
            try {
                isr.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    public boolean isOverdue() {
        File file = MyApplication.getInstance(position);
        return System.currentTimeMillis() - file.lastModified() > Constants.OVERDUETIME;
    }

}
