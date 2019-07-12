package com.mytest.okhttp;

import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.mytest.BaseApplication;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpUtils {

    public static void okhttpGet() {
        Request.Builder requestBuilder = new Request.Builder().url("http://blog.csdn.net/itachi85");
        requestBuilder.method("GET",null);
        Request request = requestBuilder.build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Logger.e("okhttpGet: -- utils " + str);
            }
        });


    }

    public static void okhttpPost(){
        //        RequestBody formBody = new FormBody.Builder()
//                .add("ip","59.108.54.37")
//                .build();
        RequestBody formBody = new FormBody.Builder()
                .add("do","andrioconfig")
                .build();

        Request request = new Request.Builder()
                .url("http://im.kencan.cc/app/love.php?i=10&c=entry&m=jy_ppp")
                .post(formBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Logger.e("okhttpPost: -- " + str);
            }
        });
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("png/x-markdown; charset=utf-8");

    public static void okhttpUploadFile(){
        String filepath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else {
            return;
        }
        File file = new File(filepath,"wangshu.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN,file))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("okhttpUploadFile:onFailure -- ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.e("okhttpUploadFile: -- " + response.body().string());
            }
        });
//        try {
//            okHttpClient.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void okhttpDownloadFile(){
        String url = "http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg";
        final Request request = new Request.Builder().url(url).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("okhttpDownloadFile:onFailure -- ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                String filepath = "";
                try{
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        filepath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    }else {
                        filepath = BaseApplication.getContext().getFilesDir().getAbsolutePath();
                    }

                    File file = new File(filepath,"wangshu.jpg");
                    if (!file.exists()){
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != file){
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buffer = new byte[2048];
                        int len = 0;
                        while ((len = inputStream.read(buffer)) != -1){
                            fileOutputStream.write(buffer,0,len);
                        }
                        fileOutputStream.flush();
                    }
                }catch (IOException e){
                    Logger.e("IOException");
                    e.printStackTrace();
                }
            }
        });
    }

    public static void okhttpSendMultipart(){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title","wangshu")
                .addFormDataPart("image","wangshu.jpg",RequestBody.create(MEDIA_TYPE_PNG,
                        new File("/sdcard/wangshu.jpg")))
                .build();

        Request request = new Request.Builder()
                .header("Authorization","Client-ID" + "...")
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("okhttpSendMultipart:onFailure -- ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.e("okhttpSendMultipart: -- " + response.body().string());
            }
        });
    }
}
