package com.a704084109qq.news.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import butterknife.Bind;

import com.a704084109qq.news.R;
import com.a704084109qq.news.adapter.NavigationAdapter;
import com.a704084109qq.news.app.Constants;
import com.a704084109qq.news.fragment.BeautyPicFragment;
import com.a704084109qq.news.fragment.MyFragment;
import com.fangzitcl.libs.util.UtilApp;
import com.fangzitcl.libs.util.UtilLog;
import com.fangzitcl.libs.util.UtilSnackbar;
import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.IFLYAdListener;
import com.iflytek.voiceads.IFLYAdSize;
import com.iflytek.voiceads.IFLYBannerAd;
import com.jdwx.sdk.ApiManager;
import com.umeng.analytics.AnalyticsConfig;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;

import java.util.Arrays;

public class MainActivity extends MyActivity {

    @Bind(R.id.type_rv)
    RecyclerView typeRv;

    @Bind(R.id.main_dl)
    DrawerLayout mainDL;

    @Bind(R.id.main_ll)
    LinearLayout mainLL;
    BeautyPicFragment fragment;

    private LinearLayoutManager mLayoutManager;
    private NavigationAdapter mAdapter;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;

    @Override
    public int setViewResID() {
        // 友盟日志加密
        AnalyticsConfig.enableEncrypt(true);
        // 有米广告
        AdManager.getInstance(mContext).init(Constants.YMID, Constants.YMSECRET, false);
        // 迅飞广告
        ApiManager.getInstance().registerApp(this, Constants.XFID, true);
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
// 设置分享  分享app    长按分享图片    根据position 的不同得到不同的Fragment 对象
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        //设置固定大小
        typeRv.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(mContext);
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        typeRv.setLayoutManager(mLayoutManager);

        // 初始化 Navigation 列表
        if ((System.currentTimeMillis() - UtilApp.getFirstInstallTime(mContext)) > Constants.S) {
            mAdapter = new NavigationAdapter(mContext, Arrays.asList(getResources().getStringArray(R.array.type)));
        } else {
            mAdapter = new NavigationAdapter(mContext, Arrays.asList(getResources().getStringArray(R.array.type)));
        }
        typeRv.setAdapter(mAdapter);

        // 添加 Fragment
        setFragment(MyFragment.position);

        UtilApp.getPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE, REQUEST);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void getHttpData() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void setFragment(int pos) {

        if ((Integer) getInstant().get(Constants.PAGEFLAG, pos) == pos) {
            getInstant().put(Constants.PAGEFLAG, pos);
        }

        mainDL.closeDrawer(mainLL);
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();
        boolean b = false;
        if (fragment != null) {
            mTransaction.remove(fragment);
            b = true;
        }
        fragment = MyFragment.getInstants(pos, b);
        mTransaction.replace(R.id.content_fl, fragment).commit();

    }

    public void openNavigation() {
        mainDL.openDrawer(mainLL);
    }


//--------------------  我是分割线  以下是广告相关   -------------------------------------


    public void createBannerAd() {
        //创建旗帜广告，传入广告位ID
        bannerView = IFLYBannerAd.createBannerAd(this, Constants.XFUnitId);
        //设置请求的广告尺寸
        bannerView.setAdSize(IFLYAdSize.BANNER);

        //请求广告，添加监听器
        bannerView.loadAd(mAdListener);
        layout_ads.removeAllViews();
        layout_ads.addView(bannerView);
        bannerView.showAd();

        // 实例化广告条
        AdView adView = new AdView(mContext, AdSize.FIT_SCREEN);
        // 将广告条加入到布局中
        adLayout.addView(adView);
    }

    @Bind(R.id.adLayout)
    LinearLayout adLayout;
    @Bind(R.id.layout_adview)
    LinearLayout layout_ads;
    private static final int REQUEST = 1000;
    private IFLYBannerAd bannerView;
    IFLYAdListener mAdListener = new IFLYAdListener() {
        /**
         * 广告请求成功
         */
        @Override
        public void onAdReceive() {
            //展示广告
            bannerView.showAd();
        }

        /**
         * 广告请求失败
         */
        @Override
        public void onAdFailed(AdError error) {
            bannerView.setBackgroundResource(R.mipmap.ic_launcher);
        }

        /**
         * 广告被点击
         */
        @Override
        public void onAdClick() {
        }

        /**
         * 广告被关闭
         */
        @Override
        public void onAdClose() {
        }

        @Override
        public void onAdExposure() {
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createBannerAd();
                } else {
                    UtilSnackbar.showLong(R.string.permissions_denied);
                }
                return;
            }
        }
    }

}
