package com.mytest.timershaft;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mytest.R;

import java.util.HashMap;
import java.util.List;

public class TimerShaftAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private List<HashMap<String,Object>> listitem;

    public TimerShaftAdapter(Context context, List<HashMap<String,Object>> listitem) {
        this.listitem = listitem;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.list_cell,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        //绑定数据到viewholder里面
        vh.title.setText((String) listitem.get(position).get("ItemTitle"));
        vh.text.setText((String) listitem.get(position).get("ItemText"));
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,text;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemtitle);
            text = itemView.findViewById(R.id.itemtext);
        }
    }
}
