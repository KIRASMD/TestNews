package com.example.testnews.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * Created by Administrator on 2016/7/29.
 */

public class ImageLoader {

    private LruCache<String, Bitmap> cache;

    private String imageUrl;
    private ImageView imageView;

    private Set<AsyncTaskForImage> Tasks;

    public ImageLoader() {

        int maxSize = (int) Runtime.getRuntime().maxMemory();
        int cacheMaxMemory = maxSize / 4;
        cache = new LruCache<String, Bitmap>(cacheMaxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    private Bitmap getBitmapFromCache(String imageUrl) {
        return cache.get(imageUrl);
    }

    private void addBitmapToCache(String imageUrl, Bitmap bitmap) {

        if (getBitmapFromCache(imageUrl) == null) {
            cache.put(imageUrl, bitmap);
        }

    }

    public void showImage(ImageView imageView, String imageUrl) {
        Bitmap bitmap = getBitmapFromCache(imageUrl);

        if (bitmap == null) {
            AsyncTaskForImage taskForImage = new AsyncTaskForImage(imageView,imageUrl);
            taskForImage.execute(imageUrl);

        } else {
            imageView.setImageBitmap(bitmap);
        }


    }

    public Bitmap getBitmapFromWap(String imageUrl) {

        Bitmap bitmap;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            connection.disconnect();
            inputStream.close();
            return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    class AsyncTaskForImage extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView;

        String imageUrl;
        public AsyncTaskForImage(ImageView imageView,String imageUrl) {
            this.imageView = imageView;
            this.imageUrl=imageUrl;
        }


        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = getBitmapFromCache(strings[0]);
            if (bitmap == null) {
                bitmap = getBitmapFromWap(strings[0]);
                addBitmapToCache(strings[0], bitmap);
            }

            return bitmap;

        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (imageView.getTag().equals(imageUrl)) {
                imageView.setImageBitmap(bitmap);

            }

        }
    }

}
