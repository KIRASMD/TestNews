package com.example.testnews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testnews.R;
import com.example.testnews.Util.HttpUtil;
import com.example.testnews.Util.OnBackCall;
import com.example.testnews.model.JsonBean;
import com.example.testnews.model.News;
import com.example.testnews.model.NewsBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/7/27.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    private int resourceId;

//    List<News> listNewses;


    public NewsAdapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);

        resourceId=resource;
//        listNewses=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        News news =getItem(position);

        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) view.findViewById(R.id.news_item_imgview);

            viewHolder.title=(TextView)view.findViewById(R.id.news_item_title);
            viewHolder.author=(TextView)view.findViewById(R.id.news_item_author);
            view.setTag(viewHolder);

        }else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }


        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        viewHolder.title.setText(news.getTitle());
        viewHolder.author.setText(news.getAuthor_name());

        return view;

    }








    class ViewHolder{
        ImageView imageView;
        TextView title;
        TextView author;

    }
}
