package com.mytest.materialdesign;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mytest.R;
import com.mytest.base.BaseActivity;

import butterknife.BindView;

public class FruitActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.iv_fruit)
    ImageView fruitImageView;
    @BindView(R.id.tv_fruit_content)
    TextView fruitContentText;

    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE_ID = "fruit_image_id";

    private String fruitName;
    private int fruitImageId;

    @Override
    public int initLayout() {
        return R.layout.activity_fruit;
    }

    @Override
    public void initIntent() {
        Intent intent = getIntent();
        fruitName = intent.getStringExtra(FRUIT_NAME);
        fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);

    }

    @Override
    public void addListener() {

    }

    @Override
    public void initData() {
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsing_toolbar.setTitle(fruitName);
        Glide.with(this).load(fruitImageId).into(fruitImageView);
        String fruitContent = generateFruitConten(fruitName);
        fruitContentText.setText(fruitContent);
    }

    private String generateFruitConten(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}
