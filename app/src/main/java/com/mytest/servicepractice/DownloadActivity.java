package com.mytest.servicepractice;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mytest.R;
import com.mytest.base.BaseActivity;

import java.util.List;

import butterknife.BindViews;

public class DownloadActivity extends BaseActivity {
    @BindViews({R.id.btn_start_download, R.id.btn_cancel_download, R.id.btn_pause_download})
    public List<Button> buttonList;

    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public int initLayout() {
        return R.layout.activity_download;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void addListener() {
        buttonList.get(0).setOnClickListener(this);
        buttonList.get(1).setOnClickListener(this);
        buttonList.get(2).setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);//启动服务
        bindService(intent, connection, BIND_AUTO_CREATE);//绑定服务
        if (ContextCompat.checkSelfPermission(DownloadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DownloadActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(DownloadActivity.this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (downloadBinder == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.btn_start_download:
                String url = "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                downloadBinder.startDownload(url);
                break;
            case R.id.btn_cancel_download:
                downloadBinder.cancelDownload();
                break;
            case R.id.btn_pause_download:
                downloadBinder.pauseDownload();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
