package com.example.my_weibo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> imageRoute;    //图片路径

    public ImageAdapter(Context context, ArrayList<String> route){
        this.mContext = context;
        this.imageRoute = route;
    }

    @Override
    public int getCount() {
        if(imageRoute == null){
            return 0;
        }else{
            return imageRoute.size() > 9 ? 9 : imageRoute.size(); //最多显示9张图片
        }
    }

    @Override
    public Object getItem(int position) {
        return imageRoute.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String route = imageRoute.get(position);
        View rootView = null;
        if(convertView != null){
            rootView = convertView;
        }else{
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rootView = inflater.inflate(R.layout.image_view, parent, false);
        }
        ImageView imageView= rootView.findViewById(R.id.image);
        Glide.with(mContext).load(route).into(imageView);
        return rootView;
    }
}
