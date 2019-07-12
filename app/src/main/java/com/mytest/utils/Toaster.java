package com.mytest.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class Toaster {
    private static Toast toaster;

    public static void show(Context context, CharSequence text, int length) {
        if (context instanceof Activity) {
            throw new IllegalArgumentException(
                    "使用Activity的context会造成内存泄漏,请使用Application");
        }
        if (toaster == null)
            toaster = Toast.makeText(context, text, length);
        else {
            toaster.setText(text);
            toaster.setDuration(length);
        }
        toaster.show();
    }

    public static void show(Context context, CharSequence text) {
        if (context instanceof Activity) {
            throw new IllegalArgumentException(
                    "使用Activity的context会造成内存泄漏,请使用Application");
        }
        if (toaster == null)
            toaster = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        else {
            toaster.setText(text);
            toaster.setDuration(Toast.LENGTH_SHORT);
        }
        toaster.show();
    }

    public void show(Context context, int resid, int length) {
        CharSequence text = context.getResources().getString(resid);
        show(context, text, length);
    }
}
