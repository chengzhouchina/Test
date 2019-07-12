package com.mytest.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class XBallView extends View implements ValueAnimator.AnimatorUpdateListener {

    public final List<XShapeHolder> balls = new ArrayList<>();//创建balls集合来存储XShapeHolder对象

    public XBallView(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //屏蔽ACTION_UP事件
        if(event.getAction() != MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_MOVE){
            return false;
        }
        //在ACTION_DOWN事件发生点生成小球
        XShapeHolder newBall = addBall(event.getX(),event.getY());
        //计算小球下落动画开始时Y坐标
        float startY = newBall.getY();
        //计算小球下落动画结束时的Y坐标,即屏幕高度减去startY
        float endY = getHeight() - XBallsFallActivity.BALL_SIZE;
        //获取屏幕高度
        float h = getHeight();
        float eventY = event.getY();
        //计算动画持续时间
        int duration = (int) (XBallsFallActivity.FULL_TIME * ((h - eventY) / h ));

        /**
         * 下面开始定义小球的下落,着地压扁,反弹等属性动画
         */
        //定义小球下落动画
        ValueAnimator fallAni = ObjectAnimator.ofFloat(newBall,"y",startY,endY);
        //设置动画持续时间
        fallAni.setDuration(duration);
        //设置加速插值器
        fallAni.setInterpolator(new AccelerateInterpolator());
        //添加addUpdateListener监听器,当ValueAnimater属性值改变时会激发事件监听方法
        fallAni.addUpdateListener(this);
        //定义小球压扁动画,控制小球x坐标左移半个球宽度
        ValueAnimator squashshAni1 = ObjectAnimator.ofFloat(newBall,"x",newBall.getX(),
                newBall.getX() - XBallsFallActivity.BALL_SIZE / 2);
        squashshAni1.setDuration(duration / 4);
        squashshAni1.setRepeatCount(1);
        squashshAni1.setRepeatMode(ValueAnimator.REVERSE);
        squashshAni1.setInterpolator(new DecelerateInterpolator());
        //添加addUpdateListener监听器,当ValueAnimator属性值改变时会激发事件监听方法
        squashshAni1.addUpdateListener(this);

        //定义小球压扁动画,控制小球宽度加倍
        ValueAnimator squashshAni2 = ObjectAnimator.ofFloat(newBall,"width",newBall.getWidth(),
                newBall.getWidth() + XBallsFallActivity.BALL_SIZE);
        squashshAni2.setDuration(duration / 4);
        squashshAni2.setRepeatCount(1);
        squashshAni2.setRepeatMode(ValueAnimator.REVERSE);
        squashshAni2.setInterpolator(new DecelerateInterpolator());
        //添加addUpdateListener监听器,当ValueAnimator属性值改变时会激发事件监听方法
        squashshAni2.addUpdateListener(this);

        //定义小球拉伸动画,控制小球y坐标下移半个球宽度
        ValueAnimator stretchAni1 = ObjectAnimator.ofFloat(newBall,"y",endY,
                endY + XBallsFallActivity.BALL_SIZE / 2);
        stretchAni1.setDuration(duration / 4);
        stretchAni1.setRepeatCount(1);
        stretchAni1.setRepeatMode(ValueAnimator.REVERSE);
        stretchAni1.setInterpolator(new DecelerateInterpolator());
        //添加addUpdateListener监听器,当ValueAnimator属性值改变时会激发事件监听方法
        stretchAni1.addUpdateListener(this);

        //定义小球拉伸动画,控制小球的高度减半
        ValueAnimator stretchAni2 = ObjectAnimator.ofFloat(newBall,"height",newBall.getHeight(),
                newBall.getHeight() - XBallsFallActivity.BALL_SIZE / 2);
        stretchAni2.setDuration(duration / 4);
        stretchAni2.setRepeatCount(1);
        stretchAni2.setRepeatMode(ValueAnimator.REVERSE);
        stretchAni2.setInterpolator(new DecelerateInterpolator());
        //添加addUpdateListener监听器,当ValueAnimator属性值改变时会激发事件监听方法
        stretchAni2.addUpdateListener(this);

        //定义小球弹起动画
        ValueAnimator bounceAni = ObjectAnimator.ofFloat(newBall,"y",endY,startY);
        bounceAni.setDuration(duration);
        bounceAni.setInterpolator(new DecelerateInterpolator());
        //添加addUpdateListener监听器,当ValueAnimator属性值改变时会激发事件监听方法
        bounceAni.addUpdateListener(this);

        //定义AnimatorSet,按顺序播放(下落，压扁&拉伸，弹起)动画
        AnimatorSet set = new AnimatorSet();
        //在squashshAni1之前播放fallAni
        set.play(fallAni).before(squashshAni1);
        /**
         * 由于小球弹起时压扁，即宽度加倍，x坐标左移，高度减半，y坐标下移
         * 因此播放squashshAni1的同时还要播放squashshAni2，stretchAni1，stretchAni2
         */
        set.play(squashshAni1).with(squashshAni2);
        set.play(squashshAni1).with(stretchAni1);
        set.play(squashshAni1).with(stretchAni2);
        //在stretchAni2之后播放bounceAni
        set.play(bounceAni).after(stretchAni2);

        //newBall对象的渐隐动画,设置alpha 1--->0
        ObjectAnimator fadeAni = ObjectAnimator.ofFloat(newBall,"alpha",1f,0f);
        //设置动画持续时间
        fadeAni.setDuration(250);
        //添加addUpdateListener监听器,当ValueAnimator属性值改变时会激发事件监听方法
        fadeAni.addUpdateListener(this);
        //为fadeAni设置监听
        fadeAni.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束时将该动画关联的ShapeHolder删除
                balls.remove(((ObjectAnimator)(animation)).getTarget());
            }
        });

        //再次定义一个AnimatorSet动画集合,来组合动画
        AnimatorSet aniSet = new AnimatorSet();
        //指定在fadeAni之前播放set动画集合
        aniSet.play(set).before(fadeAni);
        //开始播放动画
        aniSet.start();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(XShapeHolder xShapeHolder : balls){
            canvas.save();
            canvas.translate(xShapeHolder.getX(),xShapeHolder.getY());
            xShapeHolder.getShape().draw(canvas);
            canvas.restore();
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        //指定重绘界面
        this.invalidate();
    }

    /**
     * addBall()方法返回XShapeHolder对象,ShapeHolder对象持有小球
     * @return
     */
    private XShapeHolder addBall(float x, float y){
        //创建一个椭圆
        OvalShape circle = new OvalShape();
        //设置椭圆宽高
        circle.resize(XBallsFallActivity.BALL_SIZE,XBallsFallActivity.BALL_SIZE);
        //把椭圆包装成Drawable对象
        ShapeDrawable drawable = new ShapeDrawable(circle);
        //创建XShapeHolder对象
        XShapeHolder holder = new XShapeHolder(drawable);
        //设置holder坐标
        holder.setX(x - XBallsFallActivity.BALL_SIZE / 2);
        holder.setY(y - XBallsFallActivity.BALL_SIZE / 2);

        //生成随机组合的ARGB颜色
        int red = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        //把red,green,blue三个颜色随机组合成ARGB颜色
        int color = 0xff000000 + red << 16 | green << 8 | blue;
        //把red,green,blue三个颜色随机数除以4得到商值组合成ARGB颜色
        int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;

        //创建圆形渐变效果
        RadialGradient gradient = new RadialGradient(37.5f,12.5f,XBallsFallActivity.BALL_SIZE,
                color,darkColor, Shader.TileMode.CLAMP);
        //获取drawable关联的画笔
        Paint paint = drawable.getPaint();
        paint.setShader(gradient);
        //为XShapeHolder对象设置画笔
        holder.setPaint(paint);
        balls.add(holder);
        return holder;
    }
}
