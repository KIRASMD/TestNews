package com.example.testnews.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.testnews.R;
import com.example.testnews.adapter.NewsAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016/7/28.
 */

public class ImageLoder {
    //创建Cache
    private LruCache<String, Bitmap> mCaches;

    private ListView mListView;
    private Set<NewsAsyncTask> mTask;


    public ImageLoder(ListView listview) {

        mListView=listview;
        mTask=new HashSet<>();
        //获取最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        mCaches = new LruCache<String, Bitmap>(cacheSize) {

            @Override
            protected int sizeOf(String key, Bitmap value) {
                //每次存入缓存时候调用
                return value.getByteCount();

            }
        };

    }
    //增加到缓存
    public void addBitmapToCache(String imageUrl, Bitmap bitmap) {
        if (getBitmapFromCache(imageUrl) == null) {
            mCaches.put(imageUrl, bitmap);
        }
    }
    //从缓存中获取数据
    public Bitmap getBitmapFromCache(String imageUrl) {
        return mCaches.get(imageUrl);
    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }
//
//    public void showImageByAsyncTask(ImageView iamgeView, String imageUrl) {
//        //从缓存中取出对应的图片
//        Bitmap bitmap=getBitmapFromCache(imageUrl);
//        //如果缓存中没有，那么必须下载
//        if(bitmap==null){
//            iamgeView.setImageResource(R.mipmap.ic_launcher);
//        }else{
//            iamgeView.setImageBitmap(bitmap);
//        }
//    }


    public void cancelAllTasks(){
        if(mTask!=null){
             for (NewsAsyncTask task:mTask
                 ) {task.cancel(false);

    }
}
    }


    //用来加载从Start到end的所有图片
    public  void  loadImages(int start,int end){
        for (int i = start; i <end ; i++) {
            String imageUrl= NewsAdapter.imageURLS[i];
            Bitmap bitmap=getBitmapFromCache(imageUrl);

            if(bitmap==null){
                NewsAsyncTask task=new NewsAsyncTask(imageUrl);
                task.execute(imageUrl);
                mTask.add(task);
            }else{
                ImageView imageView= (ImageView) mListView.findViewWithTag(imageUrl);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {
//        private ImageView mImageView;
        private String mUrl;

        public NewsAsyncTask( String imageUrl) {
//            mImageView = imageView;
            mUrl = imageUrl;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
           Bitmap bitmap=getBitmapFromURL(strings[0]);
            if(bitmap!=null){
                //将不在缓存的存入缓存
                addBitmapToCache(strings[0],bitmap);
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView= (ImageView) mListView.findViewWithTag(mUrl);
            if(imageView!=null&&bitmap!=null){
                imageView.setImageBitmap(bitmap);
            }
            mTask.remove(this);
        }
    }

}
