package com.example.my_weibo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.my_weibo.MyDataAdapter;
import com.example.my_weibo.MyDataClass;
import com.example.my_weibo.MyDataRepository;
import com.example.my_weibo.R;
import com.example.my_weibo.WeiboDetail;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentHome extends Fragment {

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.i("FragmentMine","onAttach");
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.i("FragmentMine","onCreate");
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        //Log.i("FragmentMine","onCreateView");
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        if(savedInstanceState == null){
            final ListView listView = layout.findViewById(R.id.mainListView);
            final MyDataAdapter dataAdapter = new MyDataAdapter(this.getContext(), MyDataRepository.getData(this.getContext()));
            listView.setAdapter(dataAdapter);
            //为listView添加行点击事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final MyDataClass data = (MyDataClass) parent.getItemAtPosition(position);
                    Intent intent = new Intent(listView.getContext(), WeiboDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("avatar",data.getAvatar());
                    bundle.putString("nickName",data.getNickName());
                    bundle.putString("time",data.getTime());
                    bundle.putString("from",data.getFrom());
                    bundle.putString("content",data.getContent());
                    bundle.putStringArrayList("imageRoute",data.getImageRoute());
                    bundle.putString("forwardNickName",data.getForwardNickName());
                    bundle.putString("forwardContent",data.getForwardContent());
                    bundle.putStringArrayList("forwardImageRoute",data.getForwardImageRoute());
                    HashMap<String, String> url = data.getTopicURL();
                    if(url != null){
                        for(Map.Entry<String, String> entry: url.entrySet()) {
                            bundle.putString(entry.getKey(), entry.getValue());
                        }
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
        return layout;
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Log.i("FragmentMine","onActivityCreated");
//        ListView listView = (ListView)getActivity().findViewById(R.id.mainListView);
//        MyDataAdapter dataAdapter = new MyDataAdapter(this.getContext(), MyDataRepository.data);
//        listView.setAdapter(dataAdapter);
//    }
//
//    @Override
//    public void onStart(){
//        super.onStart();
//        Log.i("FragmentMine","onStart");
//    }
//
//    @Override
//    public void onResume(){
//        super.onResume();
//        Log.i("FragmentMine","onResume");
//    }
//
//    @Override
//    public void onPause(){
//        super.onPause();
//        Log.i("FragmentMine","onPause");
//    }
//
//    @Override
//    public void onStop(){
//        super.onStop();
//        Log.i("FragmentMine","onStop");
//    }
//
//    @Override
//    public void onDestroyView(){
//        Log.i("FragmentMine","onDestroyView");
//        super.onDestroyView();
//    }
//
//    @Override
//    public void onDestroy(){
//        Log.i("FragmentMine","onDestroy");
//        super.onDestroy();
//    }
//    @Override
//    public void onDetach(){
//        Log.i("FragmentMine","onDetach");
//        super.onDetach();
//    }
}
