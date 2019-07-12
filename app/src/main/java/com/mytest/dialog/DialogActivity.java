package com.mytest.dialog;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mytest.R;
import com.mytest.base.BaseActivity;

import butterknife.OnClick;

public class DialogActivity extends BaseActivity {

    private AlertDialog.Builder dailog ;

    @Override
    public int initLayout() {
        return R.layout.activity_dialog;
    }

    @Override
    public void initIntent() {
        dailog = new AlertDialog.Builder(this);
    }

    @Override
    public void addListener() {

    }

    @Override
    public void initData() {
        // res= getResources();activity的方法 id = R.drawable.x
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.app_icon);
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,bitmap.)
//        Bitmap.CompressFormat
    }

    @OnClick(R.id.btn_normal_dialog)
    public void normalDialogClick() {
        dailog.setTitle("杀掉你好");
        dailog.setIcon(R.drawable.app_icon);
        dailog.setPositiveButton("答应", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DialogActivity.this, "你点了确定", Toast.LENGTH_SHORT).show();
            }
        });
        dailog.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DialogActivity.this, "你点了拒绝", Toast.LENGTH_SHORT).show();
            }
        });
        dailog.show();
    }

    @Override
    public void onClick(View view) {

    }
}
