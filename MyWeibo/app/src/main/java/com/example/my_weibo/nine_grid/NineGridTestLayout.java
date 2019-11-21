package com.example.my_weibo.nine_grid;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

public class NineGridTestLayout extends NineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;

    public NineGridTestLayout(Context context) {
        super(context);
    }

    public NineGridTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {
        //Glide.with(mContext).load(url).into(imageView);
        Glide.with(mContext)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        //加载完成后的处理
                        int w = resource.getWidth();
                        int h = resource.getHeight();
                        int newW;
                        int newH;
                        if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                            newW = parentWidth / 2;
                            newH = newW * 5 / 3;
                        } else if (h < w) {//h:w = 2:3
                            newW = parentWidth * 2 / 3;
                            newH = newW * 2 / 3;
                        } else {//newH:h = newW :w
                            newW = parentWidth / 2;
                            newH = h * newW / w;
                        }
                        setOneImageLayoutParams(imageView, newW, newH);
                        imageView.setImageBitmap(resource);
                    }
                });

        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        Glide.with(mContext).load(url).into(imageView);
    }

    @Override
    protected void onClickImage(int i, String url, ArrayList<String> urlList) {
        Toast.makeText(mContext, "第"+(i+1)+ "张图片", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(mContext, MyGalleryActivity.class);
//        mContext.startActivity(intent);
    }
}
