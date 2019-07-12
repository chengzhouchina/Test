package com.mytest.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mytest.R;

/**
 * date: on 2019/4/3
 * time: 10:28
 * Projec_name: WFWebApp
 * Package_name: com.nengry.waterfriendop.common.view
 * Author: MrT.Cheng
 * desc:
 */
public class HeaderTitleBar extends RelativeLayout {
    private ImageView ivLeft, ivRight;
    private TextView leftTv, titleTv, rightTv;
    private RelativeLayout rl;
    private int rlCorlor;
    private Drawable leftIcon, rightIcon;
    private boolean leftIconVisiable, leftTextVisiable, titleTextVisiable, rightIconVisiable, rightTextVisiable;
    private String leftText, titleText, rightText;
    private int leftTextColor, titleTextColor, rightTextColor;
    private float leftTextSize, titleTextSize, rightTextSize;

    public HeaderTitleBar(Context context) {
        this(context, null);
    }

    public HeaderTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    @SuppressLint("ResourceAsColor")
    private void initViews(final Context context, AttributeSet attrs) {
        View barLayoutView = LayoutInflater.from(context).inflate(R.layout.layout_title, this, true);
        ivLeft = barLayoutView.findViewById(R.id.leftIcon);
        leftTv = barLayoutView.findViewById(R.id.leftText);
        titleTv = barLayoutView.findViewById(R.id.tvCenterTitle);
        ivRight = barLayoutView.findViewById(R.id.rightIcon);
        rightTv = barLayoutView.findViewById(R.id.rightText);
        rl = barLayoutView.findViewById(R.id.rl);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.noemalTitle);
        if (typedArray != null) {
            rlCorlor = typedArray.getColor(R.styleable.noemalTitle_rl_bg, context.getResources().getColor(R.color.color_0393FF));
            leftIcon = typedArray.getDrawable(R.styleable.noemalTitle_left_icon);//左边图片
            leftIconVisiable = typedArray.getBoolean(R.styleable.noemalTitle_left_icon_visiable, false);
            leftText = typedArray.getString(R.styleable.noemalTitle_left_text);
            leftTextColor = typedArray.getColor(R.styleable.noemalTitle_left_textColor, context.getResources().getColor(R.color.white));
            leftTextSize = typedArray.getDimension(R.styleable.noemalTitle_left_textSize, 16);
            leftTextVisiable = typedArray.getBoolean(R.styleable.noemalTitle_left_text_visiable, false);
            titleText = typedArray.getString(R.styleable.noemalTitle_title_text);
            titleTextColor = typedArray.getColor(R.styleable.noemalTitle_title_textColor, context.getResources().getColor(R.color.white));
            titleTextSize = typedArray.getDimension(R.styleable.noemalTitle_title_textSize, 20);
            titleTextVisiable = typedArray.getBoolean(R.styleable.noemalTitle_title_text_visiable, true);
            rightIcon = typedArray.getDrawable(R.styleable.noemalTitle_right_icon);
            rightIconVisiable = typedArray.getBoolean(R.styleable.noemalTitle_right_icon_visiable, false);
            rightText = typedArray.getString(R.styleable.noemalTitle_right_text);
            rightTextColor = typedArray.getColor(R.styleable.noemalTitle_right_textColor, context.getResources().getColor(R.color.white));
            rightTextSize = typedArray.getDimension(R.styleable.noemalTitle_right_textSize, 16);
            rightTextVisiable = typedArray.getBoolean(R.styleable.noemalTitle_right_text_visiable, false);
            rl.setBackgroundColor(rlCorlor);
            setLeftIcon(leftIcon, leftIconVisiable);
            setRightIcon(rightIcon, rightIconVisiable);
            setLeftText(leftText, leftTextColor, leftIconVisiable, leftTextSize);
            setTitle(titleText, titleTextColor, titleTextVisiable, titleTextSize);
            setRightText(rightText, rightTextColor, rightIconVisiable, rightTextSize);

            leftTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    titleOnClickListener.onClicked(LEFT_TEXT);
                }
            });

            rightTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    titleOnClickListener.onClicked(RIGHT_TEXT);
                }
            });

            ivRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    titleOnClickListener.onClicked(RIGHT_ICON);
                }
            });

            ivLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    titleOnClickListener.onClicked(LEFT_ICON);
                }
            });
        }
        typedArray.recycle();
    }

    public void setLeftText(String mLeftText, int color, boolean visiable, float textSize) {
        this.leftText = mLeftText;
        this.leftTextColor = color;
        this.leftTextVisiable = visiable;
        this.leftTextSize = textSize;
        if (leftTv != null) {
            leftTv.setText(leftText);
            leftTv.setTextColor(leftTextColor);
            leftTv.setTextSize(leftTextSize);
            leftTv.setVisibility(leftTextVisiable ? VISIBLE : GONE);
        }
    }

    public void setTitle(String mTitle, int color, boolean visiable, float textSize) {
        this.titleText = mTitle;
        this.titleTextColor = color;
        this.titleTextVisiable = visiable;
        this.titleTextSize = textSize;
        if (titleTv != null) {
            titleTv.setText(titleText);
            titleTv.setTextColor(titleTextColor);
            titleTv.setTextSize(titleTextSize);
            titleTv.setVisibility(titleTextVisiable ? VISIBLE : GONE);
        }
    }

    public void setRightText(String mRightText, int color, boolean visiable, float textSize) {
        this.rightText = mRightText;
        this.rightTextColor = color;
        this.rightTextSize = textSize;
        this.rightTextVisiable = visiable;
        if (rightTv != null) {
            rightTv.setText(rightText);
            rightTv.setTextColor(rightTextColor);
            rightTv.setTextSize(rightTextSize);
            rightTv.setVisibility(rightTextVisiable ? VISIBLE : GONE);
        }
    }

    public void setRightIcon(Drawable mRightIcon, boolean visiable) {
        this.rightIcon = mRightIcon;
        this.rightIconVisiable = visiable;
        if (ivRight != null) {
            ivRight.setImageDrawable(rightIcon);
            ivRight.setVisibility(rightIconVisiable ? VISIBLE : GONE);
        }
    }

    public void setLeftIcon(Drawable mLeftIcon, boolean visiable) {
        this.leftIcon = mLeftIcon;
        this.leftIconVisiable = visiable;
        if (ivLeft != null) {
            ivLeft.setImageDrawable(leftIcon);
            ivLeft.setVisibility(leftIconVisiable ? VISIBLE : GONE);
        }

    }

    private TitleOnClickListener titleOnClickListener;

    public static final int LEFT_TEXT = 1;
    public static final int LEFT_ICON = 2;
    public static final int RIGHT_TEXT = 3;
    public static final int RIGHT_ICON = 4;

    public void setTitleOnClickListener(TitleOnClickListener titleOnClickListener) {
        this.titleOnClickListener = titleOnClickListener;
    }

    /**
     * 监听标题点击接口
     */
    public interface TitleOnClickListener {
        void onClicked(int viewType);
    }
}
