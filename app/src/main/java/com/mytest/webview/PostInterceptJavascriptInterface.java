package com.mytest.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.webkit.JavascriptInterface;

import org.apache.commons.compress.utils.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class PostInterceptJavascriptInterface {
    public static final String TAG = "PostInterceptJavascriptInterface";

    private static String mInterceptHeader = null;
    private MyWebclient mWebViewClient = null;

    public PostInterceptJavascriptInterface(MyWebclient webViewClient) {
        mWebViewClient = webViewClient;
    }

    public static String enableIntercept(Context context, byte[] data) throws IOException {
        if (mInterceptHeader == null) {
            mInterceptHeader = new String(IOUtils.toByteArray(context.getAssets().open("www/interceptheader.html")), "utf-8");
        }

        Document doc = Jsoup.parse(new String(data, "utf-8"));
        doc.outputSettings().prettyPrint(true);
        // Prefix every script to capture submits
        // Make sure our interception is the first element in the
        // header
        Elements el = doc.getElementsByTag("head");
        if (el.size() > 0) {
            el.get(0).prepend(mInterceptHeader);
        }
        String pageContents = doc.toString();
        return pageContents;
    }

    @JavascriptInterface
    public void customAjax(final String method, final String body) throws UnsupportedEncodingException {
        mWebViewClient.nextMessageIsAjaxRequest(new AjaxRequestContents(method, (body + "")));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @JavascriptInterface
    public void customSubmit(String json, String method, String enctype) {
        mWebViewClient.nextMessageIsFormRequest(
                new FormRequestContents(method, json, enctype));
    }


    public class FormRequestContents {
        public String method = null;
        public String json = null;
        public String enctype = null;

        public FormRequestContents(String method, String json, String enctype) {
            this.method = method;
            this.json = json;
            this.enctype = enctype;
        }
    }

    public class AjaxRequestContents {
        public String method = null;
        public String body = null;

        public AjaxRequestContents(String method, String body) {
            this.method = method;
            this.body = body;
        }
    }

}
