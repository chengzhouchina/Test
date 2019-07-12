package com.mytest.bottomNavigation;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;

import com.mytest.BaseApplication;
import com.mytest.R;
import com.mytest.base.BaseActivity;
import com.mytest.utils.Toaster;

import butterknife.BindView;

public class BottomNavigationActivity extends BaseActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;

    @Override
    public int initLayout() {
        return R.layout.activity_bottom_navigation;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void addListener() {

    }

    @Override
    public void initData() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_call:
                        Toaster.show(BaseApplication.getContext(),"you click nav_call");
                        return true;
                    case R.id.nav_friends:
                        Toaster.show(BaseApplication.getContext(),"you click nav_friends");
                        return true;
                    case R.id.nav_location:
                        Toaster.show(BaseApplication.getContext(),"you click nav_location");
                        return true;
                    case R.id.nav_mail:
                        Toaster.show(BaseApplication.getContext(),"you click nav_mail");
                        return true;
                    case R.id.nav_task:
                        Toaster.show(BaseApplication.getContext(),"you click nav_task");
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
