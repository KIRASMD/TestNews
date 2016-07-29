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
    private List<News> mListNews;
    private int resourceId;
    private ImageLoader imageLoader;
    public NewsAdapter(Context context, int resourceId, List<News> listNews) {
        mInflater=LayoutInflater.from(context);
        mListNews=listNews;
        this.resourceId=resourceId;
        imageLoader=new ImageLoader();


    }




    @Override
    public int getCount() {
        return mListNews.size();
    }

    @Override
    public Object getItem(int i) {
        return mListNews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = null;
        ViewHolder viewHolder=null;
        if (convertView == null) {
            view=mInflater.inflate(resourceId, null);
            viewHolder=new ViewHolder();

            viewHolder.author= (TextView) view.findViewById(R.id.news_item_author);
            viewHolder.title= (TextView) view.findViewById(R.id.news_item_title);
            viewHolder.icon= (ImageView) view.findViewById(R.id.news_item_imgview);
            view.setTag(viewHolder);

        }else{
            view=convertView;
            viewHolder= (ViewHolder) convertView.getTag();
        }

        String imageUrl=mListNews.get(position).getThumbnail_pic_s();
        viewHolder.icon.setImageResource(R.mipmap.ic_launcher);

        viewHolder.icon.setTag(imageUrl);
        imageLoader.showImage(viewHolder.icon,imageUrl);

        viewHolder.title.setText(mListNews.get(position).getTitle());
        viewHolder.author.setText(mListNews.get(position).getAuthor_name());


        return view;

    }
    class ViewHolder{
        ImageView icon;
        TextView title,author;

    }

}
