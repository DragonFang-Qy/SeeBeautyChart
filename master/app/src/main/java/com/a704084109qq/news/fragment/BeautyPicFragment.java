package com.a704084109qq.news.fragment;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.a704084109qq.news.R;
import com.a704084109qq.news.activity.MainActivity;
import com.a704084109qq.news.activity.MyActivity;
import com.a704084109qq.news.activity.WebActivity;
import com.a704084109qq.news.adapter.BeautyPicAdapter;
import com.a704084109qq.news.app.Constants;
import com.a704084109qq.news.listener.LoadMoreListener;
import com.a704084109qq.news.model.BeautyPicModel;
import com.a704084109qq.news.util.BaiDuCallBack;
import com.a704084109qq.news.util.UtilBaiDuHttp;
import com.a704084109qq.news.util.UtilsShareSDK;
import com.fangzitcl.libs.util.UtilLog;
import com.fangzitcl.libs.util.UtilSnackbar;
import com.google.gson.reflect.TypeToken;
import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.IFLYAdListener;
import com.iflytek.voiceads.IFLYAdSize;
import com.iflytek.voiceads.IFLYBannerAd;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;

public class BeautyPicFragment extends MyFragment implements SwipeRefreshLayout.OnRefreshListener, BeautyPicAdapter.ItemClickListener, BeautyPicAdapter.ItemLongClickListener {

    private static int page = 1;
    public static final String ARGUMENTKAY = "checked";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.bp_rv)
    RecyclerView mRecyclerView;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private BeautyPicAdapter mAdapter;
    private List<BeautyPicModel> mListView;
    private StaggeredGridLayoutManager mManager;
    private Type type = new TypeToken<ArrayList<BeautyPicModel>>() {
    }.getType();
    private boolean checkFlag = false;
    private LoadMoreListener mLoadMoreListener = new LoadMoreListener() {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onLoadMore() {
            super.onLoadMore();
            page++;
            getHttpData();
            setLoadingMore(false);
        }

        @Override
        public void onFinish(List list) {
            super.onFinish(list);
        }
    };

    @Override
    public int setViewResID() {
        // http://www.cnblogs.com/wolf-bing/p/3153836.html
        setHasOptionsMenu(true);
        return R.layout.fragment_bp;
    }


    public BeautyPicFragment() {
        page = 1;
    }

    @Override
    protected void initView() {
        checkFlag = getArguments().getBoolean(ARGUMENTKAY);
        mUtilSharedPreferences = ((MyActivity) getActivity()).getInstant();
        mListView = new ArrayList<>();
        mAdapter = new BeautyPicAdapter(mContext, mListView, R.layout.load_more);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mManager);
        mToolbarLayout.setTitle(getResources().getStringArray(R.array.type)[position]);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_menu);
        toolbar.setOverflowIcon(null);

        createBannerAd();
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(mLoadMoreListener);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
    }

    @Override
    protected void initHttpData() {
        page = 1;
        getHttpData();
    }

    @Override
    public void onRefresh() {
        page = 1;
        mListView.clear();
        getHttpData();
    }

    void getHttpData() {
        String url = Constants.urlArray[position];
        bool = (Boolean) mUtilSharedPreferences.get(Constants.CACHEFLAG + position, false);
        if (bool && !isOverdue() && page < 2) {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(readObject());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mListView.addAll((Collection<? extends BeautyPicModel>) mGson.fromJson(jsonArray.toString(), type));
            mAdapter.notifyDataSetChanged();
            if (mAdapter.isHasFooter()) {
                mAdapter.setFooterView(0);
            }
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } else {
            if (page == 1) {
                mListView.clear();
            }
            UtilBaiDuHttp.httpGet(url, UtilBaiDuHttp.getParameter(String.valueOf(page)), new BaiDuCallBack() {
                @Override
                public void onSuccess(int status, String responseString) {
                    super.onSuccess(status, responseString);
                    try {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        JSONObject object = new JSONObject(responseString);
                        JSONArray jsonArray = object.getJSONArray("newslist");
                        mListView.addAll((Collection<? extends BeautyPicModel>) mGson.fromJson(jsonArray.toString(), type));
                        if ((isOverdue() || !bool || checkFlag) && page < 2) {
                            saveObject(jsonArray.toString());
                        }

                        mAdapter.notifyDataSetChanged();
                        if (mAdapter.isHasFooter()) {
                            mAdapter.setFooterView(0);
                        }
                        UtilSnackbar.showShort(R.string.load_success);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            checkFlag = false;
        }
    }

    @Override
    public void onItemClickListener(View v, int position) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(WebActivity.URLKEY, mListView.get(position).getUrl());
        getActivity().startActivity(intent);
    }

    @Override
    public void onItemLongClickListener(View v, int position) {
        UtilsShareSDK.showShare(mContext, mListView.get(position).getTitle(), mListView.get(position).getPicUrl());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ((MainActivity) mContext).openNavigation();
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
