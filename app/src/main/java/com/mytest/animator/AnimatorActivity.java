package com.mytest.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mytest.R;
import com.mytest.base.BaseActivity;
import com.mytest.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AnimatorActivity extends BaseActivity {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.ll_animator)
    RelativeLayout ll_animator;
    @BindView(R.id.start_ani_2)
    Button start_ani_2;
    @BindView(R.id.ll_point_circle_1)
    LinearLayout mPoint_1;
    @BindView(R.id.ll_point_circle_2)
    LinearLayout mPoint_2;
    @BindView(R.id.ll_point_circle_3)
    LinearLayout mPoint_3;
    @BindView(R.id.ll_point_circle_4)
    LinearLayout mPoint_4;

    private int mCurrentRed = -1;
    private int mCurrentGreen = -1;
    private int mCurrentBlue = -1;

    //ImageView组件id数组
    private int[] mRes = new int[]{R.id.iv, R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4};
    //ImageView对象集合
    private List<ImageView> mImViews = new ArrayList<>();
    private boolean flag = true; //启动动画，关闭动画的标记位

    @Override
    public int initLayout() {
        return R.layout.activity_animator;
    }

    @Override
    public void initIntent() {
    }

    @Override
    public void addListener() {
        start_ani_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginPropertyAni();
            }
        });
    }

    /**
     * 开启红球动画
     */
    private void beginPropertyAni() {
        ObjectAnimator animator_1 = ObjectAnimator.ofFloat(
                mPoint_1,
                "rotation",
                0,
                360);
        animator_1.setDuration(2000);
        animator_1.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator animator_2 = ObjectAnimator.ofFloat(
                mPoint_2,
                "rotation",
                0,
                360);
        animator_2.setStartDelay(150);
        animator_2.setDuration(2000 + 150);
        animator_2.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator animator_3 = ObjectAnimator.ofFloat(
                mPoint_3,
                "rotation",
                0,
                360);
        animator_3.setStartDelay(2 * 150);
        animator_3.setDuration(2000 + 2 * 150);
        animator_3.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator animator_4 = ObjectAnimator.ofFloat(
                mPoint_4,
                "rotation",
                0,
                360);
        animator_4.setStartDelay(3 * 150);
        animator_4.setDuration(2000 + 3 * 150);
        animator_4.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator_1).with(animator_2).with(animator_3).with(animator_4);
        animatorSet.start();
    }

    @Override
    public void initData() {
//        ll_animator.addView(new MyAnimationView(this));
//        QQTrembleAni trembleAni = new QQTrembleAni();
//        trembleAni.setDuration(800);//持续时间越短频率越高
//        trembleAni.setRepeatCount(2);//重复次数,不包含第一次
//        ll_animator.startAnimation(trembleAni);

//        TVCloseAni tvCloseAni = new TVCloseAni();
//        iv.setAnimation(tvCloseAni);

//        Rotate3DAni rotate3DAni = new Rotate3DAni();
//        rotate3DAni.setmRotateY(40.0f);
//        iv.setAnimation(rotate3DAni);

        // for循环创建ImageView对象，并添加到集合中
        for (int i = 0; i < mRes.length; i++) {
            ImageView iv_a = (ImageView) findViewById(mRes[i]);
            iv_a.setOnClickListener(this);
            mImViews.add(iv_a);
        }
        //帧动画
//        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getBackground();
//        animationDrawable.start();

        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.animation_set);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayResult(v, "#0000ff", "#ff0000");
            }
        });
    }

    @Override
    public void onClick(View view) {}


    private void closeAnim() {
        // 创建ObjectAnimator属性对象，参数分别是动画要设置的View对象、动画属性、属性值
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(mImViews.get(0),
                "alpha",
                0.5F,
                1F);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mImViews.get(1),
                "translationY",
                200F,
                0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mImViews.get(2),
                "translationX",
                200F,
                0);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mImViews.get(3),
                "translationY",
                -200F,
                0);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(mImViews.get(4),
                "translationX",
                -200F,
                0);
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.setDuration(4000);
        aniSet.setInterpolator(new BounceInterpolator());// 弹跳效果的插值器
        aniSet.playTogether(animator0,
                animator1,
                animator2,
                animator3,
                animator4);// 同时启动5个动画
        aniSet.start();

        // 重置标记位
        flag = true;
    }

    private void startAnim() {
        // 创建ObjectAnimator属性对象，参数分别是动画要设置的View对象、动画属性、属性值
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(
                mImViews.get(0),
                "alpha",
                1f,
                0.5f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                mImViews.get(1),
                "translationY",
                200f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                mImViews.get(2),
                "translationX",
                200f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(
                mImViews.get(3),
                "translationY",
                -200f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(
                mImViews.get(4),
                "translationX",
                -200f);
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.setDuration(4000);
        aniSet.setInterpolator(new BounceInterpolator());// 弹跳效果的插值器
        aniSet.playTogether(animator0,
                animator1,
                animator2,
                animator3,
                animator4);// 同时启动5个动画
        aniSet.start();

        // 重置标记位
        flag = false;
    }

    public void newstartAnimationSet(ImageView imageView) {
        //创建动画，参数表示他的子动画是否共用一个插值器
        AnimationSet animationSet = new AnimationSet(true);
        //添加动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setFillBefore(false);
        alphaAnimation.setRepeatCount(-1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        animationSet.addAnimation(alphaAnimation);
        //设置插值器
        animationSet.setInterpolator(new LinearInterpolator());

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setFillBefore(false);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        animationSet.addAnimation(rotateAnimation);

        AnimationSet animationSet1 = new AnimationSet(true);
        animationSet1.setStartOffset(1000);
        animationSet1.setInterpolator(new BounceInterpolator());
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, 0.5f, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setFillBefore(false);
        scaleAnimation.setRepeatCount(-1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        animationSet1.addAnimation(scaleAnimation);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 4.0f, 0, 4.0f);
        translateAnimation.setDuration(2000);
        translateAnimation.setFillAfter(true);
        translateAnimation.setFillBefore(false);
        translateAnimation.setRepeatCount(-1);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        animationSet1.addAnimation(translateAnimation);

        animationSet.addAnimation(animationSet1);


        //开始动画
        imageView.startAnimation(animationSet);
    }

    @OnClick(R.id.btn_alpha)
    public void btnAlpha() {
//        //渐变动画
//        // 初始透明度和结束透明度
//        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
//        alphaAnimation.setDuration(10 * 1000);
//        alphaAnimation.setFillAfter(true);//动画结束后保留结束状态
//        iv.startAnimation(alphaAnimation);
        if (flag) {
            startAnim();
        } else {
            closeAnim();
        }
    }

    @OnClick(R.id.btn_scale)
    public void btnScale() {
        //缩放动画
        //fromX 动画开始时的X坐标的伸缩尺寸 toX 动画结束时的X坐标的伸缩尺寸
        //pivotX/pivotXValue 缩放动画的中心点X坐标 pivotY/pivotYValue 缩放动画的中心点Y坐标
        //pivotXType 动画在X轴的伸缩模式,也就是中心点相对于哪个物件,取值是Animation.ABSOLUTE.Animation.RELATIVE_TO_SELF或RELATIVE_TO_PARENT
        //pivotYType 动画在Y轴的伸缩模式,也就是中心点相对于哪个物件,取值是Animation.ABSOLUTE.Animation.RELATIVE_TO_SELF或RELATIVE_TO_PARENT
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 3.0f, 1.0f, 3.0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 0f);
        scaleAnimation.setDuration(1000 * 10);
        scaleAnimation.setFillAfter(true);//动画结束后保留结束状态
        iv.startAnimation(scaleAnimation);
    }

    @OnClick(R.id.btn_translate)
    public void btnTranslate() {
        //位移动画
        //fromXType 动画开始时在X轴的位移模式 取值时Animation.ABSOLUTE,Animation.RELATIVE_TO_SELF或Animation.RELATIVE_TO_PARENT
        //fromXValue/fromXDelta 动画开始时当前View的X坐标
        //toXType 动画结束时在X轴的位移模式 取值时Animation.ABSOLUTE,Animation.RELATIVE_TO_SELF或Animation.RELATIVE_TO_PARENT
        //toXValue/toXDelta 动画结束时当前View的X坐标
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(3000);
        translateAnimation.setFillAfter(true);
        iv.startAnimation(translateAnimation);
    }

    @OnClick(R.id.btn_rotate)
    public void btnRotate() {
        //旋转动画
        //fromDegrees 动画开始时的旋转角度 toDegrees 动画结束时的旋转角度
        //pivotXValue 动画相对于物件的X坐标开始位置 pivotYValue 动画相对于物件的Y坐标开始位置
        RotateAnimation rotateAnimation = new RotateAnimation(0, -720,
                RotateAnimation.RELATIVE_TO_SELF, 0.2f, RotateAnimation.RELATIVE_TO_SELF, 0.2f);
        rotateAnimation.setDuration(1000 * 10);
        rotateAnimation.setFillAfter(true);
        iv.startAnimation(rotateAnimation);
    }


    private void displayResult(final View target, final String start, final String end) {
        //创建ValueAnimator对象,实现颜色渐变
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 100f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取当前动画的进度值,1 - 100
                float currentValue = (float) animation.getAnimatedValue();
                LogUtil.e("mytest", "current value : " + currentValue);

                //获取动画当前时间流逝的百分比,范围在0-1之间
                float fraction = animation.getAnimatedFraction();
                //直接调用evaluateForColor()方法,通过百分比计算出对应的颜色值
                String colorResult = evaluateForColor(fraction, start, end);
                /**
                 * 通过Color.parseColor(colorResult)解析字符串颜色值，传给ColorDrawable，创建ColorDrawable对象
                 */
                ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(colorResult));
                //ColorDrawable对象设置位target的背景
                target.setBackground(colorDrawable);
                target.invalidate();
            }
        });
        valueAnimator.setDuration(1000 * 6);

        //组装缩放动画
        ValueAnimator animator_1 = ObjectAnimator.ofFloat(target, "scaleX", 1f, 0.5f);
        ValueAnimator animator_2 = ObjectAnimator.ofFloat(target, "scaleY", 1f, 0.5f);
        ValueAnimator animator_3 = ObjectAnimator.ofFloat(target, "scaleX", 0.5f, 1f);
        ValueAnimator animator_4 = ObjectAnimator.ofFloat(target, "scaleY", 0.5f, 1f);
        AnimatorSet set_1 = new AnimatorSet();
        set_1.play(animator_1).with(animator_2);
        AnimatorSet set_2 = new AnimatorSet();
        set_2.play(animator_3).with(animator_4);
        AnimatorSet set_3 = new AnimatorSet();
        set_3.play(set_1).before(set_2);
        set_3.setDuration(1000 * 3);

        //组装颜色动画和缩放动画,并启动动画
        AnimatorSet set_4 = new AnimatorSet();
        set_4.play(valueAnimator).with(set_3);
        set_4.start();
    }

    private String evaluateForColor(float fraction, String start, String end) {
        String startColor = start;
        String endColor = end;
        int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
        int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
        int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
        int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);

        //初始化颜色的值
        if (mCurrentRed == -1) {
            mCurrentRed = startRed;
        }
        if (mCurrentGreen == -1) {
            mCurrentGreen = startGreen;
        }
        if (mCurrentBlue == -1) {
            mCurrentBlue = startBlue;
        }

        //计算初始颜色和结束颜色之间的差值
        int redDiff = Math.abs(startRed - endRed);
        int greenDiff = Math.abs(startGreen - endGreen);
        int blueDiff = Math.abs(startBlue - endBlue);
        int colorDiff = redDiff + greenDiff + blueDiff;
        if (mCurrentRed != endRed) {
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0, fraction);
        } else if (mCurrentGreen != endGreen) {
            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff, redDiff, fraction);
        } else if (mCurrentBlue != endBlue) {
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff, redDiff + greenDiff, fraction);
        }
        //将计算出的当前颜色的值组装返回
        String currentColor = "#" + getHexString(mCurrentRed) + getHexString(mCurrentGreen) +
                getHexString(mCurrentBlue);
        return currentColor;
    }

    /**
     * 根据fraction值来计算当前的颜色
     *
     * @param startColor
     * @param endColor
     * @param colorDiff
     * @param offset
     * @param fraction
     * @return
     */
    private int getCurrentColor(int startColor, int endColor, int colorDiff, int offset, float fraction) {
        int currentColor;
        if (startColor > endColor) {
            currentColor = (int) (startColor - (fraction * colorDiff - offset));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (fraction * colorDiff - offset));
            if (currentColor > endColor) {
                currentColor = endColor;
            }
        }
        return currentColor;
    }

    private String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }
}
