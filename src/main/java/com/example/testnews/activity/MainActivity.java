package com.example.testnews.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.testnews.R;
import com.example.testnews.adapter.MainFragment1;
import com.example.testnews.adapter.MainFragment2;
import com.example.testnews.adapter.MainFragment3;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private MainFragment1 mainFragment1;
    private MainFragment2 mainFragment2;
    private MainFragment3 mainFragment3;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initMain();


    }

    private void initMain() {
        imageButton1 = (ImageButton) findViewById(R.id.img_button1);
        imageButton2 = (ImageButton) findViewById(R.id.img_button2);
        imageButton3 = (ImageButton) findViewById(R.id.img_button3);
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);

        manager = getSupportFragmentManager();


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
        switch (position) {
            case 1:
                if (mainFragment1 == null) {
                    mainFragment1 = new MainFragment1();
                    transaction.add(R.id.main_framelayout, mainFragment1);
                } else {
                    transaction.show(mainFragment1);
                }
                break;
            case 2:
                if (mainFragment2 == null) {
                    mainFragment2 = new MainFragment2();
                    transaction.add(R.id.main_framelayout, mainFragment2);
                } else {
                    transaction.show(mainFragment2);
                }
                break;
            case 3:
                if (mainFragment3 == null) {
                    mainFragment3 = new MainFragment3();
                    transaction.add(R.id.main_framelayout, mainFragment3);
                } else {
                    transaction.show(mainFragment3);
                }
                break;
        }
        transaction.commit();


    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mainFragment1 != null) {
            transaction.hide(mainFragment1);
        }
        if (mainFragment2 != null) {
            transaction.hide(mainFragment2);
        }
        if (mainFragment3 != null) {
            transaction.hide(mainFragment3);
        }
    }
}
