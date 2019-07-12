package com.mytest.customview.qqredpoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class QQ_RedPoint extends View {

    private Paint mPaint;//画笔
    private int mRadius;
    private PointF mCenterPoint;
    private boolean mTouch;

    public QQ_RedPoint(Context context) {
        this(context, null);
    }

    public QQ_RedPoint(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public QQ_RedPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.FILL);
        mRadius = 20;
        mCenterPoint = new PointF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterPoint.x = w/2;
        mCenterPoint.y = h/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCenterPoint.x,mCenterPoint.y,mRadius,mPaint);
    }

    //实现手指触摸屏幕跟随手指移动的小圆效果
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mTouch = true;
                break;
            case MotionEvent.ACTION_UP:
                mTouch = false;
                break;
        }

        postInvalidate();
        return true;//事件不往父控件传递,若为 false 捕获不到 ACTION_DOWN 以后的手指状态
    }
}
