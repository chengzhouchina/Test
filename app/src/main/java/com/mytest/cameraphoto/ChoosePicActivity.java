package com.mytest.cameraphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mytest.R;
import com.mytest.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;

public class ChoosePicActivity extends BaseActivity {

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;

    @BindView(R.id.take_photo)
    Button takePhoto;
    @BindView(R.id.choose_from_album)
    Button choose_from_album;
    @BindView(R.id.picture)
    ImageView picture;

    private Uri imageUri;


    @Override
    public int initLayout() {
        return R.layout.activity_choose_pic;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void addListener() {
        takePhoto.setOnClickListener(this);
        choose_from_album.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                //创建File对象，用于存储拍照后的照片
                //调 用 Environment 的getExternalStorageDirectory()方法获取到的就是手机 SD 卡的根目录
                File outputImage = new File(Environment.getExternalStorageDirectory(), "tempImage.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //这个 Uri 对象标识着 output_image.jpg 这张图片的唯一地址
                imageUri = Uri.fromFile(outputImage);
                //拍下的照片将会输出到 output_image.jpg 中
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);//启动相机程序
                break;
            case R.id.choose_from_album:
                //  创建File 对象，用于存储选择的照片
                File newoutputImage = new File(Environment.
                        getExternalStorageDirectory(), "output_image.jpg");
                try {
                    if (newoutputImage.exists()) {
                        newoutputImage.delete();
                    }
                    newoutputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(newoutputImage);
                Intent newintent = new Intent("android.intent.action.GET_CONTENT");
                newintent.setType("image/*");
                newintent.putExtra("crop", "true");
                newintent.putExtra("scale", "true");
                newintent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(newintent, CROP_PHOTO);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);//启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);//将裁剪后的照片显示出来
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
