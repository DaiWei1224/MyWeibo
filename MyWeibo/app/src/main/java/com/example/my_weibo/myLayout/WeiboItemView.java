package com.example.my_weibo.myLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;

import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class WeiboItemView extends View {
    Paint mPaint = null;
    Bitmap avatar = null;
    int mRadius = 75;
    String nickName = null;
    String from = null;

    public WeiboItemView(Context context) {
        super(context);
    }

    public WeiboItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeiboItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(String nickName, String from, String avaterURL) {
        this.nickName = nickName;
        this.from = from;
        Glide.with(getContext())
                .load(avaterURL)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        avatar = resource;
                        postInvalidate();
                    }
                });
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 当布局参数设置为wrap_content时，设置高度为150
        if(getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), 150);
        }
    }

    @Override
    public void onDraw(final Canvas canvas){
        if(avatar != null){
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            //绘制正方形头像
//            //指定图片绘制区域
//            Rect src=new Rect(0,0, avatar.getWidth(), avatar.getHeight());
//            //指定图片在屏幕上显示的区域
//            Rect dst=new Rect(0,0,150,150);
//            //绘制图片
//            canvas.drawBitmap(avatar,src,dst,mPaint);
//            //canvas.drawBitmap(avater,0,0,new Paint());

            //绘制圆形头像
            //初始化BitmapShader，传入bitmap对象
            BitmapShader bitmapShader = new BitmapShader(avatar, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            //计算缩放比例
            float scale = mRadius * 2.0f / avatar.getWidth();
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            bitmapShader.setLocalMatrix(matrix);
            mPaint.setShader(bitmapShader);
            //画圆形，指定好坐标，半径，画笔
            canvas.drawCircle(75, 75, mRadius, mPaint);

            mPaint.setShader(null);
            //绘制昵称
            mPaint.setColor(Color.parseColor("#FF8000"));
            mPaint.setTextSize(65);
            canvas.drawText(nickName, 200 , 60, mPaint);

            //绘制设备信息
            mPaint.setColor(Color.parseColor("#808080"));
            mPaint.setTextSize(45);
            canvas.drawText(from, 200 , 145, mPaint);
        }
    }

}

