package com.example.testnews.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.testnews.R;
import com.example.testnews.Util.HttpUtil;
import com.example.testnews.Util.OnBackCall;
import com.example.testnews.activity.ContentActivity;
import com.example.testnews.adapter.NewsAdapter;
import com.example.testnews.model.JsonBean;
import com.example.testnews.model.News;
import com.example.testnews.model.NewsBean;
import com.example.testnews.model.TestNewsBean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/27.
 */

public class Fragment1_tab1 extends Fragment implements AdapterView.OnItemClickListener{

    private ListView listView;


    private String address = "http://v.juhe.cn/toutiao/index?type=guoji&key=7ad184618a826f348b4715d42b833053";
    private List<News> listNews;

    //在onActivityCreated中执行newsAsyncTask
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new NewsAsyncTask().execute(address);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_tab1, container, false);

        listView = (ListView) view.findViewById(R.id.news_listview);

        listView.setOnItemClickListener(this);

        return view;


    }

    //将地址传入处理得到Json数据
    private List<News> getJsonData(String address) {


        //通过访问网络得到数据回应
        String response = new HttpUtil().getHttpResponseData(address);


        Gson gson = new Gson();
        JsonBean jsonBean = gson.fromJson(response, JsonBean.class);
        NewsBean newsBean = jsonBean.getResult();
        listNews = newsBean.getData();


        return listNews;
    }



    //网络异步访问功能
    class NewsAsyncTask extends AsyncTask<String, Void, List<News>> {

        @Override
        protected List<News> doInBackground(String... strings) {


            return getJsonData(strings[0]);
        }

        @Override
        protected void onPostExecute(List<News> newses) {
            super.onPostExecute(newses);
            NewsAdapter adapter = new NewsAdapter(getActivity(), R.layout.news_itemlayout, listNews,listView);
            listView.setAdapter(adapter);


        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String url=listNews.get(i).getUrl();
        Intent intent=new Intent(getActivity(), ContentActivity.class);
        intent.putExtra("URL",url);
        startActivity(intent);


    }

}
