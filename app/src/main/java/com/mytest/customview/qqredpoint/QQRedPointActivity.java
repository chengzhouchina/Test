package com.mytest.customview.qqredpoint;

import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.mytest.R;
import com.mytest.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class QQRedPointActivity extends BaseActivity {

    @BindView(R.id.viewstub_test)
    ViewStub viewStub;
    @BindView(R.id.btn_show_stub)
    Button btn_show_stub;
    @BindView(R.id.btn_hide_stub)
    Button btn_hide_stub;
    @BindView(R.id.btn_update_stub)
    Button btn_update_stub;

    private TextView tv_hint;

    @Override
    public int initLayout() {
        return R.layout.activity_qqred_point;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void addListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.btn_show_stub)
    public void showViewstub() {
        try {
            View view = viewStub.inflate();
            tv_hint = view.findViewById(R.id.tv_vsContent);
        } catch (Exception e) {
            viewStub.setVisibility(View.VISIBLE);
        } finally {
            tv_hint.setText("没有相关数据，请刷新");
        }
    }

    @OnClick(R.id.btn_hide_stub)
    public void hideViewstub() {
        viewStub.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.btn_update_stub)
    public void updateViewstub() {
        if (tv_hint != null) {
            tv_hint.setText("网络异常，无法刷新，请检查网络");
        }
    }
}
