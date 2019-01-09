package com.mytest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.mytest.adapterview.ViewActivity;
import com.mytest.animator.AnimatorActivity;
import com.mytest.base.BaseActivity;
import com.mytest.customview.CustomActivity;
import com.mytest.gridlayout.ShowBigPicActivity;
import com.mytest.smartrefresh.SmartActivity;
import com.mytest.timershaft.TimerShaftActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnTouchListener {

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
    @BindView(R.id.btn_timer_shaft)
    Button btn_timer_shaft;

    @BindView(R.id.webview)
    WebView webView;


    @BindView(R.id.tv)
    TextView tv;


    private static final int CODE = 100;


        @Override
    public int initLayout() {
            //AppCompatActivity隐藏标题栏
//            if(getSupportActionBar() != null){
//                getSupportActionBar().hide();
//            }

            //activity隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_main;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void addListener() {
        btn_custom.setOnClickListener(this);
        btn_custom.setOnTouchListener(this);
        btn_smart.setOnClickListener(this);
        btn_test.setOnClickListener(this);
        btn_gridlayout.setOnClickListener(this);
        btn_adapterview.setOnClickListener(this);
        btn_animator.setOnClickListener(this);
        btn_timer_shaft.setOnClickListener(this);
    }

    public static boolean isInstallApp(Context context, String pack_name) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName.toLowerCase(Locale.ENGLISH);
                if (pn.equals(pack_name)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void initData() {
//        steepStatusBar();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);
    }

    private long first;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_custom:
                Log.d("mytest", "onClick: x -- " + v.getX());
                Log.d("mytest", "onClick: y -- " + v.getY());
                first = System.currentTimeMillis();
//                startActivity(new Intent(this, CustomActivity.class));
                break;
            case R.id.btn_smart:
                long second = System.currentTimeMillis();

                Log.d("mytest", "onClick: first-- " + first);
                Log.d("mytest", "onClick: second-- " + second);
                Log.d("mytest", "onClick: -- " + (second - first));
//                startActivity(new Intent(this, SmartActivity.class));
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
            case R.id.btn_timer_shaft:
                startActivity(new Intent(this, TimerShaftActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE) {
            Log.d("mytest", "onClick: btn_test-- " + first);
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


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(MainActivity.this, "you are SHABI", Toast.LENGTH_SHORT).show();
                Log.d("mytest", "onClick: onTouchEvent x -- " + motionEvent.getX());
                Log.d("mytest", "onClick: onTouchEvent y -- " + motionEvent.getY());
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(MainActivity.this, "you click add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(MainActivity.this, "you click remove", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
