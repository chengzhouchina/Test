package com.mytest.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mytest.R;

import java.util.List;

public class FruitAdapter extends BaseQuickAdapter<Fruit, BaseViewHolder> {

    private Context context;

    public FruitAdapter(int layoutResId, List<Fruit> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Fruit item) {
        helper.setText(R.id.tv_fruit_name, item.getName());
        Glide.with(context).load(item.getImageId()).into((ImageView) helper.getView(R.id.iv_fruit));

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME, item.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, item.getImageId());
                context.startActivity(intent);
            }
        });
    }
}
