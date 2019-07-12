package com.mytest.animator;

import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.mytest.R;

public class MyAnimationView extends View {

    public MyAnimationView(Context context) {
        super(context);
        ObjectAnimator colorAnim = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.color_anim);
        colorAnim.setEvaluator(new ArgbEvaluator());
        //对该View本身应用属性动画
        colorAnim.setTarget(this);
        colorAnim.start();
    }

    public MyAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
