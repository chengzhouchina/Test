package com.mytest.blur;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.mytest.BaseApplication;
import com.mytest.R;
import com.mytest.base.BaseActivity;
import com.mytest.widget.BlurNetworkImageView;
import com.mytest.utils.FastBlurUtil;
import com.mytest.utils.VolleyUtils;

public class BitmapBlurActivity extends BaseActivity {

    private Bitmap blurBitmap2;

    @Override
    public int initLayout() {
        return R.layout.activity_bitmap_blur;
    }

    @Override
    public void initIntent() {

    }

    @Override
    public void addListener() {

    }

    @Override
    public void initData() {

        final LinearLayout mGailay = findViewById(R.id.layout_userinfo_album);//横向图片列表
        ImageView iv_top = findViewById(R.id.iv_cover);

        BlurNetworkImageView imageView = findViewById(R.id.wc_gv_iv);
        ImageLoader imageLoader = VolleyUtils.getInstance(this).getImageLoader();
        imageView.setRadios(15);
        imageView.setImageUrl("https://jy.whwangdoudou.cn/attachment/images/sample.jpg",imageLoader);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    final View view = LayoutInflater.from(BitmapBlurActivity.this).inflate(R.layout.iteam_gaily, mGailay, false);
                    final ImageView miv = view.findViewById(R.id.iv_gaily_item);
                    ImageView ivcover = (ImageView) view.findViewById(R.id.iv_cover);
                    String url = "https://jy.whwangdoudou.cn/attachment/images/sample.jpg";
                    blurBitmap2 = FastBlurUtil.GetUrlBitmap(url, 10);
                    BaseApplication.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            miv.setImageBitmap(blurBitmap2);
                            mGailay.addView(view);
                        }
                    });



                    //        获取需要被模糊的原图bitmap
//                    Resources res = getResources();
//                    Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.app_icon);
//
//                    //        scaledBitmap为目标图像，10是缩放的倍数（越大模糊效果越高）
//                    Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 0);
//                    ivcover.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    ivcover.setImageBitmap(blurBitmap);
//                    iv_top.setImageResource(R.drawable.app_icon);
//            miv.setRadios(15);

                }


            }
        }).start();

    }

    @Override
    public void onClick(View view) {

    }
}
