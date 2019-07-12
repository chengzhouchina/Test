package com.mytest.java.genericityAndreflect;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型函数定义及使用
 */
public class StaticFans {

    /**
     * 静态函数
     *
     * @param a
     * @param <T>
     */
    public static <T> void StaticMethod(T a) {
        Log.d("harvic", "StaticMethod: " + a.toString());
    }

    /**
     * 普通函数
     *
     * @param a
     * @param <T>
     */
    public <T> void otherMethod(T a) {
        Log.d("harvic", "OtherMethod: " + a.toString());
    }

    /**
     * Class<T>类传递
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> List<T> parseArray(Class<T> object) {
        List<T> modelList = new ArrayList<>();
        return modelList;
    }

    /**
     * 泛型数组
     *
     * @param arg 接收的T类型的可变长参数
     *            由于可变长参数在输入后，会保存在arg这个数组中
     * @param <T>
     * @return
     */
    public static <T> T[] fun1(T... arg) {
        return arg;
    }
}
