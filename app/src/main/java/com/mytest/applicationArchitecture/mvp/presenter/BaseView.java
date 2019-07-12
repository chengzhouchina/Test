package com.mytest.applicationArchitecture.mvp.presenter;

/**
 * 给View绑定Presenter
 * @param <T>
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
}
