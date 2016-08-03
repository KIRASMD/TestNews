package com.example.testnews.Util;

import com.example.testnews.model.JsonNews;
import com.example.testnews.model.News;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/28.
 */

public class HttpUtil {

    public String getHttpResponseData(final String address) {

        URL url = null;
        StringBuilder result = null;
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setRequestProperty("apikey",  "75483408937232d187594475c3132533");
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            result = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

//        Log.d("ll", "getHttpResponseData: "+result.toString());
        return result.toString();

    }

    public List<News> getJsonData(String response) {
        List<News> newsList=new ArrayList<>();
        Gson gson = new Gson();
        JsonNews jsonNews=gson.fromJson(response,JsonNews.class);
        newsList=jsonNews.getNewslist();

        return newsList;

    }

}
