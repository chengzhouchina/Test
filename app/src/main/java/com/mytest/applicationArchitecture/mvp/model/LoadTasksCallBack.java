package com.mytest.applicationArchitecture.mvp.model;

/**
 * 定义网络访问回调的各种状态
 * @param <T>
 */
public interface LoadTasksCallBack<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}
