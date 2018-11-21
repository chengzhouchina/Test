package com.mytest;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mytest.adapterview.ViewActivity;
import com.mytest.animator.AnimatorActivity;
import com.mytest.customview.CustomActivity;
import com.mytest.gridlayout.ShowBigPicActivity;
import com.mytest.smartrefresh.SmartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_custom)
    Button btn_custom;
    @BindView(R.id.btn_smart)
    Button btn_smart;
    @BindView(R.id.btn_test)
    Button btn_test;
    @BindView(R.id.btn_gridlayout)
    Button btn_gridlayout;
    @BindView(R.id.btn_adapterview)
    Button btn_adapterview;
    @BindView(R.id.btn_animator)
    Button btn_animator;


    @BindView(R.id.tv)
    TextView tv;


    private static final int CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListener();
    }




    private void initListener() {
        btn_custom.setOnClickListener(this);
        btn_smart.setOnClickListener(this);
        btn_test.setOnClickListener(this);
        btn_gridlayout.setOnClickListener(this);
        btn_adapterview.setOnClickListener(this);
        btn_animator.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_custom:
                startActivity(new Intent(this, CustomActivity.class));
                break;
            case R.id.btn_smart:
                startActivity(new Intent(this, SmartActivity.class));
                break;
            case R.id.btn_test:
                startActivityForResult(new Intent(MainActivity.this, TestActivity.class), CODE);
                break;
            case R.id.btn_gridlayout:
                startActivity(new Intent(this, ShowBigPicActivity.class));
                break;
            case R.id.btn_adapterview:
                startActivity(new Intent(this, ViewActivity.class));
                break;
            case R.id.btn_animator:
                startActivity(new Intent(this, AnimatorActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MyTest", "onActivityResult: request" + requestCode);
        Log.d("MyTest", "onActivityResult: result" + resultCode);
        if (requestCode == CODE) {
            if (resultCode == RESULT_OK) {
                tv.setText("我不好");
            }
        }
    }
}
