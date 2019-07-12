package com.mytest.animator;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class TVCloseAni extends Animation {

    private int mCenterWidth,mCenterHeight;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //设置默认时长
        setDuration(4000);
        //保持动画的结束状态
        setFillAfter(true);
        //设置默认插值器
//        setInterpolator(new LinearInterpolator());
        mCenterHeight = height/2;
        mCenterWidth = width/2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();
        matrix.preScale(1,1-interpolatedTime,mCenterWidth,mCenterHeight);
    }
}
