package com.a704084109qq.news.activity;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.a704084109qq.news.R;
import com.a704084109qq.news.app.Constants;
import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.IFLYAdListener;
import com.iflytek.voiceads.IFLYAdSize;
import com.iflytek.voiceads.IFLYBannerAd;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/4/2 0002.
 */
public class WebActivity extends MyActivity {

    public static final String URLKEY = "url";

    @Bind(R.id.web)
    WebView webView;

    private String url;
    private WebSettings webSettings;
    private ActionBar mActionBar;

    @Override
    public int setViewResID() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        url = getIntent().getStringExtra(URLKEY);
        webView.loadUrl(url);

        webSettings = webView.getSettings();
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
        webSettings.setUseWideViewPort(true);
        //设置默认加载的可视范围是大视野范围
        webSettings.setLoadWithOverviewMode(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        createBannerAd();
    }


    @Override
    public void initListener() {

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);// 使用当前WebView处理跳转
                return true;//true表示此事件在此处被处理，不需要再广播
            }
        });

    }

    @Override
    public void getHttpData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




//------------------------------------------------------ 我是分割线  以下是广告  -------------------------------------------


    @Bind(R.id.adLayout)
    LinearLayout adLayout;
    @Bind(R.id.layout_adview)
    LinearLayout layout_ads;

    private IFLYBannerAd bannerView;

    public void createBannerAd() {
        //创建旗帜广告，传入广告位ID
        bannerView = IFLYBannerAd.createBannerAd(mContext, Constants.XFUnitId);
        //设置请求的广告尺寸
        bannerView.setAdSize(IFLYAdSize.BANNER);
        bannerView.loadAd(mAdListener);
        layout_ads.removeAllViews();
        layout_ads.addView(bannerView);
        bannerView.showAd();

        // 实例化广告条
        AdView adView = new AdView(mContext, AdSize.FIT_SCREEN);
        // 将广告条加入到布局中
        adLayout.addView(adView);
    }

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

}
