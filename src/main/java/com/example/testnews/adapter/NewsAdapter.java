package com.example.testnews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testnews.R;
import com.example.testnews.Util.HttpUtil;
import com.example.testnews.Util.ImageLoder;
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

public class NewsAdapter extends ArrayAdapter<News> implements AbsListView.OnScrollListener{
    private int resourceId;
    private ImageLoder imageLoder;

    private int mStart,mEnb;
    public static String[] imageURLS;
    private boolean mFirstIn;




    public NewsAdapter(Context context, int resource, List<News> objects, ListView listView) {
        super(context, resource, objects);

        mFirstIn=true;
        resourceId=resource;
//        listNewses=objects;
        imageLoder=new ImageLoder(listView);
        imageURLS=new String[objects.size()];
        for (int i = 0; i < objects.size(); i++) {
            imageURLS[i]=objects.get(i).getThumbnail_pic_s();
        }
        listView.setOnScrollListener(this);
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
        String imageUrl=news.getThumbnail_pic_s();
        viewHolder.imageView.setTag(imageUrl);
//        imageLoder.showImageByAsyncTask(viewHolder.imageView, imageUrl);


        viewHolder.title.setText(news.getTitle());
        viewHolder.author.setText(news.getAuthor_name());

        return view;

    }
//状态切换时候调用,在滚动时候停止加载，停止滚动时候加载
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if(scrollState==SCROLL_STATE_IDLE){
            //加载可见项
            imageLoder.loadImages(mStart , mEnb);
        }else{
            //停止任务
            imageLoder.cancelAllTasks();

        }
    }
//一直在调用
    @Override
    public void onScroll(AbsListView absListView, int firstVisiableItem, int visibleItemCount, int totalItem) {


        mStart = firstVisiableItem;
        mEnb=firstVisiableItem+visibleItemCount;
//第一次显示时候调用
        if (mFirstIn&&visibleItemCount>0){
            imageLoder.loadImages(mStart,mEnb);
            mFirstIn=false;
        }
    }


    class ViewHolder{
        ImageView imageView;
        TextView title;
        TextView author;

    }
}
