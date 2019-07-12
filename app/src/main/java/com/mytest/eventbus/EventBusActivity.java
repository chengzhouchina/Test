package com.mytest.eventbus;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.mytest.R;
import com.mytest.base.BaseActivity;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class EventBusActivity extends BaseActivity {

    @BindView(R.id.textview)
    TextView textView;

    @Override
    public int initLayout() {
        return R.layout.activity_event_bus;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void addListener() {
        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventBusActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btn_register)
    public void eventbusRegister(){
        //注册该页面为订阅者
        EventBus.getDefault().register(this);
    }

    /**
     * 黏性事件
     * 先发送事件再订阅也能收到事件
     * @param s
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onUpdateUIEventSticky(String s) {
        Logger.d(EventBus.getDefault().isRegistered(this));
        Logger.d(s);
        textView.setText(s);
    }

    /**
     * EventBus的4种ThreadMode（线程模型）如下。
     *     • POSTING（默认）：如果使用事件处理函数指定了线程模型为POSTING，那么该事件是在哪个线程
     *       发布出来的，事件处理函数就会在哪个线程中运行，也就是说发布事件和接收事件在同一个线程中。在线
     *       程模型为POSTING的事件处理函数中尽量避免执行耗时操作，因为它会阻塞事件的传递，甚至有可能会引
     *       起ANR。
     *     • MAIN：事件的处理会在UI线程中执行。事件处理的时间不能太长，长了会导致ANR。
     *     • BACKGROUND：如果事件是在UI线程中发布出来的，那么该事件处理函数就会在新的线程中运行；
     *       如果事件本来就是在子线程中发布出来的，那么该事件处理函数直接在发布事件的线程中执行。在此事件
     *       处理函数中禁止进行UI更新操作。
     *     • ASYNC：无论事件在哪个线程中发布，该事件处理函数都会在新建的子线程中执行；同样，此事件
     *       处理函数中禁止进行UI更新操作。
     * @param s
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateUIEvent(String s) {
        Logger.d(EventBus.getDefault().isRegistered(this));
        Logger.d(s);
        textView.setText(s);
    }

    @Override
    public void initData() {
        Logger.d(EventBus.getDefault().isRegistered(this));
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
