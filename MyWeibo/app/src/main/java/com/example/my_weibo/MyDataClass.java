package com.example.my_weibo;

import java.util.ArrayList;
import java.util.HashMap;

public class MyDataClass extends BaseDataClass{
    String avatar;                          //头像
    String nickName;                        //昵称
    String time;                            //几分钟前
    String from;                            //微博来源
    String content;                         //文字
    ArrayList<String> imageRoute;           //图片
    String forwardNickName;                 //转发昵称
    String forwardContent;                  //转发文字
    ArrayList<String> forwardImageRoute;    //转发图片
    HashMap<String, String> topicURL;       //话题URL

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getImageRoute() {
        return imageRoute;
    }

    public void setImageRoute(ArrayList<String> imageRoute) {
        this.imageRoute = imageRoute;
    }

    public String getForwardNickName() {
        return forwardNickName;
    }

    public void setForwardNickName(String forwardNickName) {
        this.forwardNickName = forwardNickName;
    }

    public String getForwardContent() {
        return forwardContent;
    }

    public void setForwardContent(String forwardContent) {
        this.forwardContent = forwardContent;
    }

    public ArrayList<String> getForwardImageRoute() {
        return forwardImageRoute;
    }

    public void setForwardImageRoute(ArrayList<String> forwardImageRoute) {
        this.forwardImageRoute = forwardImageRoute;
    }

    public HashMap<String, String> getTopicURL() {
        return topicURL;
    }

    public void setTopicURL(HashMap<String, String> topicURL) {
        this.topicURL = topicURL;
    }

}