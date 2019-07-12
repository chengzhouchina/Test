package com.mytest.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.mytest.BaseApplication;
import com.mytest.MainActivity;
import com.mytest.R;
import com.mytest.materialdesign.DesignActivity;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {
        super.onCreate();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
////        Notification notification = new Notification(R.drawable.app_icon,"Notification comes",System.currentTimeMillis());
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification.Builder builder = new Notification.Builder(this.getApplicationContext())
//                //设置顶部状态栏（未被下拉）中的小图标
//                //在有些机子上显示的小图标只显示了一部分，并不会自适应大小，可以把图片的大小改成32*32的小图标，并放在hdpi文件夹下
//                .setSmallIcon(R.mipmap.ic_launcher)
//                //设置状态栏（未被下拉）的提示文字
//                .setTicker("你有一条新消息")
//                //设置通知面板中显示的时间，通常传一个系统当前的时间戳进去
//                .setWhen(System.currentTimeMillis())
//                //设置通知面板左边的大图标
//                .setLargeIcon(bitmap)
//                //设置通知面板中第一栏标题的显示文本
//                .setContentTitle("这是标题")
//                //设置通知面板中第二栏内容的显示文本
//                .setContentText("这是文本内容")
//                //设置为true时，用户点击后关闭，为false时点击不关闭，默认为false
//                .setAutoCancel(false)
//                //设置系统默认的显示参数，在Notification中，默认的系统样式已经定义成参量类型了，我们只要使用就好
//                //Notification.DEFAULT_ALL：铃声、闪光、震动均系统默认
//                //Notification.DEFAULT_SOUND：系统默认铃声
//                //Notification.DEFAULT_LIGHTS：系统默认闪光灯
//                //Notification.DEFAULT_VIBRATE：系统默认震动
//                .setDefaults(Notification.DEFAULT_ALL)
//                //设置是否需要把Notification放置在“正在运行”的栏目
//                .setOngoing(true);
//
//        Intent notificationIntent = new Intent(this, DesignActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        builder.setContentIntent(pendingIntent);
//        notificationManager.notify(1, builder.build());
//
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(BaseApplication.getContext())
                //设置顶部状态栏（未被下拉）中的小图标
                //在有些机子上显示的小图标只显示了一部分，并不会自适应大小，可以把图片的大小改成32*32的小图标，并放在hdpi文件夹下
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置状态栏（未被下拉）的提示文字
                .setTicker("")
                //设置通知面板中显示的时间，通常传一个系统当前的时间戳进去
                .setWhen(System.currentTimeMillis())
                //设置通知面板左边的大图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                //设置通知面板中第一栏标题的显示文本
                .setContentTitle("")
                //设置通知面板中第二栏内容的显示文本
                .setContentText(getResources().getString(R.string.tips))
                //设置为true时，用户点击后关闭，为false时点击不关闭，默认为false
                .setAutoCancel(true)
                //设置系统默认的显示参数，在Notification中，默认的系统样式已经定义成参量类型了，我们只要使用就好
                //Notification.DEFAULT_ALL：铃声、闪光、震动均系统默认
                //Notification.DEFAULT_SOUND：系统默认铃声
                //Notification.DEFAULT_LIGHTS：系统默认闪光灯
                //Notification.DEFAULT_VIBRATE：系统默认震动
                .setDefaults(Notification.DEFAULT_ALL)
                //设置是否需要把Notification放置在“正在运行”的栏目
                .setOngoing(true);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(1, builder.build());
    }
}
