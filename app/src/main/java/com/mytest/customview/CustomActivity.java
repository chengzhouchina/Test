package com.mytest.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mytest.R;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
    }
}
