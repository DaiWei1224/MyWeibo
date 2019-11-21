package com.example.my_weibo;

import android.content.Context;

import com.example.my_weibo.json.AnalysisJsonFile;

import java.util.ArrayList;
import java.util.List;

public class MyDataRepository {

    public static List<BaseDataClass> dataClasses = null;

    public static List<BaseDataClass> getData(Context context){
        if(dataClasses == null)
            setData(context);
        return dataClasses;
    }

    private static void setData(Context context){
        //List<BaseDataClass> list = new ArrayList<>();
        dataClasses = new ArrayList<>();
        //顶部横滑栏
        MyTopDataClass header = new MyTopDataClass();
        dataClasses.add(header);
        //微博流
        ArrayList<MyDataClass> data = AnalysisJsonFile.openJsonFile(context,"Feed2.json");
        for(MyDataClass dataItem: data){
            dataClasses.add(dataItem);
        }
    }

}