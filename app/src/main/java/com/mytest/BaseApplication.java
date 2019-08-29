package com.mytest;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.mytest.datastore.gen.DaoMaster;
import com.mytest.datastore.gen.DaoSession;
import com.mytest.service.MyService;
import com.mytest.webview.NetConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

public class BaseApplication extends Application {

    public static final String TAG = "mytest";

    private static Context context;
    public static BaseApplication instances;

    private static DaoSession daoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化handler
        mHandler = new Handler();

        context = getApplicationContext();
        instances = this;

        startService(new Intent(this, MyService.class));

        //配置数据库
        setupDatabase();

        //Logger初始化
//        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return super.isLoggable(priority, tag);
//                return DEBUG;
            }
        });

        //步数宝初始化
//        com.weapp.wj.util.WUtils.init(this);

        //初始化
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());

        readCer();

        //xutils初始化
        x.Ext.init(this);
    }

    /**
     * DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
     * <p>
     * DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
     * <p>
     * DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
     */
    private void setupDatabase() {
        //创建数据库shop.db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static Application getInstance() {
        return instances;
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    public static Context getContext() {
        return context;
    }

    //qcl用来在主线程中刷新ui
    private static Handler mHandler;

    /**
     * 在主线程中刷新UI的方法
     *
     * @param r
     */
    public static void runOnUIThread(Runnable r) {
        BaseApplication.getMainHandler().post(r);
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    /**
     * 读取cer证书
     */
    private void readCer() {
        // 添加https证书
        try {
            InputStream is = getAssets().open("server.cer");
            NetConfig.addCertificate(is); // 这里将证书读取出来，，放在配置中byte[]里

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
