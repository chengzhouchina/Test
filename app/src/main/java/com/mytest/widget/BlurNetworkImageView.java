package com.mytest.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;
import com.mytest.utils.FastBlurUtil;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class BlurNetworkImageView extends NetworkImageView {
    private int radios = 0;

    public void setRadios(int radios) {
        this.radios = radios;
    }

    public BlurNetworkImageView(Context context) {
        super(context);
        // setupView();
    }

    public BlurNetworkImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //  setupView();
    }

    public BlurNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // setupView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (radios < 1) {
            return;
        }

        Drawable drawable = getDrawable();

        //空值判断，必要步骤，避免由于没有设置src导致的异常错误
        if (drawable == null) {
            return;
        }

        //必要步骤，避免由于初始化之前导致的异常错误
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        if (!(drawable instanceof BitmapDrawable)) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();

        if (null == b) {
            return;
        }

        Bitmap bitmap3 = b.copy(Bitmap.Config.ARGB_8888, true);


        Bitmap bitmap2 = FastBlurUtil.doBlur(bitmap3, radios, false);
        canvas.drawBitmap(bitmap2, 0, 0, null);

    }

}
