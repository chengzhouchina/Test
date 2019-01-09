package com.mytest.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
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

    public void back(){
        finish();
    }
}
