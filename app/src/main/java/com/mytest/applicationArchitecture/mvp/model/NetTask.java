package com.mytest.applicationArchitecture.mvp.model;

/**
 * 获取网络数据的接口类
 * @param <T>
 */
public interface NetTask<T> {
    void execute(T data,LoadTasksCallBack callBack);
}
