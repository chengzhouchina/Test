package com.mytest.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.mytest.R;

import java.lang.ref.WeakReference;

public class DialogUtil {

    private static Dialog sDialog;

    public static void showProgress(Context context){
        //弱引用
        WeakReference<Context> weakReference = new WeakReference<>(context);
        if(sDialog == null){
            init(weakReference.get());
        }
        sDialog.show();
    }

    public static void dismissDialog(){
        if (sDialog.isShowing()){
            sDialog.dismiss();
        }
    }
    private static void init(Context context) {
        sDialog = new Dialog(context);
        //修改对话框默认背景为透明,因为不这么设置的话，对话框默认是白色的
        //然后你自定义的背景是黑色且有圆角,相信我，你不想看到的
        Window window = sDialog.getWindow();
        if (window != null){
            window.setBackgroundDrawableResource(android.R.color.transparent);
            //修改dialog宽高
            WindowManager windowManager = window.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (display.getWidth() * 0.68);
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
            window.setGravity(Gravity.CENTER);
        }
        // 设置自己编写的布局文件，即刚才有 ProgressBar 和 TextView 的那个布局文件
        sDialog.setContentView(R.layout.layout_dialog_bg);
        // 设置不可点击或点按返回键取消对话框，这样相当于禁止了用户与界面的交互
        // 实际情况根据需求而定
//        sDialog.setCancelable(false);
//        sDialog.setCanceledOnTouchOutside(false);
    }
}
