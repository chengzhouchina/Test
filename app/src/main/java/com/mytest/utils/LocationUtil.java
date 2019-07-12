package com.mytest.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import com.mytest.BaseApplication;
import com.mytest.utils.Toaster;

import java.util.List;
import java.util.Locale;

public class LocationUtil {

    private static LocationManager lm;
    private static String provider;
    private static int targetversion;
    private static String address = "";

    public static String getAddress() {
        return address;
    }

    public static void initLocation(Activity context) {
        int read_contacts;
        int write_external_storage;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            targetversion = info.applicationInfo.targetSdkVersion;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (targetversion >= Build.VERSION_CODES.M) {
                read_contacts = ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
                write_external_storage = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                read_contacts = PermissionChecker.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
                write_external_storage = PermissionChecker.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (read_contacts == PackageManager.PERMISSION_GRANTED && write_external_storage == PackageManager.PERMISSION_GRANTED) {
                //拥有权限，执行操作
                lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                // 获取所有可用的位置提供器
                List<String> providerList = lm.getProviders(true);
                if (providerList.contains(LocationManager.GPS_PROVIDER)) {
                    provider = LocationManager.GPS_PROVIDER;
                } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                    provider = LocationManager.NETWORK_PROVIDER;
                } else {
                    // 当没有可用的位置提供器时，弹出Toast提示用户
                    Toaster.show(BaseApplication.getContext(), "请打开定位设置");
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                    context.finish();
                    return;
                }

                Location location = lm.getLastKnownLocation(provider);
                if (location != null) {
                    // 显示当前设备的位置信息
                    getLocation(location);
                }
                lm.requestLocationUpdates(provider, 5000, 1, locationListener);
            } else {
                //没有权限，向用户请求权限
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            // 获取所有可用的位置提供器
            List<String> providerList = lm.getProviders(true);
            if (providerList.contains(LocationManager.GPS_PROVIDER)) {
                provider = LocationManager.GPS_PROVIDER;
            } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                provider = LocationManager.NETWORK_PROVIDER;
            } else {
                // 当没有可用的位置提供器时，弹出Toast提示用户
                Toaster.show(BaseApplication.getContext(), "请打开定位设置");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
                context.finish();
                return;
            }
            Location location = lm.getLastKnownLocation(provider);
            if (location != null) {
                // 显示当前设备的位置信息
                getLocation(location);
            }
            lm.requestLocationUpdates(provider, 5000, 1, locationListener);
        }
    }

    private static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 更新当前设备的位置信息
            getLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private static void getLocation(Location lm) {

        if (lm != null) {
            getAddress(BaseApplication.getContext(), lm.getLatitude(), lm.getLongitude());
        }
    }

    private static void getAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            //"经度："+String.valueOf(address.get(0).getLongitude()*1E6)+"\n";
            //"纬度："+String.valueOf(address.get(0).getLatitude()*1E6)+"\n";
            //"国家："+address.get(0).getCountryName()+"\n";
            //"省："+address.get(0).getAdminArea()+"\n";
            //"城市："+address.get(0).getLocality()+"\n";
            // "名称："+address.get(0).getAddressLine(1)+"\n";
            //"街道："+address.get(0).getAddressLine(0);
            //zip code:     getPostalCode
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0);
                Log.e("location -- ", addresses.get(0).getAddressLine(0));

            }
        } catch (Exception e) {
//            e.printStackTrace();
//            return "unKnown";
        }
    }
}
