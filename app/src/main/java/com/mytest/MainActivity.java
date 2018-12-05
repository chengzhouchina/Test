package com.mytest;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.mytest.adapterview.ViewActivity;
import com.mytest.animator.AnimatorActivity;
import com.mytest.customview.CustomActivity;
import com.mytest.gridlayout.ShowBigPicActivity;
import com.mytest.smartrefresh.SmartActivity;

import java.util.HashMap;
import java.util.Map;

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
    @BindView(R.id.btn_mmp)
    Button btn_mmp;



    @BindView(R.id.tv)
    TextView tv;


    private static final int CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        steepStatusBar();
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
        btn_mmp.setOnClickListener(this);
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
            case R.id.btn_mmp:
//                    .headers("AppId","5c061ccd77b09b472e7a528b")
//                Map<String,Object>  map = new HashMap<>();
//                map.put("timestamp",System.currentTimeMillis());
//                map.put("user_id","1");
//                map.put("merchant_transaction_id","2");
//                map.put("payment_channel","airtime_testing");
//                map.put("currency","IDR");
//                map.put("amount",100);
//                map.put("item_name","3");
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.put("AppId","5c061ccd77b09b472e7a528b");
                String sandbox = "https://staging.simplepayment.solutions/";
                String production  = "https://api.simplepayment.solutions/";
                OkGo.<String>post(sandbox+ "api/v1/create")
                        .headers(httpHeaders)
                        .params("timestamp",System.currentTimeMillis())
                        .params("user_id","1")
                        .params("merchant_transaction_id","2")
                        .params("payment_channel","airtime_testing")
                        .params("currency","IDR")
                        .params("amount",100)
                        .params("item_name","3")
                        .execute(new Callback<String>() {
                            @Override
                            public void onStart(Request<String, ? extends Request> request) {

                            }

                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.e("mytest", "onSuccess: " + response );
                            }

                            @Override
                            public void onCacheSuccess(Response<String> response) {

                            }

                            @Override
                            public void onError(Response<String> response) {

                            }

                            @Override
                            public void onFinish() {

                            }

                            @Override
                            public void uploadProgress(Progress progress) {

                            }

                            @Override
                            public void downloadProgress(Progress progress) {

                            }

                            @Override
                            public String convertResponse(okhttp3.Response response) throws Throwable {
                                return null;
                            }
                        });
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

    /*
    判断版本号，版本号小于19不好使
  */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
