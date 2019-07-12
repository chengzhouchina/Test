package com.mytest.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.mytest.utils.LogUtil;

import static com.mytest.customview.CustomActivity.NEWTAG;

public class MyTextView extends android.support.v7.widget.AppCompatTextView {

    public static final String TAG = "MyTextView";

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                LogUtil.d(NEWTAG,TAG + "dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d(NEWTAG,TAG + "dispatchTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.d(NEWTAG,TAG + "dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.d(NEWTAG,TAG + "dispatchTouchEvent ACTION_CANCEL");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 返回true或super.onTouchEvent(event)表示当前视图可以处理对应的事件，事件将不会向上传递给父视图
     * 返回值为false表示当前视图不处理这个事件，事件会被传递给父视图的onTouchEvent方法进行处理
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d(NEWTAG,TAG + "onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d(NEWTAG,TAG + "onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.d(NEWTAG,TAG + "onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                LogUtil.d(NEWTAG,TAG + "onTouchEvent ACTION_CANCEL");
                break;
        }
        return false;
    }
}
