package com.mytest.applicationArchitecture.mvp.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mytest.R;
import com.mytest.applicationArchitecture.mvp.model.IpData;
import com.mytest.applicationArchitecture.mvp.model.IpInfo;
import com.mytest.applicationArchitecture.mvp.presenter.IpInfoContract;

public class IpInfoFragment extends Fragment implements IpInfoContract.View {

    private TextView tv_country;
    private TextView tv_area;
    private TextView tv_city;
    private Button bt_ipinfo;
    private Dialog mDialog;
    private IpInfoContract.Presenter mPresenter;

    public static IpInfoFragment newInstance(){
        return new IpInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ipinfo,container,false);
        tv_city = root.findViewById(R.id.tv_city);
        tv_country = root.findViewById(R.id.tv_country);
        bt_ipinfo = root.findViewById(R.id.btn_ipinfo);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setTitle("获取数据中");
        bt_ipinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getIpInfo("39.155.184.147");
            }
        });
    }

    @Override
    public void setPresenter(IpInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setIpInfo(IpInfo ipInfo) {
        if (ipInfo != null && ipInfo.getData() != null){
            IpData ipData = ipInfo.getData();
            tv_country.setText(ipData.getCountry());
            tv_city.setText(ipData.getCity());
        }
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity().getApplicationContext(),"网络出错",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
