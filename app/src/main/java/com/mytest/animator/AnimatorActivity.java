package com.mytest.animator;

import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mytest.R;

public class AnimatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        LinearLayout ll_animator = findViewById(R.id.ll_animator);
        ll_animator.addView(new MyAnimationView(this));
    }

    public class MyAnimationView extends View {

        public MyAnimationView(Context context) {
            super(context);
            ObjectAnimator colorAnim = (ObjectAnimator) AnimatorInflater.loadAnimator(AnimatorActivity.this,R.animator.color_anim);
            colorAnim.setEvaluator(new ArgbEvaluator());
            //对该View本身应用属性动画
            colorAnim.setTarget(this);
            colorAnim.start();
        }
    }
}
