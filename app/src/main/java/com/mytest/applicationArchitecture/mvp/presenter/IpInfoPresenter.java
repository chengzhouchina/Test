package com.mytest.applicationArchitecture.mvp.presenter;

import com.mytest.applicationArchitecture.mvp.model.IpInfo;
import com.mytest.applicationArchitecture.mvp.model.LoadTasksCallBack;
import com.mytest.applicationArchitecture.mvp.model.NetTask;

public class IpInfoPresenter implements IpInfoContract.Presenter,LoadTasksCallBack<IpInfo> {

    private NetTask netTask;
    private IpInfoContract.View addTastView;

    public IpInfoPresenter(NetTask netTask, IpInfoContract.View addTastView) {
        this.netTask = netTask;
        this.addTastView = addTastView;
    }

    @Override
    public void getIpInfo(String ip) {
        netTask.execute(ip,this);
    }

    @Override
    public void onSuccess(IpInfo ipInfo) {
        if (addTastView.isActive()){
            addTastView.setIpInfo(ipInfo);
        }
    }

    @Override
    public void onStart() {
        if (addTastView.isActive()){
            addTastView.showLoading();
        }
    }

    @Override
    public void onFailed() {
        if (addTastView.isActive()){
            addTastView.showError();
            addTastView.hideLoading();
        }
    }

    @Override
    public void onFinish() {
        if (addTastView.isActive()){
            addTastView.hideLoading();
        }
    }
}
