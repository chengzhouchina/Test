package com.mytest.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mytest.R;

import org.greenrobot.eventbus.EventBus;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SecondActivity.this, "滋醒你~~~", Toast.LENGTH_SHORT).show();
                // 发送普通事件
//                EventBus.getDefault().post("you are stubid");
                // 发送黏性事件
                EventBus.getDefault().postSticky("you are stubid");
            }
        });
    }
}
