package com.mytest.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytest.utils.ActivityCollector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        ActivityCollector.addActivity(this);
        ButterKnife.bind(this);
        addListener();
        initIntent();
        initData();
    }

    public abstract int initLayout();

    public abstract void initIntent();

    public abstract void addListener();

    public abstract void initData();

    @Override
    public abstract void onClick(View view);

    public void back() {
        finish();
    }
}
