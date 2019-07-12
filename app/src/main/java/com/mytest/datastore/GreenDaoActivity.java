package com.mytest.datastore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.mytest.R;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_add;
    private Button btn_edit;
    private Button btn_delete;
    private Button btn_query;

    private ListView listView;

    private List<Shop> shops;
    private ShopListAdapter adapter;

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);

        initView();
        initData();
    }

    private void initData() {
        shops = new ArrayList<>();
        shops = com.mytest.datastore.ShopDao.queryShop();
        adapter = new ShopListAdapter(this, shops);
        listView.setAdapter(adapter);
    }



    private void initView() {

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_query = (Button) findViewById(R.id.btn_query);
        listView = (ListView) findViewById(R.id.listView);

        btn_add.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_query.setOnClickListener(this);

    }

    private void addDate() {
        Shop shop = new Shop();
        shop.setType(Shop.TYPE_CART);
        shop.setAddress("广东深圳");
        shop.setImage_url("https://img.alicdn.com/bao/uploaded/i2/TB1N4V2PXXXXXa.XFXXXXXXXXXX_!!0-item_pic.jpg_640x640q50.jpg");
        shop.setPrice("19.40");
        shop.setSell_num(15263);
        shop.setName("正宗梅菜扣肉 聪厨梅干菜扣肉 家宴常备方便菜虎皮红烧肉 2盒包邮" + i++);
        com.mytest.datastore.ShopDao.insertShop(shop);
        initData();
    }

    private void updateDate() {
        if (!shops.isEmpty()) {
            Shop shop = shops.get(0);
            shop.setName("我是修改的名字");
            com.mytest.datastore.ShopDao.updateShop(shop);
            initData();
        }
    }

    private void deleteDate() {
        if (!shops.isEmpty()) {
            com.mytest.datastore.ShopDao.deleteShop(shops.get(0).getId());
            initData();
        }
    }

    private void query() {
        if (!shops.isEmpty()) {
            com.mytest.datastore.ShopDao.queryAll();
            initData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                addDate();
                break;
            case R.id.btn_edit:
                updateDate();
                break;
            case R.id.btn_delete:
                deleteDate();
                break;
            case R.id.listView:
                initData();
                break;
            case R.id.btn_query:
                query();
                break;
        }
    }
}
