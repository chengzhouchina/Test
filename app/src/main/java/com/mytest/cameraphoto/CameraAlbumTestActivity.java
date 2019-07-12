package com.mytest.cameraphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mytest.R;
import com.mytest.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class CameraAlbumTestActivity extends BaseActivity {

    @BindView(R.id.btn_take_photo)
    Button btn_take_photo;
    @BindView(R.id.iv_picture)
    ImageView iv_picture;

    public static final int TAKE_PHOTO = 1;
    private Uri imageUri;

    @OnClick(R.id.btn_take_photo)
    public void takePhoto() {
        //创建File对象,用于存储拍照后的图片
        //getExternalCacheDir() 可以得到SD卡中专门用于存放当前应用缓存数据的位置
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT >= 24){
            //android7.0开始，使用本地真实路径的Uri被认为是不安全的
            imageUri = FileProvider.getUriForFile(this,"com.mytest.cameraalbumtest.fileprovider",outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    //将拍摄的照片显示出来
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        iv_picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_camera_album_test;
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
}
