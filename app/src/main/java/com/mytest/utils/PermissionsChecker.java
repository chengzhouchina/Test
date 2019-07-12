package com.mytest.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.mytest.BaseApplication;

/**
 * 检查权限的工具类
 * <p/>
 * Created by yzz on 16/1/26.
 * see http://www.jianshu.com/p/dbe4d37731e6/
 */
public class PermissionsChecker {

    // 判断权限集合  返回true代表缺少权限
    public static boolean lacksPermissions(Context mContext,String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(mContext,permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private static boolean lacksPermission(Context mContext,String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}


