package com.example.testnews.Util;

/**
 * Created by Administrator on 2016/7/28.
 */

public interface OnBackCall {
    void onFinish(String response);

    void onError(Exception e);

}
