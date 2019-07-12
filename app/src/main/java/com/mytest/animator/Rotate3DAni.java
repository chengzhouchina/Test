package com.mytest.animator;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

public class Rotate3DAni extends Animation {

    private int mCenterWidthm,mCenterHeight;
    private Camera mCamera = new Camera();
    private float mRotateY = 0.0f;

    // 一般在此方法初始化一些动画相关的变量和值
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        //设置默认时长
        setDuration(4000);
        //保持动画的结束状态
        setFillAfter(true);
        //设置默认插值器
        setInterpolator(new BounceInterpolator());//回弹效果的插值器
        mCenterHeight = height/2;
        mCenterWidthm = width/2;
    }

    public void setmRotateY(float mRotateY) {
        this.mRotateY = mRotateY;
    }

    // 自定义动画的核心，在动画的执行过程中会不断回调此方法，并且每次回调interpolatedTime值都在不断变化(0----1)
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();
        mCamera.save();
        //使用Camera设置Y轴方向的旋转角度
        mCamera.rotateY(mRotateY * interpolatedTime);
        //将旋转变化作用到matrix上
        mCamera.getMatrix(matrix);
        mCamera.restore();
        //通过pre方法设置矩阵作用前的偏移量来改变旋转中心
        matrix.preTranslate(mCenterWidthm,mCenterHeight);//旋转之前开始位移动画
        matrix.postTranslate(-mCenterWidthm,-mCenterHeight);//旋转之后开始位移动画
    }
}
