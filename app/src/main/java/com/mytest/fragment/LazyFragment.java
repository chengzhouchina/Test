package com.mytest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mytest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LazyFragment extends Fragment {

    private View mRootView;
    private boolean mIsInited;
    private boolean mIsPrepared;


    public LazyFragment() {
        // Required empty public constructor
    }

    public static LazyFragment getInstance(){
        return new LazyFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_lazy, container, false);
        mIsPrepared = true;
        lazyLoad();
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            lazyLoad();
        }
    }

    private void lazyLoad() {
        if (getUserVisibleHint() && mIsPrepared && !mIsInited){
            loadData();
        }
    }

    private void loadData() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                //1. 加载数据
                //2. 更新UI
                //3. mIsInited = true
            }
        }.start();
    }

}
