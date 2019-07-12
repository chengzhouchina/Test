package com.mytest.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.mytest.R;
import com.mytest.base.BaseActivity;
import com.orhanobut.logger.Logger;

import static android.app.Notification.VISIBILITY_SECRET;

public class NotificationActivity extends BaseActivity {

    private NotificationManager mNotificationManager;

    @Override
    public int initLayout() {
        return R.layout.activity_notification;
    }

    @Override
    public void initIntent() {
    }

    @Override
    public void addListener() {
    }

    @Override
    public void initData() {
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
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        builder.setContentIntent(pendingIntent);

//        notificationManager.notify(1, builder.build());
//        NotificationManagerCompat notification = NotificationManagerCompat.from(this);
//        boolean isEnabled = notification.areNotificationsEnabled();
//        Logger.d(isEnabled);
//        notification(isEnabled);
//        Tongzhi();

        test();
    }

    private void test() {
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,getPackageName())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setWhen(System.currentTimeMillis())
//                .setAutoCancel(true)
//                .setContentTitle("ContentTitle")
//                .setContentText("Content Text Here");
//        //适配8.0
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(getPackageName(), "mytest", NotificationManager.IMPORTANCE_HIGH);
//            // 设置是否应在锁定屏幕上显示此频道的通知
//            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//            // 设置是否显示角标
//            channel.setShowBadge(true);
//            notificationManager.createNotificationChannel(channel);
//            mBuilder.setChannelId(getPackageName());
//        }
//
//        notificationManager.notify(1,mBuilder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            //是否绕过请勿打扰模式
            channel.canBypassDnd();
            //闪光灯
            channel.enableLights(true);
            //锁屏显示通知
            channel.setLockscreenVisibility(VISIBILITY_SECRET);
            //闪光灯的灯光颜色
            channel.setLightColor(Color.RED);
            //桌面launcher的消息角标
            channel.canShowBadge();
            //是否允许震动
            channel.enableVibration(true);
            //获取系统通知响铃声音的配置
            channel.getAudioAttributes();
            //获取通知取到组
            channel.getGroup();
            //设置可绕过 请勿打扰配置
            channel.setBypassDnd(true);
            //设置震动模式
            channel.setVibrationPattern(new long[]{100, 100, 200});
            //是否会有灯光
            channel.shouldShowLights();
//            getNotificationManager().createNotificationChannel(channel);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "channel_id");

        notification.setContentTitle("新消息来了");
        notification.setContentText("周末到了，不用上班了");
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setAutoCancel(true);
        notification.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notification.setChannelId("channel_id");
        getNotificationManager().notify(1, notification.build());
    }

    @Override
    public void onClick(View view) {

    }

    private void notification(boolean isEnabled) {
        if (!isEnabled) {
            //未打开通知
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请在“通知”中打开通知权限")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent = new Intent();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("android.provider.extra.APP_PACKAGE", NotificationActivity.this.getPackageName());
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("app_package", NotificationActivity.this.getPackageName());
                                intent.putExtra("app_uid", NotificationActivity.this.getApplicationInfo().uid);
                                startActivity(intent);
                            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + NotificationActivity.this.getPackageName()));
                            } else if (Build.VERSION.SDK_INT >= 15) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", NotificationActivity.this.getPackageName(), null));
                            }
                            startActivity(intent);

                        }
                    })
                    .create();
            alertDialog.show();
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        } else {
//            createNotification();
            createNewNotifi();
        }
    }

    private void createNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(NotificationActivity.this)
                //设置顶部状态栏（未被下拉）中的小图标
                //在有些机子上显示的小图标只显示了一部分，并不会自适应大小，可以把图片的大小改成32*32的小图标，并放在hdpi文件夹下
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置状态栏（未被下拉）的提示文字
                .setTicker("")
                //设置通知面板中显示的时间，通常传一个系统当前的时间戳进去
                .setWhen(System.currentTimeMillis())
                //设置通知面板左边的大图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                //设置通知面板中第一栏标题的显示文本
                .setContentTitle("")
                //设置通知面板中第二栏内容的显示文本
                .setContentText(getResources().getString(R.string.app_name))
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

//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        builder.setContentIntent(pendingIntent);
        notificationManager.notify(2, builder.build());
    }

    private void createNewNotifi() {
        Logger.d(Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT);
            //是否绕过请勿打扰模式
            channel.canBypassDnd();
            //闪光灯
            channel.enableLights(true);
            //锁屏显示通知
            channel.setLockscreenVisibility(VISIBILITY_SECRET);
            //闪光灯的灯光颜色
            channel.setLightColor(Color.RED);
            //桌面launcher的消息角标
            channel.canShowBadge();
            //是否允许震动
            channel.enableVibration(true);
            //获取系统通知响铃声音的配置
            channel.getAudioAttributes();
            //获取通知取到组
            channel.getGroup();
            //设置可绕过 请勿打扰配置
            channel.setBypassDnd(true);
            //设置震动模式
            channel.setVibrationPattern(new long[]{100, 100, 200});
            //是否会有灯光
            channel.shouldShowLights();
//            getNotificationManager().createNotificationChannel(channel);
        }
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "channel_id");

        notification.setContentTitle("新消息来了");
        notification.setContentText("周末到了，不用上班了");
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setAutoCancel(true);
        notification.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notification.setChannelId("channel_id");
        getNotificationManager().notify(1, notification.build());
    }

    private NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }

    private void Tongzhi() {
        //获取系统提供的通知管理服务
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //判断是否为8.0以上系统，是的话新建一个通道
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //创建一个通道 一参：id  二参：name 三参：统通知的优先级
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("chId", "聊天信息", NotificationManager.IMPORTANCE_MAX);
            mNotificationManager.createNotificationChannel(channel);//创建
            channel.setVibrationPattern(new long[]{0});//通道来控制震动
            tong();
            Toast.makeText(this, "8.0", Toast.LENGTH_SHORT).show();
        } else {
            tong();
            Toast.makeText(this, "no8.0", Toast.LENGTH_SHORT).show();
        }
    }

    private void tong() {
//        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//获取管理类的实例
//        Intent intent = new Intent(this, MainActivity.class);
//        //PendingIntent点击通知后跳转，一参：context 二参：一般为0 三参：intent对象 四参：一般为0
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        Notification builder = new NotificationCompat.Builder(this, "chId")
//                .setContentTitle("this is a notification")//标题
//                .setContentText("this is a content:lalalallalaal")//内容
//                .setSmallIcon(R.drawable.ic_launcher_background)//图片
//                .setContentIntent(pendingIntent)//点击通知跳转
//                .setAutoCancel(true)//完成跳转自动取消通知
//                .build();
//        mNotificationManager.notify(1, builder);//让通知显示出来
        // 获取系统 通知管理 服务
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// 构建 Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,null);
        builder.setContentTitle("ContentTitle")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("Content Text Here");

        // 兼容  API 26，Android 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 第三个参数表示通知的重要程度，默认则只在通知栏闪烁一下
            NotificationChannel notificationChannel = new NotificationChannel("AppTestNotificationId", "AppTestNotificationName", NotificationManager.IMPORTANCE_DEFAULT);
            // 注册通道，注册后除非卸载再安装否则不改变
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId("AppTestNotificationId");
        }
        // 发出通知
        notificationManager.notify(100000, builder.build());
    }
}
