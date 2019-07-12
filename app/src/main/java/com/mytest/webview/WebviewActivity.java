package com.mytest.webview;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mytest.R;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

public class WebviewActivity extends AppCompatActivity {

    private WebView webView;
    private MyWebclient webclient;
    private SSLContext sslContext;

    //https://weappios.whwangdoudou.cn/iostest.php

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webview);
        try {
            webclient = new MyWebclient(this);
            PostInterceptJavascriptInterface interceptJavascriptInterface = new PostInterceptJavascriptInterface(webclient);
            interceptJavascriptInterface.customAjax("post","weappios.whwangdoudou.cn");
            //采用自定义WebViewClient
            webView.setWebViewClient(webclient);
            webView.requestFocus();
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBuiltInZoomControls(false);
            webSettings.setDisplayZoomControls(false);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

            String ua = webSettings.getUserAgentString();
            webSettings.setUserAgentString(ua); // add by LiuPeng 2018-08-03

            // localStorage读写
            webSettings.setDomStorageEnabled(true);// 打开本地缓存提供JS调用,至关重要
            webSettings.setAppCacheMaxSize(1024 * 1024 * 8);// 实现8倍缓存
            webSettings.setAllowFileAccess(true);
            webSettings.setAppCacheEnabled(true);
            String appCachePath = getApplication().getCacheDir().getAbsolutePath();
            webSettings.setAppCachePath(appCachePath);
            webSettings.setDatabaseEnabled(true);
            webView.addJavascriptInterface(this, "Android");

//            webView.loadUrl("https://testpdd.whwangdoudou.cn/");
            webView.loadUrl("https://weappios.whwangdoudou.cn/g/quiescent/login.html");
//            webView.loadUrl("file:///android_asset/test.html");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            jump_auth();
//            getPic();
//            upload_pic();
        }
    }

    private void postRunJS(String strJs) {
        final String jsUrl = "javascript:" + strJs;

        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 根据不同版本，使用不同的 API 执行 Js
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript(jsUrl, null);
                } else {
                    webView.loadUrl(jsUrl);
                }
            }
        }, 1);
    }

    @JavascriptInterface
    public void battery() {
        Logger.d("battery");
        postRunJS("battery_receive('" + "click" + "')");

    }


    public void jump_auth() {
        RequestParams params = new RequestParams("https://weappios.whwangdoudou.cn/iostest.php");
        params.setSslSocketFactory(webclient.getSslContext().getSocketFactory());
        x.http().post(
                params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Logger.d(s);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Logger.d("onError: " + throwable.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException e) {
                        Logger.d("onCancelled");
                    }

                    @Override
                    public void onFinished() {
                        Logger.d("onFinished");
                    }
                }
        );
    }

    public void getPic() {
        RequestParams params = new RequestParams("https://weappios.whwangdoudou.cn/80422351559728154.jpg");
        params.setSslSocketFactory(webclient.getSslContext().getSocketFactory());
        params.setSaveFilePath(getExternalCacheDir().getAbsolutePath()+"/test.jpg");
        params.setAutoRename(true);
        x.http().get(params, new Callback.CommonCallback<File>() {

            @Override
            public void onSuccess(File file) {
                Logger.d(file.getAbsolutePath());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Logger.d("onError: " + throwable.getMessage());
            }

            @Override
            public void onCancelled(CancelledException e) {
                Logger.d("onCancelled");
            }

            @Override
            public void onFinished() {
                Logger.d("onFinished");
            }
        });
    }

    public void upload_pic() {
        File file = new File(getExternalCacheDir().getAbsolutePath() + "/test.jpg");
        RequestParams params = new RequestParams("https://weappios.whwangdoudou.cn/iostest.php");
        params.setSslSocketFactory(webclient.getSslContext().getSocketFactory());
        params.setMultipart(true);
        params.setAsJsonContent(true);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("pic", file));
        list.add(new KeyValue("m", "upload_pic"));
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);
        x.http().post(
                params,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Logger.d(s);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Logger.d("onError: " + throwable.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException e) {
                        Logger.d("onCancelled");
                    }

                    @Override
                    public void onFinished() {
                        Logger.d("onFinished");
                    }
                }
        );
    }
}
