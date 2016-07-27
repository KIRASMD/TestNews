package com.example.testnews.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.testnews.R;
import com.example.testnews.fragment.Fragment1_tab1;
import com.example.testnews.fragment.Fragment1_tab2;
import com.example.testnews.fragment.Fragment1_tab3;
import com.example.testnews.fragment.FragmentMain1;
import com.example.testnews.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */

public class TestActivity extends FragmentActivity {




    private View view;
    private FragmentManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        manager=getSupportFragmentManager();
        final TestFragment fragme=new TestFragment();
        Button button=(Button)findViewById(R.id.testButton) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.beginTransaction().add(R.id.testFram,fragme).commit();
            }
        });



    }
}
