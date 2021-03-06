package com.example.my_weibo;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.my_weibo.myLayout.WeiboItemView;
import com.example.my_weibo.nine_grid.NineGridTestLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;

public class MyDataAdapter extends BaseAdapter{
    private Context mContext = null;
    private List<BaseDataClass> mData = null;

    public MyDataAdapter(Context context, List<BaseDataClass> data){
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getViewTypeCount(){
        return ViewTypeHelper.VIEW_TYPE_COUNT;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        BaseDataClass obj = mData.get(position);
        try {
            return ViewTypeHelper.getViewType(obj);
        } catch (UnknownTypeViewTypeException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View rootView = null;
        BaseDataClass obj = mData.get(position);
        ViewHolder viewHolder;
        try {
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                rootView = inflater.inflate(ViewTypeHelper.getViewTemplate(obj), parent, false);
                viewHolder = new ViewHolder();
                viewHolder.avatar = rootView.findViewById(R.id.avatar);
                viewHolder.content =  rootView.findViewById(R.id.content);
                viewHolder.jiugongge = rootView.findViewById(R.id.jiugongge);
                viewHolder.forwardContent = rootView.findViewById(R.id.forwardContent);
                viewHolder.forwardJiugongge = rootView.findViewById(R.id.forwardJiugongge);
                viewHolder.forwardLayout = rootView.findViewById(R.id.forwardLayout);
                rootView.setTag(viewHolder);
            }else{
                rootView = convertView;
                viewHolder = (ViewHolder)rootView.getTag();
            }
            if(ViewTypeHelper.getViewType(obj) == ViewTypeHelper.VIEW_TYPE_ITEM){
                processStreamObject((MyDataClass)obj, rootView, viewHolder);
            }else if(ViewTypeHelper.getViewType(obj) == ViewTypeHelper.VIEW_TYPE_TOP){
                processTopObject((MyTopDataClass)obj, rootView);
            }
        } catch (UnknownTypeViewTypeException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    //处理顶部横向ScrollView
    private void processTopObject(MyTopDataClass obj, View rootView) {
        ImageView avatar = (ImageView) rootView.findViewById(R.id.imageView1);
        Glide.with(mContext).load("https://tvax3.sinaimg.cn/crop.0.0.1002.1002.180/68418ffbly8g4mvnfoe89j20ru0ruwga.jpg?KID=imgbed,tva&Expires=1572866440&ssig=9Lf5u6YSbe").into(avatar);
        avatar = (ImageView) rootView.findViewById(R.id.imageView2);
        Glide.with(mContext).load("https://tvax3.sinaimg.cn/crop.0.0.512.512.180/006wDhxsly8g62hkt9go1j30e80e8gmk.jpg?KID=imgbed,tva&Expires=1572866440&ssig=4Bw1x%2FxCcO").into(avatar);
        avatar = (ImageView) rootView.findViewById(R.id.imageView3);
        Glide.with(mContext).load("https://tvax1.sinaimg.cn/crop.0.0.100.100.180/718878b5ly8fwsf8nj3kkj202s02sq37.jpg?KID=imgbed,tva&Expires=1572866440&ssig=Gr44t53L0F").into(avatar);
        avatar = (ImageView) rootView.findViewById(R.id.imageView4);
        Glide.with(mContext).load("https://tvax3.sinaimg.cn/crop.0.0.512.512.180/78ed3187ly8g6ojf7nf39j20e80e8jrl.jpg?KID=imgbed,tva&Expires=1572866440&ssig=6hBA2n225v").into(avatar);
        avatar = (ImageView) rootView.findViewById(R.id.imageView5);
        Glide.with(mContext).load("https://tva4.sinaimg.cn/crop.0.0.180.180.180/61e7f8b0jw8eswr7csaddj205005074c.jpg?KID=imgbed,tva&Expires=1572866440&ssig=uuLgtXfdis").into(avatar);
        avatar = (ImageView) rootView.findViewById(R.id.imageView6);
        Glide.with(mContext).load("https://tvax4.sinaimg.cn/crop.0.0.1000.1000.180/8345c393ly8g8f25gmtj8j20rs0rs468.jpg?KID=imgbed,tva&Expires=1572866440&ssig=9JQPs6KiVX").into(avatar);
        avatar = (ImageView) rootView.findViewById(R.id.imageView7);
        Glide.with(mContext).load("https://tvax1.sinaimg.cn/crop.0.0.512.512.180/b7cd25e6ly8ftk9plsedfj20e80e8wes.jpg?KID=imgbed,tva&Expires=1572866440&ssig=FrAS1%2BICu7").into(avatar);
    }

    //处理流数据
    private void processStreamObject(final MyDataClass obj, View rootView, ViewHolder viewHolder) {
        //设置昵称、几分钟前和来自哪里、头像
        viewHolder.avatar.init(obj.getNickName(), obj.getTime()+obj.getFrom(),obj.getAvatar());
        //文本内容
        viewHolder.content.setOnTouchListener(new TextViewListener());
        viewHolder.content.setText(findOutTopic(obj.getContent(), obj.getTopicURL()));
        //判断正文是否有图片，若无图片则隐藏九宫格
        ArrayList<String> imageRoute = obj.getImageRoute();
        if(imageRoute == null){
            viewHolder.jiugongge.setVisibility(View.GONE);
        }else{
            viewHolder.jiugongge.setUrlList(imageRoute);
        }
        //判断是否转发，若无转发则隐藏转发视图
        String forward_content = obj.getForwardContent();
        if(forward_content == null){
            viewHolder.forwardLayout.setVisibility(View.GONE);
        }else{
            viewHolder.forwardContent.setText(
                    findOutTopic("@" + obj.getForwardNickName() + ":" + forward_content,
                            obj.getTopicURL()));
            //开始响应点击事件
            viewHolder.forwardContent.setOnTouchListener(new TextViewListener());
            //判断转发的微博是否有图片，没有则将九宫格隐藏
            ArrayList<String> forwardImageRoute = obj.getForwardImageRoute();
            if(forwardImageRoute == null){
                viewHolder.forwardJiugongge.setVisibility(View.GONE);
            }else{
                viewHolder.forwardJiugongge.setUrlList(forwardImageRoute);
            }
        }
    }

    //找出文本中的话题部分（以#开始#结束），设置字体为蓝色且可点击
    private SpannableString findOutTopic(String text, HashMap<String, String> topicURL){
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
                        final String url = topicURL.get(String.valueOf(ss.subSequence(firstPtr,lastPtr)));
                        ss.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull View widget) {
                                if(url != null){
                                    try{
                                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                                    }catch (ActivityNotFoundException e){
                                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://c.weibo.cn/")));
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

    //makes ListView items and TextView UrlSpans clickable
    class TextViewListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean ret = false;
            CharSequence text = ((TextView) v).getText();
            Spannable stext = Spannable.Factory.getInstance().newSpannable(text);
            TextView widget = (TextView) v;
            int action = event.getAction();
            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();
                x += widget.getScrollX();
                y += widget.getScrollY();
                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);
                ClickableSpan[] link = stext.getSpans(off, off, ClickableSpan.class);
                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget);
                    }
                    ret = true;
                }
            }
            return ret;
        }
    }

    //ViewHolder内部类，它的实例用于缓存View控件
    private static class ViewHolder{
        WeiboItemView avatar;
        TextView content;
        NineGridTestLayout jiugongge;
        LinearLayout forwardLayout;
        TextView forwardContent;
        NineGridTestLayout forwardJiugongge;
    }
}