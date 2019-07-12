package com.mytest.gridlayout;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowBigPicActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_magnify)
    Button btn_magnify;
    @BindView(R.id.btn_decrease)
    Button btn_decrease;
    @BindView(R.id.iv_normal)
    ImageView iv_normal;
    @BindView(R.id.iv_show)
    ImageView iv_show;

    //定义图片的初始透明度
    private int alpha = 255;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ButterKnife.bind(this);
        btn_decrease.setOnClickListener(this);
        btn_magnify.setOnClickListener(this);

        iv_normal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) iv_normal.getDrawable();
                //获取第一个图片显示框中的位图
                Bitmap bitmap = bitmapDrawable.getBitmap();
                //bitmap图片实际大小与第一个imageview的缩放比例
                double scale = 1.0 * bitmap.getHeight() / iv_normal.getHeight();
                //获取需要显示的图片的开始点
                int x = (int) (event.getX() * scale);
                int y = (int) (event.getY() * scale);
                if (x + 120 > bitmap.getWidth()) {
                    x = bitmap.getWidth() - 120;
                }
                if (y + 120 > bitmap.getHeight()) {
                    y = bitmap.getWidth() - 120;
                }
                //显示图片的制定区域
                iv_show.setImageBitmap(Bitmap.createBitmap(bitmap, x, y, 120, 120));

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (btn_decrease == v) {
            alpha -= 20;
        }
        if (btn_magnify == v) {
            alpha += 20;
        }
        if (alpha >= 255) {
            alpha = 255;
        }
        if (alpha <= 0) {
            alpha = 0;
        }
        iv_normal.setImageAlpha(alpha);
        iv_show.setImageAlpha(alpha);
    }
}
