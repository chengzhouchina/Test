package com.mytest.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorUtil {

    //传感器
    private static SensorManager sensorManager;
    private static SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            //可以得到传感器实时测量出来的变化值
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            Log.e("Sensor x-- ",x+"");
            Log.e("Sensor y-- ",y+"");
            Log.e("Sensor z-- ",z+"");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.e("Sensor", "sensor: " + sensor + ", accuracy" + accuracy);
        }
    };

    public static void createSensor(Context context,int type){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(type);
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_UI);
    }

    public static void unregisterSensor(){
        sensorManager.unregisterListener(listener);
    }
}
