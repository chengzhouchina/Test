package com.mytest.datastore;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mytest.R;

import java.util.List;

public class ShopListAdapter extends BaseAdapter {

    private Context context;
    private List<Shop> datas;
    private LayoutInflater mInflater;


    public ShopListAdapter(Context context, List<Shop> datas) {
        this.context = context;
        this.datas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_adapter_shop_list,null);
        }
        ViewHolder holder = getViewHolder(convertView);
        Shop shop = datas.get(position);
        holder.iv_shop.setImageResource(R.mipmap.ic_launcher);
        holder.tv_name.setText(shop.getName());
        holder.tv_price.setText(shop.getPrice() + "");
        holder.tv_price_discount.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_sell_num.setText("已售" + shop.getSell_num() + "件");
        return convertView;
    }

    /**
     * 获得控件管理对象
     * @param view
     * @return
     */
    private ViewHolder getViewHolder(View view){
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder == null){
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    /**
     * 控件管理类
     */
    private class ViewHolder{
        private TextView tv_name, tv_price, tv_price_discount, tv_sell_num;
        private ImageView iv_shop;

        ViewHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_price_discount = (TextView) view.findViewById(R.id.tv_price_discount);
            tv_sell_num = (TextView) view.findViewById(R.id.tv_sell_num);
            iv_shop = (ImageView) view.findViewById(R.id.iv_shop);
        }
    }
}
