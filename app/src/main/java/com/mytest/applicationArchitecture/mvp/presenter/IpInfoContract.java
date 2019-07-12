package com.mytest.applicationArchitecture.mvp.presenter;

import com.mytest.applicationArchitecture.mvp.model.IpInfo;

/**
 * 用来存放相同业务的Presenter和View的接口
 */
public interface IpInfoContract {
    interface Presenter{
        void getIpInfo(String ip);
    }

    interface View extends BaseView<Presenter>{
        void setIpInfo(IpInfo ipInfo);
        void showLoading();
        void hideLoading();
        void showError();
        boolean isActive();
    }
}
