package com.example.testnews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.testnews.R;

/**
 * Created by Administrator on 2016/7/29.
 */

public class ContentActivity extends Activity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_content);
        webView= (WebView) findViewById(R.id.webview);

        Intent intent=getIntent();
        String url=intent.getStringExtra("URL");
        Log.d("ddfd",url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键


                        webView.goBack();   //后退

                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });


    }
}
