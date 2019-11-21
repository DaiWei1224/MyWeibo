package com.example.my_weibo.json;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.example.my_weibo.MyDataClass;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalysisJsonFile {
    //打开J并解析Json文件
    public static ArrayList<MyDataClass> openJsonFile(Context context, String fileName) {
        ArrayList<MyDataClass> data = null;
        try {
            InputStreamReader reader  = new InputStreamReader(context.getAssets().open(fileName));
            JsonReader jr = new JsonReader(reader);
            data = readMessageArray(jr);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // 解析数组
    public static ArrayList<MyDataClass> readMessageArray(JsonReader reader) {
        ArrayList<MyDataClass> data = new ArrayList<>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                data.add(readMessage(reader));
            }
            reader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // 解析对象
    public static MyDataClass readMessage(JsonReader reader) {
        MyDataClass item = null;
        ArrayList<String> routeIndex = null;
        try {
            reader.beginObject();
            item = new MyDataClass();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if ("text".equals(name)) {
                    item.setContent(reader.nextString());
                } else if ("timestamp_text".equals(name)) {
                    item.setTime(reader.nextString());
                } else if ("source".equals(name)) {
                    item.setFrom(getFrom(reader.nextString()));
                } else if ("user".equals(name)) {
                    item = getUserInfo(reader, item);
                } else if("pic_ids".equals((name))){
                    routeIndex = getImageIndex(reader);
                } else if("pic_infos".equals(name)){
                    if(routeIndex != null){
                        item.setImageRoute(getImageRoute(reader, routeIndex));
                    }else{
                        reader.skipValue();
                    }
                } else if("retweeted_status".equals(name)){ //转发内容
                    getForward(reader, item);
                } else if("topic_struct".equals(name)) { //话题URL
                    item.setTopicURL(getTopicURL(reader));
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    // 解析user对象
    public static MyDataClass getUserInfo(JsonReader reader, MyDataClass item) {
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if ("screen_name".equals(name)) {
                    item.setNickName(reader.nextString());
                } else if ("avatar_large".equals(name)) {
                    item.setAvatar(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    //获取微博来源
    public static String getFrom(String in){
        String out = "";
        int i = 1;
        for(; i < in.length() && in.charAt(i) != '>'; i++);
        i++;
        for(;i < in.length() && in.charAt(i) != '<'; i++){
            out += in.charAt(i);
        }
        if(out.length() != 0){
            out = " 来自" + out;
        }
        return out;
    }

    //获取图片路径的索引
    public static ArrayList<String> getImageIndex(JsonReader reader){
        ArrayList<String > index = null;
        try{
            if(reader.hasNext()){
                index = new ArrayList<>();
                reader.beginArray();
                while (reader.hasNext()){
                    String s = reader.nextString();
                    index.add(s);
                }
                reader.endArray();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return index;
    }

    //获取图片路径
    public static ArrayList<String> getImageRoute(JsonReader reader, ArrayList<String> index){
        ArrayList<String > route = new ArrayList<>();
        try {
            reader.beginObject();//进入pic_infos对象
            while (reader.hasNext()) {
                reader.nextName();
                reader.beginObject();//进入图片索引名称对象
                while (reader.hasNext()){
                    String name = reader.nextName();
                    if("large".equals(name)){
                        reader.beginObject();//进入large对象，选择large类型的图片进行展示
                        while (reader.hasNext()){
                            String url = reader.nextName();
                            if("url".equals(url)){
                                route.add(reader.nextString());//获取图片url
                            }else{
                                reader.skipValue();
                            }
                        }reader.endObject();
                    }else{
                        reader.skipValue();
                    }
                }reader.endObject();
            }reader.endObject();//退出pic_infos对象
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  route;
    }

    //获取转发内容
    public static void getForward(JsonReader reader, MyDataClass item){
        ArrayList<String> routeIndex = null;
        try{
            reader.beginObject();//进入retweed_status对象
            while(reader.hasNext()) {
                String name = reader.nextName();
                if("text".equals(name)){
                    item.setForwardContent(reader.nextString());
                } else if("pic_ids".equals(name)){
                    routeIndex = getImageIndex(reader);
                } else if("user".equals(name)){
                    reader.beginObject();
                    while(reader.hasNext()){
                        String name2 = reader.nextName();
                        if("screen_name".equals(name2)){
                            item.setForwardNickName(reader.nextString());
                        }else{
                            reader.skipValue();
                        }
                    }reader.endObject();
                } else if("pic_infos".equals(name)){
                    if(routeIndex != null){
                        item.setForwardImageRoute(getImageRoute(reader, routeIndex));
                    }else{
                        reader.skipValue();
                    }
                } else{
                    reader.skipValue();
                }
            }reader.endObject();//退出retweed_status对象
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    //获取话题的URL
    public static HashMap<String, String> getTopicURL(JsonReader reader){
        HashMap<String, String> hashMap = new HashMap<>();
        try{
            if(reader.hasNext()){
                reader.beginArray();
                while (reader.hasNext()){
                    //reader.nextName();
                    reader.beginObject();
                    String url = null;
                    while (reader.hasNext()){
                        String name = reader.nextName();
                        if("topic_url".equals(name)){
                            url = reader.nextString();  //因为Json中先读取url后读取话题名称，所以先保存url的值
                        }else if("topic_title".equals(name)){
                            hashMap.put("#"+reader.nextString()+"#", url);
                        }else {
                            reader.skipValue();
                        }
                    }reader.endObject();
                }reader.endArray();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return hashMap;
    }

//    // 解析Geo数组
//    public static ArrayList<Double> readGeo(JsonReader reader) {
//        ArrayList<Double> list = new ArrayList<>();
//        try {
//            reader.beginArray();
//            while (reader.hasNext()) {
//                // 直接读数据就可以
//                double nextDouble = reader.nextDouble();
//                list.add(nextDouble);
//            }
//            reader.endArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    // 解析user对象
//    public static User readUser(JsonReader reader) {
//        User user = new User();
//        try {
//            reader.beginObject();
//            while (reader.hasNext()) {
//                String name = reader.nextName();
//                if ("name".equals(name)) {
//                    user.setName(reader.nextString());
//                } else if ("followers_count".equals(name)) {
//                    user.setFollowers_count(reader.nextInt());
//                } else {
//                    reader.skipValue();
//                }
//            }
//            reader.endObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return user;
//    }

//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//            AssetManager assetManager = context.getAssets();
//            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
//            String line;
//            while ((line = bf.readLine()) != null){
//                stringBuilder.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return stringBuilder.toString();
//    }
}
