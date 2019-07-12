package com.mytest.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.mytest.R;
import com.mytest.utils.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomActivity extends AppCompatActivity {
    @BindView(R.id.check_view)
    public CheckView checkView;
    @BindView(R.id.mytext)
    public MyTextView mytext;
    @BindView(R.id.rll_mytext)
    public MyTextView rll_mytext;

    public static final String TAG = "CustomActivity";
    public static final String NEWTAG = "mycustomtest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        PieView view = new PieView(this);
//        setContentView(new CheckView(this));
        ButterKnife.bind(this);
        ArrayList<PieData> datas = new ArrayList<>();
        PieData pieData = new PieData("sloop", 60);
        PieData pieData2 = new PieData("sloop", 30);
        PieData pieData3 = new PieData("sloop", 40);
        PieData pieData4 = new PieData("sloop", 20);
        PieData pieData5 = new PieData("sloop", 20);
        datas.add(pieData);
        datas.add(pieData2);
        datas.add(pieData3);
        datas.add(pieData4);
        datas.add(pieData5);
        view.setData(datas);

        mytext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtil.d(NEWTAG, "MyTextView onTouch ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtil.d(NEWTAG, "MyTextView onTouch ACTION_UP");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        LogUtil.d(NEWTAG, "MyTextView onTouch ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        LogUtil.d(NEWTAG, "MyTextView onTouch ACTION_CANCEL");
                        break;
                }
                return false;
            }
        });

        rll_mytext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtil.d(NEWTAG, "MyTextView onTouch ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtil.d(NEWTAG, "MyTextView onTouch ACTION_UP");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        LogUtil.d(NEWTAG, "MyTextView onTouch ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        LogUtil.d(NEWTAG, "MyTextView onTouch ACTION_CANCEL");
                        break;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btn_check)
    public void check() {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        checkView.check();
    }

    @OnClick(R.id.btn_uncheck)
    public void uncheck() {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        checkView.unCheck();
    }


    @OnClick({R.id.mytext, R.id.rll_mytext})
    public void onClickMyText() {
        LogUtil.d(NEWTAG, TAG + "MyTextView OnClick");
    }

    /**
     * 返回true表示事件被当前视图消费掉，不在继续分发事件
     * 返回值为super.dispatchTouchEvent表示继续分发该事件
     * 如果当前视图是ViewGroup及其子类，则会调用onInterceptTouchEvent方法判定是否拦截该事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d(NEWTAG, TAG + "dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d(NEWTAG, TAG + "dispatchTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.d(NEWTAG, TAG + "dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.d(NEWTAG, TAG + "dispatchTouchEvent ACTION_CANCEL");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 返回true或super.onTouchEvent表示当前视图可以处理对应的事件，事件将不会向上传递给父视图
     * 返回值为false表示当前视图不处理这个事件，事件会被传递给父视图的onTouchEvent方法进行处理
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d(NEWTAG, TAG + "onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d(NEWTAG, TAG + "onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.d(NEWTAG, TAG + "onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.d(NEWTAG, TAG + "onTouchEvent ACTION_CANCEL");
                break;
        }
        return super.onTouchEvent(event);
    }


}
