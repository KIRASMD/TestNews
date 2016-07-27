package com.example.testnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testnews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */

public class FragmentMain1 extends Fragment {
    private Fragment1_tab1 tab1;
    private Fragment1_tab2 tab2;
    private Fragment1_tab3 tab3;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> listFragment=new ArrayList<Fragment>();
    private List<String> listTitle=new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_1,container,false);

        tab1=new Fragment1_tab1();
        tab2=new Fragment1_tab2();
        tab3=new Fragment1_tab3();

        listFragment.add(tab1);
        listFragment.add(tab2);
        listFragment.add(tab3);

        listTitle.add("头条");
        listTitle.add("体育");
        listTitle.add("娱乐");
        tabLayout=(TabLayout)view.findViewById(R.id.fragmain1_tablayout);
        viewPager=(ViewPager)view.findViewById(R.id.fragmain1_viewpager);


        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(2)));


        FragmentPagerAdapter adapter=new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return listFragment.get(position);
            }

            @Override
            public int getCount() {
                return listFragment.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return listTitle.get(position);
            }
        };

        viewPager.
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

        return view;



    }
    private void initFragment(){

    }

}
