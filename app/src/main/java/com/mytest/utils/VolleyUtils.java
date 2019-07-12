package com.mytest.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by PeterLee on 15/4/3.
 */
public class VolleyUtils {
    private static VolleyUtils mInstance = null;
    private RequestQueue mRequestQueue;
    private final RequestQueue mMultiPartRequestQueue;  // 用于上传文件的RequestQueue
    private ImageLoader mImageLoader;

    private VolleyUtils(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
//        mMultiPartRequestQueue = Volley.newRequestQueue(context, new MultiPartStack());
        mMultiPartRequestQueue = Volley.newRequestQueue(context, null);
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
    }

    public static VolleyUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyUtils(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public RequestQueue getMultiPartRequestQueue() {
        return mMultiPartRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public void release() {
        this.mImageLoader = null;
        this.mRequestQueue = null;
        mInstance = null;
    }
}
