package com.mytest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mytest.adapterview.ViewActivity;
import com.mytest.animator.AnimatorActivity;
import com.mytest.applicationArchitecture.mvp.presenter.IpInfoActivity;
import com.mytest.base.BaseActivity;
import com.mytest.blur.BitmapBlurActivity;
import com.mytest.bottomNavigation.BottomNavigationActivity;
import com.mytest.cameraphoto.ChoosePicActivity;
import com.mytest.customview.CustomActivity;
import com.mytest.customview.qqredpoint.QQRedPointActivity;
import com.mytest.datastore.DataStoreActivity;
import com.mytest.datastore.GreenDaoActivity;
import com.mytest.dialog.DialogActivity;
import com.mytest.eventbus.EventBusActivity;
import com.mytest.gridlayout.ShowBigPicActivity;
import com.mytest.internet.NetworkActivity;
import com.mytest.location.LocationActivity;
import com.mytest.materialdesign.DesignActivity;
import com.mytest.notification.NotificationActivity;
import com.mytest.retrofit.RetrofitActivity;
import com.mytest.rxjava.RxjavaActivity;
import com.mytest.servicepractice.DownloadActivity;
import com.mytest.smartrefresh.SmartActivity;
import com.mytest.timershaft.TimerShaftActivity;
import com.mytest.webview.WebviewActivity;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionGen;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_PERMISSION_SETTING = 200;
    @BindView(R.id.btn_custom)
    Button btn_custom;
    @BindView(R.id.btn_smart)
    Button btn_smart;
    @BindView(R.id.btn_test)
    Button btn_test;
    @BindView(R.id.btn_gridlayout)
    Button btn_gridlayout;
    @BindView(R.id.btn_adapterview)
    Button btn_adapterview;
    @BindView(R.id.btn_animator)
    Button btn_animator;
    @BindView(R.id.btn_timer_shaft)
    Button btn_timer_shaft;
    @BindView(R.id.btn_notification)
    Button btn_notification;
    @BindView(R.id.btn_camera)
    Button btn_camera;
    @BindView(R.id.btn_eventbus)
    Button btn_eventbus;
    @BindView(R.id.btn_network)
    Button btn_network;
    @BindView(R.id.btn_location)
    Button btn_location;
    @BindView(R.id.btn_data_store)
    Button btn_data_store;
    @BindView(R.id.btn_service_practice)
    Button btn_service_practice;
    @BindView(R.id.btn_material_design)
    Button btn_material_design;
    @BindView(R.id.btn_dialog)
    Button btn_dialog;
    @BindView(R.id.btn_qq_redpoint)
    Button btn_qq_redpoint;
    @BindView(R.id.btn_greendao_test)
    Button btn_greendao_test;
    @BindView(R.id.btn_blur)
    Button btn_blur;
    @BindView(R.id.btn_navigation)
    Button btn_navigation;


    @BindView(R.id.tv)
    TextView tv;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static final int CODE = 100;

    @Override
    public int initLayout() {
        //AppCompatActivity隐藏标题栏
//            if(getSupportActionBar() != null){
//                getSupportActionBar().hide();
//            }
        //activity隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_main;
    }

    /**
     * 异常情况下Activity 重建，非用户主动去销毁
     * 系统异常终止时，调用onSavaInstanceState来保存状态
     * 该方法调用在onStop之前，但和onPause没有时序关系
     * onSaveInstanceState与onPause的区别：前者适用于对临时性状态的保存，而后者适用于对数据的持久化保存
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Activity被重新创建时，调用onRestoreInstanceState（该方法在onStart之后），并将onSavaInstanceState
     * 保存的Bundle对象作为参数传到onRestoreInstanceState与onCreate方法
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }



    @Override
    public void initIntent() {
        setSupportActionBar(toolbar);
    }

    @Override
    public void addListener() {
        btn_custom.setOnClickListener(this);
        btn_smart.setOnClickListener(this);
        btn_test.setOnClickListener(this);
        btn_gridlayout.setOnClickListener(this);
        btn_adapterview.setOnClickListener(this);
        btn_animator.setOnClickListener(this);
        btn_timer_shaft.setOnClickListener(this);
        btn_notification.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        btn_eventbus.setOnClickListener(this);
        btn_network.setOnClickListener(this);
        btn_location.setOnClickListener(this);
        btn_data_store.setOnClickListener(this);
        btn_service_practice.setOnClickListener(this);
        btn_material_design.setOnClickListener(this);
        btn_dialog.setOnClickListener(this);
        btn_qq_redpoint.setOnClickListener(this);
        btn_greendao_test.setOnClickListener(this);
        btn_blur.setOnClickListener(this);
        btn_navigation.setOnClickListener(this);
    }


    @Override
    public void initData() {
//        Toaster.show(getApplicationContext(),getPackageName());
//        startActivity(new Intent(this, com.weapp.wj.ui.MainActivity.class).putExtra("channel","bsbwj")
//                .putExtra("third_uid","1").putExtra("phone","18876386487"));
//        StaticFans fans = new StaticFans();
//        fans.otherMethod(123);
//        fans.<Integer>otherMethod(123);

//        Class<?>[] clazzes = Reflect.getAllInterface(AnimalImpl.class);
//        SpannableStringBuilder builder = new SpannableStringBuilder();
//        for (Class clazz : clazzes){
//            builder.append(clazz.getName());
//            builder.append("  ");
//        }
//        Log.e("mytest", "AnimalImpl继承的所有接口:"+ builder.toString());

        //枚举
//        Class cla = Person.class;
//        Constructor[] constructors = cla.getDeclaredConstructors();
//        for (Constructor item : constructors){
//            Logger.d("枚举到的构造函数："+item.toString());
//        }
//
//        try {
//            Constructor constructor = cla.getDeclaredConstructor(Integer.class,String.class);
//            Logger.d("指定参数得到的构造函数："+constructor.toString());
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//        Map<String,String> map = new HashMap<>();
//        for(Map.Entry<String,String> entry: map.entrySet()){
//
//        }

//        PermissionGen.with(MainActivity.this).addRequestCode(100).permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).request();


//        DialogUtil.showProgress(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);

//        steepStatusBar();
        //打开浏览器
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("http://www.baidu.com"));
//        startActivity(intent);

        // 同一服务只会开启1个工作线程
        // 在onHandleIntent（）函数里，依次处理传入的Intent请求
        // 将请求通过Bundle对象传入到Intent，再传入到服务里

//        // 请求1
//        Intent i = new Intent(this,MyIntentService.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("taskName", "task1");
//        i.putExtras(bundle);
//        startService(i);
//
//        // 请求2
//        Intent i2 = new Intent(this,MyIntentService.class);
//        Bundle bundle2 = new Bundle();
//        bundle2.putString("taskName", "task2");
//        i2.putExtras(bundle2);
//        startService(i2);
//
//        startService(i);  //多次启动
//
//        //启动长期在后台执行定时任务的服务
//        startService(new Intent(this,LongRunningService.class));
//        getApplicationMsg();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
    }

//    @PermissionSuccess(requestCode = 100)
//    private void test() {
////        OkhttpUtils.okhttpUploadFile();
////        OkhttpUtils.okhttpGet();
////        OkHttpEngine.getInstance(this).getAsynHttp("http://blog.csdn.net/itachi85", new ResultCallback() {
////            @Override
////            public void onError(Request request, Exception e) {
////
////            }
////
////            @Override
////            public void onResponse(Response response) throws IOException {
////                Log.e("mytest", "okhttpGet: -- Main " + response.body().string());
////            }
////        });
//        Main.createChannel(String.valueOf(Environment.getExternalStorageDirectory() + "/dzm_qudao.txt"));
//    }
//
//    @PermissionFail(requestCode = 100)
//    private void fail() {
//        Toaster.show(this, "请求权限失败");
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_custom:
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://www.baidu.com"));
//                startActivity(intent);
                startActivity(new Intent(this, CustomActivity.class));
                break;
            case R.id.btn_smart:
                startActivity(new Intent(this, SmartActivity.class));
                break;
            case R.id.btn_test:
                startActivity(new Intent(MainActivity.this, TestActivity.class));
                break;
            case R.id.btn_gridlayout:
                startActivity(new Intent(this, ShowBigPicActivity.class));
                break;
            case R.id.btn_adapterview:
                startActivity(new Intent(this, ViewActivity.class));
                break;
            case R.id.btn_animator:
                startActivity(new Intent(this, AnimatorActivity.class));
                break;
            case R.id.btn_timer_shaft:
                startActivity(new Intent(this, TimerShaftActivity.class));
                break;
            case R.id.btn_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.btn_camera:
//                startActivity(new Intent(this, CameraAlbumTestActivity.class));
                startActivity(new Intent(this, ChoosePicActivity.class));
                break;
            case R.id.btn_eventbus:
                startActivity(new Intent(this, EventBusActivity.class));
                break;
            case R.id.btn_network:
                startActivity(new Intent(this, NetworkActivity.class));
                break;
            case R.id.btn_location:
                startActivity(new Intent(this, LocationActivity.class));
                break;
            case R.id.btn_data_store:
                startActivity(new Intent(this, DataStoreActivity.class));
                break;
            case R.id.btn_service_practice:
                startActivity(new Intent(this, DownloadActivity.class));
                break;
            case R.id.btn_material_design:
                startActivity(new Intent(this, DesignActivity.class));
                break;
            case R.id.btn_dialog:
                startActivity(new Intent(this, DialogActivity.class));
                break;
            case R.id.btn_qq_redpoint:
                startActivity(new Intent(this, QQRedPointActivity.class));
                break;
            case R.id.btn_greendao_test:
                startActivity(new Intent(this, GreenDaoActivity.class));
                break;
            case R.id.btn_blur:
                startActivity(new Intent(this, BitmapBlurActivity.class));
                break;
            case R.id.btn_navigation:
                startActivity(new Intent(this, BottomNavigationActivity.class));
                break;
        }
    }

    /*
    判断版本号，版本号小于19不好使
    */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Toast.makeText(MainActivity.this, "you click add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(MainActivity.this, "you click remove", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnClick(R.id.btn_retrofit)
    public void btn_retrofit() {
        startActivity(new Intent(MainActivity.this, RetrofitActivity.class));
    }

    @OnClick(R.id.btn_rxjava)
    public void btn_rxjava() {
        startActivity(new Intent(MainActivity.this, RxjavaActivity.class));
    }

    @OnClick(R.id.btn_mvp)
    public void btn_mvp() {
        startActivity(new Intent(MainActivity.this, WebviewActivity.class));
    }

    @OnClick(R.id.btn_data_picker)
    public void btn_data_picker() {
        startActivity(new Intent(MainActivity.this, DataPickerActivity.class));
    }
}
