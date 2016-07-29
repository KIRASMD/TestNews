package com.example.testnews.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.testnews.R;
import com.example.testnews.Util.HttpUtil;
import com.example.testnews.activity.ContentActivity;
import com.example.testnews.adapter.NewsAdapter;
import com.example.testnews.model.JsonBean;
import com.example.testnews.model.News;
import com.example.testnews.model.NewsBean;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */

public class Fragment1_tab2 extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    private String address = "http://v.juhe.cn/toutiao/index?type=tiyu&key=7ad184618a826f348b4715d42b833053";

    private List<News> listNews;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new AsyncTaskForNewsGet().execute(address);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_tab2, container, false);

        listView = (ListView) view.findViewById(R.id.news_listview_2);
        listView.setOnItemClickListener(this);


        return view;


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        News news=listNews.get(i);
        Intent intent=new Intent(getActivity(),ContentActivity.class);
        intent.putExtra("URL",news.getUrl());
        startActivity(intent);
    }
    class AsyncTaskForNewsGet extends AsyncTask<String,Void,List<News>> {

        HttpUtil httpUtil = new HttpUtil();

        @Override
        protected List<News> doInBackground(String... strings) {

            String response = httpUtil.getHttpResponseData(strings[0]);
            listNews=httpUtil.getJsonData(response);
            return listNews;
        }

        @Override
        protected void onPostExecute(List<News> newses) {
            super.onPostExecute(newses);
            NewsAdapter adapter=new NewsAdapter(getContext(),R.layout.news_itemlayout, newses);

            listView.setAdapter(adapter);

        }
    }
}
