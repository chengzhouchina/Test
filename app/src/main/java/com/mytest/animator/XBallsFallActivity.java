package com.mytest.animator;

import android.view.View;
import android.widget.LinearLayout;

import com.mytest.R;
import com.mytest.base.BaseActivity;

public class XBallsFallActivity extends BaseActivity {

    static final float BALL_SIZE = 50f;//小球直径
    static final float FULL_TIME = 1000;//下落时间

    @Override
    public int initLayout() {
        return R.layout.activity_xballs_fall;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void addListener() {

    }

    @Override
    public void initData() {
        LinearLayout xContainer = findViewById(R.id.xcontainer);
        xContainer.addView(new XBallView(this));
    }

    @Override
    public void onClick(View view) {

    }
}
