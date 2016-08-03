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

import com.example.testnews.R;
import com.example.testnews.Util.HttpUtil;
import com.example.testnews.activity.ContentActivity;
import com.example.testnews.adapter.LoadListView;
import com.example.testnews.adapter.NewsAdapter;
import com.example.testnews.model.News;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/7/27.
 */

public class Fragment1_tab1 extends Fragment implements LoadListView.Listinterface,AdapterView.OnItemClickListener,LoadListView.RefreshListener{

    private LoadListView listView;
    private NewsAdapter adapter;
    private int currentPage=1;
    private List<News> listNews=new ArrayList<>();
    private int loadcode;//标记下拉还是刷新

    String address="http://apis.baidu.com/txapi/tiyu/tiyu?num=15&page="+currentPage;
    //在onActivityCreated中执行newsAsyncTask
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new AsyncTaskForNewsGet().execute(address);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_tab1, container, false);

        listView = (LoadListView) view.findViewById(R.id.news_listview);


        listView.setOnItemClickListener(this);
        return view;

    }

//

    @Override
    public void onLoad() {
        loadcode=0;
        currentPage=currentPage+1;
        Log.d("page", "page: " + currentPage);
        address="http://apis.baidu.com/txapi/tiyu/tiyu?num=15&page="+currentPage;
        new AsyncTaskForNewsGet().execute(address);

    }

    @Override
    public void onRefresh() {
        loadcode=1;
        Log.d("dd", "loadcode: " + loadcode);
        address="http://apis.baidu.com/txapi/tiyu/tiyu?num=15&page=1";
        new AsyncTaskForNewsGet().execute(address);
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
        protected List<News> doInBackground(String... strings)
        {

            String response= httpUtil.getHttpResponseData(address);
            List<News> newsList=httpUtil.getJsonData(response);
            if(loadcode==0){
                listNews.addAll(newsList);
            }else if(loadcode==1){
                listNews.clear();
                listNews.addAll(newsList);
            }
            return listNews;
        }


        @Override
        protected void onPostExecute(List<News> newses) {
            super.onPostExecute(newses);
            showListView(newses);
        }
    }
    private void showListView(List<News> newses){
        if(adapter==null) {
            adapter = new NewsAdapter(getActivity(), newses);
            listView.setListinterface(this);
            listView.setRefreshListener(this);
            listView.setAdapter(adapter);
        }else{
            adapter.setDataChanged(listNews);
        }
    }



}
