package com.example.testnews.model;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27.
 */

public class NewsBean {
    private int stat;
    private List<News> data;

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
        this.data = data;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }
}
