package com.mytest.datastore;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mytest.R;
import com.mytest.base.BaseActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.OnClick;

public class DataStoreActivity extends BaseActivity {

    @BindView(R.id.edt_text)
    EditText edt_text;

    @Override
    public int initLayout() {
        return R.layout.activity_data_store;
    }

    @Override
    public void initIntent() {
        //动态请求权限
        //相等已经授权
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {

        }
    }

    @OnClick(R.id.btn_create_database)
    public void createDatabase() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {

        }
    }


    @Override
    public void addListener() {

    }

    @Override
    public void initData() {
        String inputText = load();
        if (!TextUtils.isEmpty(inputText)) {
            edt_text.setText(load());
            edt_text.setSelection(inputText.length());
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_save_data)
    public void saveData() {
//        getSharedPreferences("data",MODE_PRIVATE);
//        getPreferences(MODE_PRIVATE);
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = getSharedPreferences("data", MODE_PRIVATE).edit();
        edit.putString("name", "tom");
        edit.putInt("age", 28);
        edit.putBoolean("married", false);
        edit.apply();
        edit.commit();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = edt_text.getText().toString();
        save(inputText);
    }

    /**
     * 数据存储到文件中
     *
     * @param inputText
     */
    private void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            //有两种模式 MODE_PRIVATE 是默认的操作模式，表示当指定同样文件名的时候，所写入的内容将会
            // 覆盖原文件中的内容，而 MODE_APPEND 则表示如果该文件已存在就往文件里面追加内容，不存在就创建新文件
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从文件中读数据
     *
     * @return
     */
    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    /**
     * 动态请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //dosomething
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
