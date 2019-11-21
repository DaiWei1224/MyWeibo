package com.example.my_weibo;

public class ViewTypeHelper {
    public static final int VIEW_TYPE_COUNT = 2;
    public static final int VIEW_TYPE_TOP = 0;
    public static final int VIEW_TYPE_ITEM = 1;

    //依据数据对象类型获取其视图类型
    public static int getViewType(BaseDataClass obj) throws UnknownTypeViewTypeException {
        String className=obj.getClass().getSimpleName();
        switch (className){
            case "MyDataClass":
                return VIEW_TYPE_ITEM;
            case "MyTopDataClass":
                return VIEW_TYPE_TOP;
            default:
                throw new UnknownTypeViewTypeException("未知的数据类型");
        }
    }

    //依据数据对象类型，获取其视图模板
    public static int getViewTemplate(BaseDataClass obj) throws UnknownTypeViewTypeException{
        String className=obj.getClass().getSimpleName();
        switch (className){
            case "MyDataClass":
                return R.layout.weibo_item;
            case "MyTopDataClass":
                return R.layout.top_recycle_view;
            default:
                throw new UnknownTypeViewTypeException("未知的数据类型");
        }
    }

}