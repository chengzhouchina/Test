package com.mytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class BatteryChangeReceiver extends BroadcastReceiver {

    SharedPreferences.Editor editor;
    int batteryCurrent;


    public int getBatteryCurrent() {
        return batteryCurrent;
    }

    public BatteryChangeReceiver() {
    }

    public BatteryChangeReceiver(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //获取电池的当前电量和总电量
        batteryCurrent = intent.getExtras().getInt("level");// 获得当前电量
        int batteryTotal = intent.getExtras().getInt("scale");// 获得总电量
        editor.putString("batteryCurrent", String.valueOf(batteryCurrent));
        editor.putString("batteryTotal", String.valueOf(batteryTotal));
        editor.commit();
        Log.e("Battery --",batteryCurrent+"");
        Log.e("Battery --",batteryTotal+"");
    }
}
