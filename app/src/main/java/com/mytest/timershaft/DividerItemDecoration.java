package com.mytest.timershaft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private List<String> data;
    //写右边字的画笔(具体信息)
    private Paint mPaint;

    //写左边日期字的画笔(时间 + 日期)
    private Paint mPaint1;
    private Paint mPaint2;

    //左 上偏移长度
    private int itemView_leftinterval;
    private int itemView_topinterval;

    //轴点半径
    private int circle_tadius;

    //图标
    private Bitmap mIcon;

    public DividerItemDecoration(Context context, List<String> data) {
        //轴点画笔(红色)
        mPaint = new Paint();
        mPaint.setColor(Color.RED);

        //左边时间文本画笔(蓝色)
        mPaint1 = new Paint();
        mPaint1.setColor(Color.BLUE);
        mPaint1.setTextSize(30);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.BLUE);

        //赋值ItemView的左偏移长度为200
        itemView_leftinterval = 200;
        //赋值ItemView的上偏移长度为50
        itemView_topinterval = 100;
        //赋值轴点圆的半径为10
        circle_tadius = 10;

        //获取图标资源
//        mIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        this.data = data;
    }

    //重写getItemOffsets()方法
    //设置itemview左&上偏移长度
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //设置itemview的左&上偏移长度分别为200px%50px,即此为onDraw()可绘制的区域
        outRect.set(itemView_leftinterval, itemView_topinterval, 0, 0);
    }

    //重写OnDraw()
    //在间隔区域里绘制时光轴线 & 时间文本
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //获取recyclerview的child_view数量
        int child = parent.getChildCount();

        //遍历每个item,分别获取它们的位置信息,然后再绘制对应的分割线
        for (int i = 0; i < child; i++) {
            //获取每个item对象
            View childview = parent.getChildAt(i);

            //绘制轴点  轴点 = 圆（x,y）
            float centerX = childview.getLeft() - itemView_leftinterval / 3;
            float centerY = childview.getTop() - itemView_topinterval + (itemView_topinterval + childview.getHeight()) / 2;

            //绘制轴点圆
            c.drawCircle(centerX, centerY, circle_tadius, mPaint);
            //通过canvas绘制角标
//            c.drawBitmap(mIcon,centerX - circle_tadius,centerY - circle_tadius,mPaint);

            /**
             * 绘制上半轴线
             */
            //上端点坐标(x,y)
            float upLine_up_x = centerX;
            float upLine_up_y = childview.getTop() - itemView_topinterval;
            //下端点坐标
            float upLine_bottom_x = centerX;
            float upLine_bottom_y = centerY - circle_tadius;
            //绘制上半轴轴线
            c.drawLine(upLine_up_x, upLine_up_y, upLine_bottom_x, upLine_bottom_y, mPaint);


            /**
             * 绘制下半轴线
             */
            //上端点坐标
            float bottomLine_up_x = centerX;
            float bottomLine_up_y = centerY + circle_tadius;
            //下端点坐标
            float bottomLine_bottom_x = centerX;
            float bottomLine_bottom_y = childview.getBottom();
            //绘制下半轴线
            c.drawLine(bottomLine_up_x, bottomLine_up_y, bottomLine_bottom_x, bottomLine_bottom_y, mPaint);

            /**
             * 绘制左边时间文本
             */
            //获取每个Item的位置
            int index = parent.getChildAdapterPosition(childview);
            //设置文本起始的位置
            float text_x = childview.getLeft() - itemView_leftinterval * 5 / 6;
            float text_y = upLine_bottom_y;

            c.drawText(data.get(index), text_x, text_y, mPaint1);
            c.drawText("2017.12.13 -- " + index, text_x + 5, text_y + 20, mPaint2);
            //根据item位置设置时间文本
//            switch (index){
//                case (0):
//                    //设置日期绘制位置
//                    c.drawText("13:40",text_x,text_y,mPaint1);
//                    c.drawText("2017.12.13",text_x+5,text_y+20,mPaint2);
//                break;
//                case (1):
//                    //设置日期绘制位置
//                    c.drawText("13:40",text_x,text_y,mPaint1);
//                    c.drawText("2017.12.13",text_x+5,text_y+20,mPaint2);
//                    break;
//                case (2):
//                    //设置日期绘制位置
//                    c.drawText("13:40",text_x,text_y,mPaint1);
//                    c.drawText("2017.12.13",text_x+5,text_y+20,mPaint2);
//                    break;
//                case (3):
//                    //设置日期绘制位置
//                    c.drawText("13:40",text_x,text_y,mPaint1);
//                    c.drawText("2017.12.13",text_x+5,text_y+20,mPaint2);
//                    break;
//                case (4):
//                    //设置日期绘制位置
//                    c.drawText("13:40",text_x,text_y,mPaint1);
//                    c.drawText("2017.12.13",text_x+5,text_y+20,mPaint2);
//                    break;
//                case (5):
//                    //设置日期绘制位置
//                    c.drawText("13:40",text_x,text_y,mPaint1);
//                    c.drawText("2017.12.13",text_x+5,text_y+20,mPaint2);
//                    break;
//            }
        }
    }
}
