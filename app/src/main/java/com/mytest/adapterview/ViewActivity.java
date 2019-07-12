package com.mytest.adapterview;

import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mytest.R;
import com.mytest.base.BaseActivity;

import butterknife.BindView;

public class ViewActivity extends BaseActivity {

    @BindView(R.id.exlv)
    ExpandableListView exlv;
    @BindView(R.id.spinner)
    Spinner spinner;

    @Override
    public int initLayout() {
        return R.layout.activity_view;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void addListener() {

    }

    @Override
    public void initData() {
        ExpandableListAdapter adapter = new ExpandableListAdapter() {
            int[] logos = new int[]{
                    R.drawable.app_icon,
                    R.drawable.app_icon,
                    R.drawable.app_icon,
            };
            private String[] armTypes = new String[]{
                    "神族兵种", "虫族兵种", "人族兵种"
            };
            private String[][] arms = new String[][]{
                    {"狂战士", "龙骑士"},
                    {"小狗", "刺蛇"},
                    {"机枪兵", "护士MM"}
            };

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getGroupCount() {
                return armTypes.length;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return arms[groupPosition].length;
            }

            //指定组位置处的组数据
            @Override
            public Object getGroup(int groupPosition) {
                return armTypes[groupPosition];
            }

            //获取指定组位置、指定子列表项处的子列表项数据
            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return arms[groupPosition][childPosition];
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            //该方法决定每个组选项的外观
            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                LinearLayout ll = new LinearLayout(ViewActivity.this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ImageView logo = new ImageView(ViewActivity.this);
                logo.setImageResource(logos[groupPosition]);
                ll.addView(logo);
                TextView textView = getTextView();
                textView.setText(getGroup(groupPosition).toString());
                ll.addView(textView);
                return ll;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                TextView textView = getTextView();
                textView.setText(getChild(groupPosition, childPosition).toString());
                return textView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }


            @Override
            public void onGroupExpanded(int groupPosition) {

            }

            @Override
            public void onGroupCollapsed(int groupPosition) {

            }

            @Override
            public long getCombinedChildId(long groupId, long childId) {
                return 0;
            }

            @Override
            public long getCombinedGroupId(long groupId) {
                return 0;
            }
        };
        exlv.setAdapter(adapter);

        String[] arr = {"孙悟空", "猪八戒", "沙僧"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewActivity.this
                , android.R.layout.simple_list_item_multiple_choice, arr);
        spinner.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View view) {

    }

    private TextView getTextView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 64);
        TextView textView = new TextView(ViewActivity.this);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(36, 0, 0, 0);
        textView.setTextSize(20);
        return textView;
    }
}
