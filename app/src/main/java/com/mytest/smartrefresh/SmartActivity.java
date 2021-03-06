package com.mytest.smartrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.mytest.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.LinkedList;
import java.util.List;

public class SmartActivity extends AppCompatActivity {

    private List<String> mDatas;
    private RecyclerView rvList;
    private MyRecyclerAdapter recycleAdapter;
    private Handler mHandler;
    private SmartRefreshLayout srfresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart);
        mHandler = new Handler();
        initData();
        initView();
        aboutAapter();
    }

    private void aboutAapter() {
        recycleAdapter = new MyRecyclerAdapter(this, mDatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
//        PagerSnapHelper helper = new PagerSnapHyielper();
//        helper.attachToRecyclerView(rvList);
        //设置布局管理器
        rvList.setLayoutManager(layoutManager);
        //设置Adapter
        rvList.setAdapter(recycleAdapter);
        //设置 Header 为 贝塞尔雷达 样式
        srfresh.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        srfresh.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
//        srfresh.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(final RefreshLayout refreshlayout) {
//                //延时展示，延时2秒
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        initData();
//                        recycleAdapter.refresh(mDatas);
//                        refreshlayout.finishRefresh();
//                    }
//                }, 2000);
//
//            }
//        });
//
//        srfresh.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(final RefreshLayout refreshlayout) {
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        initData();
//                        recycleAdapter.refresh(mDatas);
//                        refreshlayout.finishLoadmore();
//                    }
//                }, 2000);
//            }
//        });
        srfresh.setEnableLoadmore(true);
//        srfresh.autoRefresh();
    }

    private void initData() {
        mDatas = new LinkedList<>();
        for (int i = 0; i < 15; i++) {
            mDatas.add("第" + i + "条数据");
        }
    }

    private void initView() {
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        srfresh = (SmartRefreshLayout) findViewById(R.id.srl_fresh);
    }
}
