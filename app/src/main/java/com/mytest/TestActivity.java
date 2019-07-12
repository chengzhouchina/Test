package com.mytest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mytest.bean.CallInfo;
import com.mytest.bean.PhoneDto;
import com.mytest.bean.SmsInfo;
import com.mytest.utils.AudioUtil;
import com.mytest.utils.LocationUtil;
import com.mytest.utils.PhoneUtil;
import com.mytest.utils.SensorUtil;
import com.mytest.utils.Toaster;
import com.mytest.widget.CameraView;

import junit.framework.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Activity {

    private int targetversion = 0; //版本号
    private String cameraFilePath;
    private String soundsFilePath;//音频路径
    private static final int CAMERA_PERMISSION = 1;
    private static final int PHONE_PERMISSION = 2;
    private static final int CAMERA_PATH_CALL_BACK = 3;

    private List<PhoneDto> phoneInfos = new ArrayList<>();
    private List<SmsInfo> smsInfos = new ArrayList<>();
    private List<CallInfo> callInfos = new ArrayList<>();

    //录音
    private MediaRecorder mr = null;

    private boolean isStart = false;

    private Camera camera;
    private CameraView cameraView;
    private static final int FRONT = 1;//前置摄像头标记
    private static final int BACK = 2;//后置摄像头标记
    private int currentCameraType = -1;//当前打开的摄像头标记

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //使用兼容库就无需判断系统版本
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            targetversion = info.applicationInfo.targetSdkVersion;
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(!checkCamera()){
            finish();
        }
        try {
            camera = openCamera(FRONT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cameraView = (CameraView)findViewById(R.id.cameraview);
        cameraView.init(camera);

        Button btn_get_phone_msg = findViewById(R.id.btn_get_phone_msg);
        btn_get_phone_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoneMsg();
            }
        });

        final Button btn_audio = findViewById(R.id.btn_audio);
        btn_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int audio;
                    if (targetversion >= Build.VERSION_CODES.M) {
                        audio = ContextCompat.checkSelfPermission(TestActivity.this, Manifest.permission.RECORD_AUDIO);
                    } else {
                        audio = PermissionChecker.checkSelfPermission(TestActivity.this, Manifest.permission.RECORD_AUDIO);
                    }
                    if (audio == PackageManager.PERMISSION_GRANTED) {
                        if (!isStart) {
                            btn_audio.setText("结束录制");
//                            startRecord();
                            AudioUtil.startRecord();
                            isStart = true;
                        } else {
                            btn_audio.setText("开始录制");
//                            stopRecord();
                            AudioUtil.stopRecord();
                            isStart = false;
                        }
                    } else {
                        //没有权限，向用户请求权限
                        if (targetversion >= Build.VERSION_CODES.M) {
                            ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                        } else {
                            TestActivity.this.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, CAMERA_PERMISSION);
                        }
                    }
                } else {
                    if (!isStart) {
//                        startRecord();
                        AudioUtil.startRecord();
                        isStart = true;
                    } else {
//                        stopRecord();
                        AudioUtil.stopRecord();
                        isStart = false;
                    }
                }
            }
        });

        Button btn_sensor_open = findViewById(R.id.btn_sensor_open);
        btn_sensor_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                createSensor();
                SensorUtil.createSensor(TestActivity.this,Sensor.TYPE_ACCELEROMETER);
            }
        });
        Button btn_sensor_close = findViewById(R.id.btn_sensor_close);
        btn_sensor_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                createSensor();
                SensorUtil.unregisterSensor();
            }
        });

        final SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        final BatteryChangeReceiver batteryChangeReceiver = new BatteryChangeReceiver(editor);
        registerReceiver(batteryChangeReceiver,intentFilter);

        final ImageView imageView = findViewById(R.id.iv_test);
        Button btn_camera = findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                requestCameraAndAlbumPermission();
//                try {
//                    changeCamera();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        // 根据拍照所得的数据创建位图
                        final Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                        imageView.setImageBitmap(bm);
                    }
                });
//                //重新浏览
//                camera.stopPreview();
//                camera.startPreview();
            }
        });
        LocationUtil.initLocation(TestActivity.this);
        Button btn_location = findViewById(R.id.btn_location);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Battery click--",LocationUtil.getAddress()+"");
                camera.stopPreview();
                camera.startPreview();
            }
        });

        Button btn_sport = findViewById(R.id.btn_sport);
        btn_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sensorManager != null) {
                    sensorManager = null;
                }
                //获取传感器管理类
                sensorManager = (SensorManager) TestActivity.this.getSystemService(SENSOR_SERVICE);
                int versionCodes = Build.VERSION.SDK_INT;//取得SDK版本
                if (versionCodes >= 19) {
                    //SDK版本大于等于19开启计步传感器
                    addCountStepListener();
                } else {        //小于就使用加速度传感器
                    addBasePedometerListener();
                }
            }
        });

    }


    /**
     * 获取手机通讯录、通话记录、短信
     */
    private void getPhoneMsg() {
        int read_contacts;
        int read_phone;
        int read_sms;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (targetversion >= Build.VERSION_CODES.M) {
                read_contacts = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                read_phone = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG);
                read_sms = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

            } else {
                read_contacts = PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                read_phone = PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG);
                read_sms = PermissionChecker.checkSelfPermission(this, Manifest.permission.READ_SMS);
            }

            if (read_contacts == PackageManager.PERMISSION_GRANTED && read_phone == PackageManager.PERMISSION_GRANTED
                    && read_sms == PackageManager.PERMISSION_GRANTED) {
                //有权限，执行操作
                phoneInfos = PhoneUtil.getPhone(this);
                smsInfos = PhoneUtil.getSms(this);
                callInfos = PhoneUtil.getCallLog(this);
                for (PhoneDto phoneDto : phoneInfos) {
                    Log.e("PhoneDto-- ", phoneDto.getName());
                    Log.e("PhoneDto-- ", phoneDto.getTelPhone());
                }
                for (SmsInfo phoneDto : smsInfos) {
                    Log.e("SmsInfo-- ", phoneDto.getName());
                    Log.e("SmsInfo-- ", phoneDto.getBody());
                    Log.e("SmsInfo-- ", phoneDto.getNumber());
                }
                for (CallInfo phoneDto : callInfos) {
                    Log.e("CallInfo-- ", phoneDto.getDate() + "");
                    Log.e("CallInfo-- ", phoneDto.getType() + "");
                    Log.e("CallInfo-- ", phoneDto.getNumber());
                }
            } else {
                //没有权限，向用户请求权限
                if (targetversion >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS}, PHONE_PERMISSION);
                } else {
                    this.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG,
                            Manifest.permission.READ_SMS}, PHONE_PERMISSION);
                }
            }
        } else {
            phoneInfos = PhoneUtil.getPhone(this);
            smsInfos = PhoneUtil.getSms(this);
            callInfos = PhoneUtil.getCallLog(this);
            for (PhoneDto phoneDto : phoneInfos) {
                Log.e("PhoneDto-- ", phoneDto.getName());
                Log.e("PhoneDto-- ", phoneDto.getTelPhone());
            }
            for (SmsInfo phoneDto : smsInfos) {
                Log.e("SmsInfo-- ", phoneDto.getName());
                Log.e("SmsInfo-- ", phoneDto.getBody());
                Log.e("SmsInfo-- ", phoneDto.getNumber());
            }
            for (CallInfo phoneDto : callInfos) {
                Log.e("CallInfo-- ", phoneDto.getDate() + "");
                Log.e("CallInfo-- ", phoneDto.getType() + "");
                Log.e("CallInfo-- ", phoneDto.getNumber());
            }
        }
    }


    //拍照
    public void requestCameraAndAlbumPermission() {
        File dir = new File(Environment.getExternalStorageDirectory(), "image");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        cameraFilePath = System.currentTimeMillis() + ".jpg";
        File soundFile = new File(dir, cameraFilePath);
        if (!soundFile.exists()) {
            try {
                soundFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int camera;
            if (targetversion >= Build.VERSION_CODES.M) {
                camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            } else {
                camera = PermissionChecker.checkSelfPermission(this, Manifest.permission.CAMERA);
            }
            if (camera == PackageManager.PERMISSION_GRANTED) {
                selectPicFromCamera(this, CAMERA_PATH_CALL_BACK, cameraFilePath);
            } else {
                //没有权限，向用户请求权限
                if (targetversion >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(TestActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    this.requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                }
            }
        } else {
            selectPicFromCamera(this, CAMERA_PATH_CALL_BACK, cameraFilePath);
        }
    }


    /**
     * 通过拍照获取图片
     */
    private void selectPicFromCamera(Activity activity, int requestCode, String cameraFilePath) {
        Context context = null;
        if (context != null) {
            try {
                if (!isExitsSdcard()) {
                    Toaster.show(getApplicationContext(), "SD卡不存在,不能拍照");
                    return;
                }

                File cameraFile = new File(cameraFilePath);
                cameraFile.getParentFile().mkdirs();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName()
                            + ".provider", cameraFile);
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT,
                            photoURI), requestCode);
                } else {
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(cameraFile)), requestCode);

                }
            } catch (Exception e) {
                Toaster.show(getApplicationContext(), "拍照不可用");
            }
        }
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    //开始录制音频
    private void startRecord() {
        if (mr == null) {
            File dir = new File(Environment.getExternalStorageDirectory(), "sounds");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            soundsFilePath = System.currentTimeMillis() + ".amr";
            File soundFile = new File(dir, soundsFilePath);
            if (!soundFile.exists()) {
                try {
                    soundFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);  //音频输入源
            mr.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);   //设置输出格式
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);   //设置编码格式
            mr.setOutputFile(soundFile.getAbsolutePath());
            try {
                mr.prepare();
                mr.start();  //开始录制
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //停止录制，资源释放
    private void stopRecord() {
        if (mr != null) {
            mr.stop();
            mr.release();
            mr = null;
        }
    }

    //重力传感器
//    private SensorManager sensorManager;
//    private SensorEventListener listener = new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//
//            //可以得到传感器实时测量出来的变化值
//            float x = event.values[0];
//            float y = event.values[1];
//            float z = event.values[2];
//            Log.e("Sensor x-- ",x+"");
//            Log.e("Sensor y-- ",y+"");
//            Log.e("Sensor z-- ",z+"");
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//            Log.e("Sensor", "sensor: " + sensor + ", accuracy" + accuracy);
//        }
//    };

//    private void createSensor(){
//        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_UI);
//    }
//
//    private void unregisterSensor(){
//        sensorManager.unregisterListener(listener);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterSensor();
//        File file = new File(soundsFilePath);
//        if (file.exists())
//            file.delete();
//
//        soundsFilePath = "";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
        }
    }


    /**
     * @return 摄像头是否存在
     */
    private boolean checkCamera(){
        return TestActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @SuppressLint("NewApi")
    private Camera openCamera(int type){
        int frontIndex =-1;
        int backIndex = -1;
        int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for(int cameraIndex = 0; cameraIndex<cameraCount; cameraIndex++){
            Camera.getCameraInfo(cameraIndex, info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                frontIndex = cameraIndex;
            }else if(info.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                backIndex = cameraIndex;
            }
        }

        currentCameraType = type;
        if(type == FRONT && frontIndex != -1){
            return Camera.open(frontIndex);
        }else if(type == BACK && backIndex != -1){
            return Camera.open(backIndex);
        }
        return null;
    }

    private void changeCamera() throws IOException{
        camera.stopPreview();
        camera.release();
        if(currentCameraType == FRONT){
            camera = openCamera(BACK);
        }else if(currentCameraType == BACK){
            camera = openCamera(FRONT);
        }
        camera.setPreviewDisplay(cameraView.getHolder());
        camera.startPreview();
//        camera.takePicture();
    }

    private SensorManager sensorManager;
    private int stepSensorType;
//    private StepCount mStepCount;

    /**
     * 启动计步传感器计步
     */
    private void addCountStepListener() {
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            stepSensorType = Sensor.TYPE_STEP_COUNTER;
            sensorManager.registerListener((SensorEventListener) TestActivity.this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("计步传感器类型", "Sensor.TYPE_STEP_COUNTER");
        } else if (detectorSensor != null) {
            stepSensorType = Sensor.TYPE_STEP_DETECTOR;
            sensorManager.registerListener((SensorEventListener) TestActivity.this, detectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            addBasePedometerListener();
        }
    }

    /**
     * 启动加速度传感器计步
     */
    private void addBasePedometerListener() {
//        Log.i("BindService", "加速度传感器");
//        mStepCount = new StepCount();
//        mStepCount.setSteps(nowBuSu);
//        //获取传感器类型 获得加速度传感器
//        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        //此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
//        boolean isAvailable = sensorManager.registerListener(mStepCount.getStepDetector(), sensor, SensorManager.SENSOR_DELAY_UI);
//        mStepCount.initListener(new StepValuePassListener() {
//            @Override
//            public void stepChanged(int steps) {
//                nowBuSu = steps;//通过接口回调获得当前步数
//                updateNotification();    //更新步数通知
//            }
//        });
    }
}
