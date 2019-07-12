package com.mytest.smartrefresh;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mytest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018\11\5 0005.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;
    private int TYPE_CONTENT = 1003;

    /**
     * 头布局和尾布局的数量
     */
    private int mHeader=1;
    private int mFoot=1;


    public MyRecyclerAdapter(Context mContext, List<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View viewHead = inflater.inflate(R.layout.layout_title, parent, false);
            return new MyViewHolder(viewHead);
        }
        if (viewType == TYPE_FOOTER) {
            View viewFoot = inflater.inflate(R.layout.layout_title, parent, false);
            return new MyViewHolder(viewFoot);
        }

        if (viewType == TYPE_CONTENT){
            View view = inflater.inflate(R.layout.item, parent, false);
            return new MyRecyclerAdapter.MyViewHolder(view);
        }
        View view = inflater.inflate(R.layout.item, parent, false);
//        MyRecyclerAdapter.MyViewHolder holder = new MyRecyclerAdapter.MyViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.e("mytest", "getItemViewType: -- position" + position );
        if (position != 0 && position != mDatas.size() + 1) {
            holder.tv.setText(mDatas.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 2;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_item);
        }

    }

    //下面两个方法提供给页面刷新和加载时调用
    public void refresh(List<String> addList) {
        //增加数据
        int position = mDatas.size();
        mDatas.addAll(position, addList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_HEADER;
        }
        if (position == mDatas.size() + 1){
            return TYPE_FOOTER;
        }
        return TYPE_CONTENT;
    }
}
