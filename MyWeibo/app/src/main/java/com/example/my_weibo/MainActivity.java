package com.example.my_weibo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.my_weibo.fragment.FragmentDiscover;
import com.example.my_weibo.fragment.FragmentHome;
import com.example.my_weibo.fragment.FragmentMine;
import com.example.my_weibo.fragment.FragmentMsg;
import com.example.my_weibo.fragment.FragmentPopular;
import com.example.my_weibo.fragment.FragmentVedio;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    FragmentHome fHome = new FragmentHome();
    FragmentPopular fPopular = new FragmentPopular();
    FragmentVedio fVedio = new FragmentVedio();
    FragmentDiscover fDiscover = new FragmentDiscover();
    FragmentMsg fMsg = new FragmentMsg();
    FragmentMine fMine = new FragmentMine();

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft;

    LinearLayout topButton;
    boolean isFollowPage = true;    //判断当前微博首页是热门还是关注

    Button btnHome;
    Button btn1;
    Button btn2;
    Button btnVedio;
    Button btnDiscover;
    Button btnMsg;
    Button btnMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHome = (Button)findViewById(R.id.page_home);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btnVedio = (Button)findViewById(R.id.page_video);
        btnDiscover = (Button)findViewById(R.id.page_discover);
        btnMsg = (Button)findViewById(R.id.page_msg);
        btnMine = (Button)findViewById(R.id.page_mine);

        topButton = findViewById(R.id.top);

        initialClickListener();
        initialFragmentTransaction();

    }

    @Override
    public void onClick(View v) {
        int buttonID = v.getId();
        topButton.setVisibility(View.VISIBLE);
        if(buttonID != R.id.btn1 && buttonID != R.id.btn2){
            resetButtonColor();
            v.setBackgroundColor(Color.parseColor("#DCDCDC"));
            if(buttonID != R.id.page_home){
                topButton.setVisibility(View.INVISIBLE);
            }
        }
        switch (buttonID) {
                case R.id.page_home:
                    if(isFollowPage)
                        loadFragment(fHome);
                    else
                        loadFragment(fPopular);
                    break;
                case R.id.btn1:
                    loadFragment(fHome);
                    isFollowPage = true;
                    btn1.setTextColor(Color.parseColor("#000000"));
                    btn2.setTextColor(Color.parseColor("#A9A9A9"));
                    break;
                case R.id.btn2:
                    loadFragment(fPopular);
                    isFollowPage = false;
                    btn2.setTextColor(Color.parseColor("#000000"));
                    btn1.setTextColor(Color.parseColor("#A9A9A9"));
                    break;
                case R.id.page_video:
                    loadFragment(fVedio);
                    break;
                case R.id.page_discover:
                    loadFragment(fDiscover);
                    break;
                case R.id.page_msg:
                    loadFragment(fMsg);
                    break;
                case R.id.page_mine:
                    loadFragment(fMine);
                    break;
                default:
                    break;
        }
    }

    //初始化所有按钮的事件监听
    public void initialClickListener(){
        btnHome.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btnVedio.setOnClickListener(this);
        btnDiscover.setOnClickListener(this);
        btnMsg.setOnClickListener(this);
        btnMine.setOnClickListener(this);
    }

    //重置按钮颜色
    public void resetButtonColor(){
        btnHome.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btnVedio.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btnDiscover.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btnMsg.setBackgroundColor(Color.parseColor("#FFFFFF"));
        btnMine.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    //向FragmentTransaction中添加所有的Fragment
    public void initialFragmentTransaction(){
        ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, fMine);
        ft.add(R.id.fragment_container, fPopular);
        ft.add(R.id.fragment_container, fMsg);
        ft.add(R.id.fragment_container, fDiscover);
        ft.add(R.id.fragment_container, fVedio);
        ft.add(R.id.fragment_container, fHome);
        ft.commit();
    }

    //载入Fragment
    public void loadFragment(@NonNull Fragment fragment){
        ft = fm.beginTransaction();
        hideFragments(ft);
        ft.show(fragment);
        ft.commit();
    }

    //隐藏所有Fragment
    private void hideFragments(FragmentTransaction ft) {
        if (fHome != null) ft.hide(fHome);
        if (fPopular != null) ft.hide(fPopular);
        if (fVedio != null) ft.hide(fVedio);
        if (fDiscover != null) ft.hide(fDiscover);
        if (fMsg != null) ft.hide(fMsg);
        if (fMine != null) ft.hide(fMine);
    }

}