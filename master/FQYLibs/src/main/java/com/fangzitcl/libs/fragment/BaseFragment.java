package com.fangzitcl.libs.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    public static String TAG = null;
    public static Context mContext;

    public static View mView = null;
    private int mViewResID = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TAG = getClass().getSimpleName();
        mContext = getActivity();
        mViewResID = setViewResID();
        mView = inflater.inflate(mViewResID, null);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
        initHttpData();
    }


    public abstract int setViewResID();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initHttpData();
}
