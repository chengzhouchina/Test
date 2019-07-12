package com.mytest.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.mytest.utils.Toaster;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RelativeLayout(this));
//        request();
//        request1();
//        request2();
        request3();
    }

    private void request3() {
        String url = "http://ip.taobao.com/service/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(GetRequest_Interface.class).getIpMsg().enqueue(new Callback<IpModel>() {
            @Override
            public void onResponse(Call<IpModel> call, Response<IpModel> response) {
                if (response.body() != null){
                    String country = response.body().getData().getCountry();
                    Toaster.show(RetrofitActivity.this, country);
                    Logger.d(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<IpModel> call, Throwable t) {

            }
        });
    }

    private void request2() {
        FormBody.Builder body = new FormBody.Builder();
        body.add("do","andrioconfig");
        Map<String,Object> map = new HashMap<>();
        map.put("do","andrioconfig");
        map.put("i",10);
        map.put("c","entry");
        map.put("m","jy_ppp");
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://im.kencan.cc")
                .build();

//        retrofit.create(GetRequest_Interface.class).getKencan(map).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Logger.d(response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
        retrofit.create(GetRequest_Interface.class).getKencan2().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Logger.d(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 有道翻译
     */
    private void request1() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(GetRequest_Interface.class).getCall("I Love You").enqueue(new Callback<Translationyd>() {
            @Override
            public void onResponse(Call<Translationyd> call, Response<Translationyd> response) {
                if (response.body() != null) {
                    Logger.d("翻译是：" + response.body().getTranslateResult().get(0).get(0).getTgt());
                }
            }

            @Override
            public void onFailure(Call<Translationyd> call, Throwable t) {

            }
        });
    }

    private void request() {
        //创建retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")//设置网络请求url
                .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析
                .build();
        //创建网络请求接口的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        request.getCall1().enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                if (response.body() != null) {
                    response.body().show();
                }
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {

            }
        });
    }
}
