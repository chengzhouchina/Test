package com.mytest.applicationArchitecture.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mytest.R;
import com.mytest.applicationArchitecture.mvp.model.IpInfoTask;
import com.mytest.applicationArchitecture.mvp.util.ActivityUtils;
import com.mytest.applicationArchitecture.mvp.view.IpInfoFragment;

public class IpInfoActivity extends AppCompatActivity {

    private IpInfoPresenter ipInfoPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipinfo);
        IpInfoFragment ipInfoFragment = (IpInfoFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (ipInfoFragment == null){
            ipInfoFragment = IpInfoFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),ipInfoFragment,R.id.contentFrame);
        }

        IpInfoTask ipInfoTask = IpInfoTask.getInstance();
        ipInfoPresenter = new IpInfoPresenter(ipInfoTask,ipInfoFragment);
        ipInfoFragment.setPresenter(ipInfoPresenter);
    }
}
