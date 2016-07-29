package com.example.testnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.testnews.R;
import com.example.testnews.fragment.FragmentMain1;
import com.example.testnews.fragment.FragmentMain2;
import com.example.testnews.fragment.FragmentMain3;

/**
 * Created by Administrator on 2016/7/27.
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private FragmentManager manager;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;

    private FragmentMain1 fragmentMain1;
    private FragmentMain2 fragmentMain2;
    private FragmentMain3 fragmentMain3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        initMain();


    }

    private void initMain() {
        imageButton1 = (ImageButton) findViewById(R.id.img_button1);
        imageButton2 = (ImageButton) findViewById(R.id.img_button2);
        imageButton3 = (ImageButton) findViewById(R.id.img_button3);
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_button1:
                selectFragment(1);
                break;
            case R.id.img_button2:
                selectFragment(2);
                break;
            case R.id.img_button3:
                selectFragment(3);
                break;
        }
    }

    private void selectFragment(int position) {
        FragmentTransaction transaction = manager.beginTransaction();

        hideFragment(transaction);

        switch (position){
            case 1:
                if(fragmentMain1==null){
                    fragmentMain1=new FragmentMain1();
                    transaction.add(R.id.main_framelayout,fragmentMain1);
                }else{
                    transaction.show(fragmentMain1);
                }
                break;
            case 2:
                if(fragmentMain2==null){
                    fragmentMain2=new FragmentMain2();
                    transaction.add(R.id.main_framelayout,fragmentMain2);
                }else{
                    transaction.show(fragmentMain2);
                }
                break;
            case 3:
                if(fragmentMain3==null){
                    fragmentMain3=new FragmentMain3();
                    transaction.add(R.id.main_framelayout,fragmentMain3);
                }else{
                    transaction.show(fragmentMain3);
                }
                break;

        }
        transaction.commit();

    }

    private void hideFragment(FragmentTransaction transaction) {
        if (fragmentMain1 != null) {
            transaction.hide(fragmentMain1);
        }
        if (fragmentMain2 != null) {
            transaction.hide(fragmentMain2);
        }
        if (fragmentMain3 != null) {
            transaction.hide(fragmentMain3);
        }
    }
}
