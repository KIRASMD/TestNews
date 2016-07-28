package com.example.testnews.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/28.
 */

public class HttpUtil {

    public String getHttpResponseData(final String address) {

        URL url = null;
        StringBuilder result=null;
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setReadTimeout(8000);

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

        return result.toString();

    }

}
