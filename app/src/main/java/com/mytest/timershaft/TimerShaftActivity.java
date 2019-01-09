package com.mytest.timershaft;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mytest.R;
import com.mytest.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class TimerShaftActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private List<HashMap<String,Object>> listItem;
    private List<String> data = new ArrayList<>();
    private TimerShaftAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,data));

        adapter = new TimerShaftAdapter(this,listItem);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_timer_shaft;
    }

    @Override
    public void initIntent() {}

    @Override
    public void addListener() {}

    @Override
    public void initData() {
        listItem = new ArrayList<>();
        HashMap<String,Object> map1 = new HashMap<>();
        HashMap<String,Object> map2 = new HashMap<>();
        HashMap<String,Object> map3 = new HashMap<>();
        HashMap<String,Object> map4 = new HashMap<>();
        HashMap<String,Object> map5 = new HashMap<>();
        HashMap<String,Object> map6 = new HashMap<>();
        map1.put("ItemTitle", "美国谷歌公司已发出");
        map1.put("ItemText", "发件人:谷歌 CEO Sundar Pichai");
        listItem.add(map1);

        map2.put("ItemTitle", "国际顺丰已收入");
        map2.put("ItemText", "等待中转");
        listItem.add(map2);

        map3.put("ItemTitle", "国际顺丰转件中");
        map3.put("ItemText", "下一站中国");
        listItem.add(map3);

        map4.put("ItemTitle", "中国顺丰已收入");
        map4.put("ItemText", "下一站广州华南理工大学");
        listItem.add(map4);

        map5.put("ItemTitle", "中国顺丰派件中");
        map5.put("ItemText", "等待派件");
        listItem.add(map5);

        map6.put("ItemTitle", "华南理工大学已签收");
        map6.put("ItemText", "收件人:Carson");
        listItem.add(map6);

        for(int i = 0 ; i < 6 ;i ++){
            data.add("test-- " + i);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
