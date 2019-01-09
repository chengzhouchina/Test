package com.mytest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mytest.base.BaseActivity;

import butterknife.BindView;

public class TestActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btn_back;

    @Override
    public int initLayout() {
        return R.layout.activity_test;
    }

    @Override
    public void addListener() {
        btn_back.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initIntent() {

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                setResult(RESULT_OK);
                back();
                break;
        }
    }
}
