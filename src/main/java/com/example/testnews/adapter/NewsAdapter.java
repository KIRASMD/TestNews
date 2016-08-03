package com.example.testnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testnews.R;
import com.example.testnews.Util.ImageLoader;
import com.example.testnews.model.News;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29.
 */

public class NewsAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<News> listNews;
    private ImageLoader imageLoader;
    public NewsAdapter(Context context,List<News> listNews) {
        mInflater= LayoutInflater.from(context);
        this.listNews=listNews;
        imageLoader=new ImageLoader();


    }

    @Override
    public int getCount() {
        return listNews.size();
    }

    @Override
    public Object getItem(int i) {
        return listNews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = null;
        ViewHolder viewHolder=null;
        News news=listNews.get(position);
        if (convertView == null) {
            view=mInflater.inflate(R.layout.news_item_1pic, null);
            viewHolder=new ViewHolder();

            viewHolder.author= (TextView) view.findViewById(R.id.pic1_author_time);
            viewHolder.title= (TextView) view.findViewById(R.id.pic1_title);
            viewHolder.icon= (ImageView) view.findViewById(R.id.pic1_icon);
            view.setTag(viewHolder);

        }else{
            view=convertView;
            viewHolder= (ViewHolder) convertView.getTag();
        }

        String imageUrl=news.getPicUrl();
        viewHolder.icon.setImageResource(R.mipmap.ic_launcher);

        viewHolder.icon.setTag(imageUrl);
        imageLoader.showImage(viewHolder.icon,imageUrl);

        viewHolder.title.setText(news.getTitle());
        viewHolder.author.setText(news.getDescription()+" "+news.getCtime());


        return view;

    }

    public void setDataChanged(List<News> listNews) {

        this.listNews=listNews;
        this.notifyDataSetChanged();
    }


    class ViewHolder{
        ImageView icon;
        TextView title,author;

    }

}
