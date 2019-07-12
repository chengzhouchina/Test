package com.mytest.animator;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 *   这段代码就是我们自定义的QQ抖一抖动画了，所有的自定义动画都需要继承 android.view.animation.Animation 抽
 *   象类，然后重写 initialize() 和 applyTransformation() 这两个方法，在 initialize() 方法中对一些变量进行
 *   始化，在 applyTransformation() 方法中通过矩阵修改动画数值，从而控制动画的实现过程，这也是自定义动画的核心。
 */
public class QQTrembleAni extends Animation {


    //applyTransformation(float interpolatedTime, Transformation t) 方法在动画的执行过程中会不断地调
    // 用，可以看到接收的两个参数分别是 float interpolatedTime 表示当前动画进行的时间与动画总时
    // 间（一般在 setDuration() 方法中设置）的比值，从0逐渐增大到1； Transformation t 传递当前动画对
    // 象，一般可以通过代码 android.graphics.Matrix matrix = t.getMatrix() 获得 Matrix 矩阵对象，再
    // 设置 Matrix 对象，一般要用到 interpolatedTime 参数，以此达到控制动画实现的结果。
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        //50 越大频率越高, 8 越小振幅越小
        t.getMatrix().setTranslate((float) Math.sin(interpolatedTime * 50) * 8,(float) Math.sin(interpolatedTime * 50) * 8);

        super.applyTransformation(interpolatedTime, t);
    }
}
