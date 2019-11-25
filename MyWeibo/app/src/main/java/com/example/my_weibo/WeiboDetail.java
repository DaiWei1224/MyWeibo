package com.example.my_weibo;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.my_weibo.myLayout.WeiboItemView;
import com.example.my_weibo.nine_grid.NineGridTestLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class WeiboDetail extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weibo_detail);
        inatialUI();
    }

    //初始化界面
    private void inatialUI(){
        //返回首页按钮
        findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle = this.getIntent().getExtras();
//        //昵称
//        TextView nickName = (TextView) findViewById(R.id.nickName);
//        nickName.setText(bundle.getString("nickName"));
//        //几分钟前和来自哪里
//        TextView from = (TextView) findViewById(R.id.from);
//        from.setText(bundle.getString("time") + bundle.getString("from"));
//        //头像
//        ImageView avatar = (ImageView) findViewById(R.id.avatar);
//        //int resId = mContext.getResources().getIdentifier("star","drawable",mContext.getPackageName());
//        //head.setImageResource(resId);
//        Glide.with(this).load(bundle.getString("avatar")).into(avatar);
        //设置昵称、几分钟前和来自哪里、头像
        WeiboItemView weiboItemView = findViewById(R.id.avatar);
        weiboItemView.init(bundle.getString("nickName"),
                bundle.getString("time") + bundle.getString("from"),
                bundle.getString("avatar"));
        //文本内容
        TextView content = (TextView) findViewById(R.id.content);
        //content.setText(bundle.getString("content"));
        //开始响应点击事件
        content.setMovementMethod(LinkMovementMethod.getInstance());
        content.setText(findOutTopic(bundle.getString("content"), bundle));

        //判断正文是否有图片，若无图片则隐藏九宫格
        ArrayList<String> imageRoute = bundle.getStringArrayList("imageRoute");
        //GridView jiuGongGe = findViewById(R.id.jiugongge);
        NineGridTestLayout jiuGongGe = findViewById(R.id.jiugongge);
        if(imageRoute == null){
            jiuGongGe.setVisibility(View.GONE);
        }else{
            jiuGongGe.setUrlList(imageRoute);
//            ImageAdapter imageAdapter = new ImageAdapter(this, imageRoute);
////            jiuGongGe.setAdapter(imageAdapter);
        }
        //判断是否转发，若无转发则隐藏转发视图
        LinearLayout forwardLayout = (LinearLayout)findViewById(R.id.forwardLayout);
        String forward_content = bundle.getString("forwardContent");
        if(forward_content == null){
            forwardLayout.setVisibility(View.GONE);
        }else{
            TextView forwardContent = (TextView)findViewById(R.id.forwardContent);
            //开始响应点击事件
            forwardContent.setMovementMethod(LinkMovementMethod.getInstance());
            forwardContent.setText(findOutTopic("@" + bundle.getString("forwardNickName") + ":" + forward_content, bundle));
            //判断转发的微博是否有图片，没有则将九宫格隐藏
            ArrayList<String> forwardImageRoute = bundle.getStringArrayList("forwardImageRoute");
            //GridView forwardJiugongge = findViewById(R.id.forwardJiugongge);
            NineGridTestLayout forwardJiugongge = findViewById(R.id.forwardJiugongge);
            if(forwardImageRoute == null){
                forwardJiugongge.setVisibility(View.GONE);
            }else{
                forwardJiugongge.setUrlList(forwardImageRoute);
//                ImageAdapter forwardImageAdapter = new ImageAdapter(this, forwardImageRoute);
//                forwardJiugongge.setAdapter(forwardImageAdapter);
            }
        }
    }

    //找出文本中的话题部分（以#开始#结束），设置字体为蓝色且可点击
    private SpannableString findOutTopic(String text, final Bundle bundle){
        SpannableString ss = new SpannableString(text);
        int firstPtr = 0;
        int lastPtr = 0;
        int i = 0;
        int len = ss.length();
        while(i < len){
            if(ss.charAt(i) == '#'){    //找到第一个#
                firstPtr = i++;
                while(i < len){
                    if(ss.charAt(i) == '#'){    //找到对应结尾的#
                        lastPtr = ++i;
                        //设置点击事件
                        final String url = bundle.getString(String.valueOf(ss.subSequence(firstPtr,lastPtr)));
                        ss.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull View widget) {
                                //Toast.makeText(getBaseContext(),url,Toast.LENGTH_SHORT).show();
                                if(url != null){
                                    try{
                                        getBaseContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                    }catch (ActivityNotFoundException e){
                                        getBaseContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://c.weibo.cn/")));
                                    }
                                }
                            }
                        }, firstPtr, lastPtr, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        //设置字体颜色
                        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#6CA6CD")),
                                firstPtr, lastPtr, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        break;
                    }else{
                        i++;
                    }
                }
            }else{
                i++;
            }
        }
        return ss;
    }
}
